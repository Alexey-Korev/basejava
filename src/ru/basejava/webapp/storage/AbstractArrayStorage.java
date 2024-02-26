package ru.basejava.webapp.storage;

import ru.basejava.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int countResumes = 0;


    public void clear() {
        Arrays.fill(storage, 0, countResumes, null);
        countResumes = 0;
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index > -1) {
            storage[index] = resume;
        } else {
            System.out.println("Resume " + resume + " is not in the storage");
        }
    }

    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (countResumes >= STORAGE_LIMIT) {
            System.out.println("Storage is full");
        } else if (index > -1 && index <= countResumes) {
            System.out.println("Resume " + resume + " is already in the storage");
        } else {
            insertToArray(resume, index);
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.println("Resume " + uuid + " is not in the storage");
            return null;
        }
        return storage[index];
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index > -1) {
            removeFromArray(index);
        } else {
            System.out.println("Resume " + uuid + " is not in the storage");
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, countResumes);
    }

    public int size() {
        return countResumes;
    }

    protected abstract int getIndex(String uuid);

    protected abstract void insertToArray(Resume resume, int index);

    protected abstract void removeFromArray(int index);
}
