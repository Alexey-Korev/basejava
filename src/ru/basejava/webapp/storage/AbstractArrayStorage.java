package ru.basejava.webapp.storage;

import ru.basejava.webapp.exception.StorageException;
import ru.basejava.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int countResumes = 0;


    public void clear() {
        Arrays.fill(storage, 0, countResumes, null);
        countResumes = 0;
    }

    public List<Resume> makeList() {
        return Arrays.asList(Arrays.copyOf(storage, countResumes));
    }


    public int size() {
        return countResumes;
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage[(Integer) searchKey];
    }

    @Override
    protected void doUpdate(Resume resume, Object searchKey) {
        storage[(Integer) searchKey] = resume;
    }

    public void doSave(Resume resume, Object searchKey) {
        if (countResumes >= STORAGE_LIMIT) {
            throw new StorageException("Storage is full", resume.getUuid());
        } else {
            insertToArray(resume, (Integer) searchKey);
            countResumes++;
        }
    }

    public void doDelete(Object searchKey) {
        removeFromArray((Integer) searchKey);
        countResumes--;
        storage[countResumes] = null;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return ((Integer) searchKey >= 0);
    }

    protected abstract void insertToArray(Resume resume, int index);

    protected abstract void removeFromArray(int index);
}
