package ru.basejava.webapp.storage;

import ru.basejava.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, countResumes, searchKey);
    }

    @Override
    protected void insertToArray(Resume resume, int index) {
        index = -index-1;
        System.arraycopy(storage, index, storage, index + 1, countResumes - index);
        storage[index] = resume;
        countResumes++;
    }

    @Override
    protected void removeFromArray(int index) {
        System.arraycopy(storage, index + 1, storage, index, countResumes - index);
        countResumes--;
    }
}
