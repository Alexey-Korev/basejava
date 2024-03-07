package ru.basejava.webapp.storage;

import ru.basejava.webapp.exception.StorageException;
import ru.basejava.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int countResumes = 0;


    public void clear() {
        Arrays.fill(storage, 0, countResumes, null);
        countResumes = 0;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, countResumes);
    }


    public int size() {
        return countResumes;
    }

    @Override
    protected Resume toGet(int index) {
        return storage[index];
    }

    @Override
    protected void toUpdate(Resume resume, int index) {
        storage[index] = resume;
    }

    public void toSave(Resume resume, int index) {
        if (countResumes >= STORAGE_LIMIT) {
            throw new StorageException("Storage is full", resume.getUuid());
        } else {
            insertToArray(resume, index);
            countResumes++;
        }
    }

    public void toDelete(int index) {
        removeFromArray(index);
        countResumes--;
        storage[countResumes] = null;
    }

    protected abstract void insertToArray(Resume resume, int index);

    protected abstract void removeFromArray(int index);
}
