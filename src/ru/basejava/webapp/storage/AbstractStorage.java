package ru.basejava.webapp.storage;

import ru.basejava.webapp.exception.ExistStorageException;
import ru.basejava.webapp.exception.NotExistStorageException;
import ru.basejava.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage implements Storage {
    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    public abstract void clear();

    public abstract int size();

    protected abstract Object getSearchKey(String uuid);

    protected abstract void doSave(Resume resume, Object searchKey);

    protected abstract Resume doGet(Object searchKey);

    protected abstract void doDelete(Object searchKey);

    protected abstract void doUpdate(Resume resume, Object searchKey);

    protected abstract boolean isExist(Object searchKey);

    protected abstract List<Resume> doGetAll();

    public void update(Resume resume) {
        Object searchKey = getSearchKey(resume.getUuid());
        doUpdate(resume, getExistingSearchKey(searchKey, resume.getUuid()));
    }


    public void save(Resume resume) {
        Object searchKey = getSearchKey(resume.getUuid());
        doSave(resume, getNotExistingSearchKey(searchKey, resume.getUuid()));
    }

    public Resume get(String key) {
        Object searchKey = getSearchKey(key);
        return doGet(getExistingSearchKey(searchKey, key));
    }

    public void delete(String key) {
        Object searchKey = getSearchKey(key);
        doDelete(getExistingSearchKey(searchKey, key));
    }

    public List<Resume> getAllSorted() {
        List<Resume> list = doGetAll();
        list.sort(RESUME_COMPARATOR);
        return list;
    }
    private Object getExistingSearchKey(Object searchKey, String uuid) {
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        } else {
            return searchKey;
        }
    }


    private Object getNotExistingSearchKey(Object searchKey, String uuid) {
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        } else {
            return searchKey;
        }
    }


}
