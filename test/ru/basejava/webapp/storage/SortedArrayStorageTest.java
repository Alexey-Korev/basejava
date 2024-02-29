package ru.basejava.webapp.storage;

public class SortedArrayStorageTest extends AbstractArrayStorageTest{
    @Override
    protected Storage createStorage() {
        return new SortedArrayStorage();
    }
}