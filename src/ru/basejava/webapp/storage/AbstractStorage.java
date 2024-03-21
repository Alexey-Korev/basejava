package ru.basejava.webapp.storage;

import ru.basejava.webapp.exception.ExistStorageException;
import ru.basejava.webapp.exception.NotExistStorageException;
import ru.basejava.webapp.model.Resume;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {
    private static Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

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
        LOG.info("Update " + resume);
        SK searchKey = getSearchKey(resume.getUuid());
        doUpdate(resume, getExistingSearchKey(searchKey, resume.getUuid()));
    }


    public void save(Resume resume) {
        LOG.info("Save " + resume);
        SK searchKey = getSearchKey(resume.getUuid());
        doSave(resume, getNotExistingSearchKey(searchKey, resume.getUuid()));
    }

    public Resume get(String key) {
        LOG.info("Get " + key);
        SK searchKey = getSearchKey(key);
        return doGet(getExistingSearchKey(searchKey, key));
    }

    public void delete(String key) {
        LOG.info("Delete " + key);
        SK searchKey = getSearchKey(key);
        doDelete(getExistingSearchKey(searchKey, key));
    }

    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> list = doGetAll();
        Collections.sort(list);
        return list;
    }
    private SK getExistingSearchKey(SK searchKey, String uuid) {
        if (!isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " is not in the storage");
            throw new NotExistStorageException(uuid);
        } else {
            return searchKey;
        }
    }


    private SK getNotExistingSearchKey(SK searchKey, String uuid) {
        if (isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " is already in the storage");
            throw new ExistStorageException(uuid);
        } else {
            return searchKey;
        }
    }


}
