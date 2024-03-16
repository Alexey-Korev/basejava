package ru.basejava.webapp.storage;

import ru.basejava.webapp.exception.StorageException;
import ru.basejava.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int countResumes = 0;


    public void clear() {
        Arrays.fill(storage, 0, countResumes, null);
        countResumes = 0;
    }

    public List<Resume> doGetAll() {
        return Arrays.asList(Arrays.copyOf(storage, countResumes));
    }


    public int size() {
        return countResumes;
    }

    @Override
    protected Resume doGet(Integer searchKey) {
        return storage[searchKey];
    }

    @Override
    protected void doUpdate(Resume resume, Integer searchKey) {
        storage[searchKey] = resume;
    }

    public void doSave(Resume resume, Integer searchKey) {
        if (countResumes >= STORAGE_LIMIT) {
            throw new StorageException("Storage is full", resume.getUuid());
        } else {
            insertToArray(resume, searchKey);
            countResumes++;
        }
    }

    public void doDelete(Integer searchKey) {
        removeFromArray(searchKey);
        countResumes--;
        storage[countResumes] = null;
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey >= 0;
    }

    protected abstract void insertToArray(Resume resume, int index);

    protected abstract void removeFromArray(int index);
}
