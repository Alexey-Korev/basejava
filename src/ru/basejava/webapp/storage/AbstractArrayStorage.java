package ru.basejava.webapp.storage;

import ru.basejava.webapp.model.Resume;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int countResumes = 0;

    public int size() {
        return countResumes;
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.println("Resume " + uuid + " is not in the storage");
            return null;
        }
        return storage[index];
    }

    protected abstract int getIndex(String uuid);
}
