package ru.basejava.webapp.storage;

import ru.basejava.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {


    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (countResumes >= STORAGE_LIMIT) {
            System.out.println("Storage is full");
        } else if (index > -1 && index <= countResumes) {
            System.out.println("Resume " + resume + " is already in the storage");
        } else {
            storage[countResumes++] = resume;
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index > -1) {
            storage[index] = storage[--countResumes];
            storage[countResumes] = null;
        } else {
            System.out.println("Resume " + uuid + " is not in the storage");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */

    protected int getIndex(String uuid) {
        for (int i = 0; i < countResumes; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
