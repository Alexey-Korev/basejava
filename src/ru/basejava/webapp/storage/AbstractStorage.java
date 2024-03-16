package ru.basejava.webapp.storage;

import ru.basejava.webapp.exception.ExistStorageException;
import ru.basejava.webapp.exception.NotExistStorageException;
import ru.basejava.webapp.model.Resume;

import java.util.Collections;
import java.util.List;

public abstract class AbstractStorage<SK> implements Storage {
//    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    public abstract void clear();

    public abstract int size();

    protected abstract SK getSearchKey(String uuid);

    protected abstract void doSave(Resume resume, SK searchKey);

    protected abstract Resume doGet(SK searchKey);

    protected abstract void doDelete(SK searchKey);

    protected abstract void doUpdate(Resume resume, SK searchKey);

    protected abstract boolean isExist(SK searchKey);

    protected abstract List<Resume> doGetAll();

    public void update(Resume resume) {
        SK searchKey = getSearchKey(resume.getUuid());
        doUpdate(resume, getExistingSearchKey(searchKey, resume.getUuid()));
    }


    public void save(Resume resume) {
        SK searchKey = getSearchKey(resume.getUuid());
        doSave(resume, getNotExistingSearchKey(searchKey, resume.getUuid()));
    }

    public Resume get(String key) {
        SK searchKey = getSearchKey(key);
        return doGet(getExistingSearchKey(searchKey, key));
    }

    public void delete(String key) {
        SK searchKey = getSearchKey(key);
        doDelete(getExistingSearchKey(searchKey, key));
    }

    public List<Resume> getAllSorted() {
        List<Resume> list = doGetAll();
        Collections.sort(list);
        return list;
    }
    private SK getExistingSearchKey(SK searchKey, String uuid) {
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        } else {
            return searchKey;
        }
    }


    private SK getNotExistingSearchKey(SK searchKey, String uuid) {
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        } else {
            return searchKey;
        }
    }


}
