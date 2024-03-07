package ru.basejava.webapp.storage;

import ru.basejava.webapp.exception.ExistStorageException;
import ru.basejava.webapp.exception.NotExistStorageException;
import ru.basejava.webapp.model.Resume;

public abstract class AbstractStorage implements Storage{

    public abstract void clear();
    public abstract Resume[] getAll();

    public abstract int size();

    protected abstract int getIndex(String uuid);
    protected abstract void toSave(Resume resume, int index);
    protected abstract Resume toGet(int index);
    protected abstract void toDelete(int index);
    protected abstract void toUpdate(Resume resume, int index);

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
        } else {
            toUpdate(resume, index);
        }
    }


    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >=0 ) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            toSave(resume, index);
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return toGet(index);
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            toDelete(index);
        }
    }

}
