package ru.basejava.webapp.storage;

import ru.basejava.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (countResumes >= STORAGE_LIMIT) {
            System.out.println("Storage is full");
        } else if (index > -1 && index <= countResumes) {
            System.out.println("Resume " + resume + " is already in the storage");
        } else {
            index = -index - 1;
            System.arraycopy(storage, index, storage, index + 1, countResumes - index);
            storage[index] = resume;
            countResumes++;
        }
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index > -1) {
            System.arraycopy(storage, index + 1, storage, index, countResumes - index);
            countResumes--;
        } else {
            System.out.println("Resume " + uuid + " is not in the storage");
        }
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, countResumes, searchKey);
    }
}
