package ru.basejava.webapp.storage;

import ru.basejava.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    protected int getIndex(String uuid) {
        for (int i = 0; i < countResumes; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void insertToArray(Resume resume, int index) {
        storage[countResumes++] = resume;
    }

    @Override
    protected void removeFromArray(int index) {
        storage[index] = storage[--countResumes];
        storage[countResumes] = null;
    }
}
