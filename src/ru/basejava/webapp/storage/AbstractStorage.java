package ru.basejava.webapp.storage;

import ru.basejava.webapp.exception.ExistStorageException;
import ru.basejava.webapp.exception.NotExistStorageException;
import ru.basejava.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public abstract void clear();

    public abstract Resume[] getAll();

    public abstract int size();

    protected abstract Object getSearchKey(String uuid);

    protected abstract void doSave(Resume resume, Object searchKey);

    protected abstract Resume doGet(Object searchKey);

    protected abstract void doDelete(Object searchKey);

    protected abstract void doUpdate(Resume resume, Object searchKey);

    protected abstract boolean isExist(Object searchKey);

    public void update(Resume resume) {
        Object searchKey = getSearchKey(resume.getUuid());
        doUpdate(resume, getExistingSearchKey(searchKey, resume.getUuid()));
    }


    public void save(Resume resume) {
        Object searchKey = getSearchKey(resume.getUuid());
        doSave(resume, getNotExistingSearchKey(searchKey, resume.getUuid()));
    }

    public Resume get(String uuid) {
        Object searchKey = getSearchKey(uuid);
        return doGet(getExistingSearchKey(searchKey, uuid));
    }

    public void delete(String uuid) {
        Object searchKey = getSearchKey(uuid);
        doDelete(getExistingSearchKey(searchKey, uuid));
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
