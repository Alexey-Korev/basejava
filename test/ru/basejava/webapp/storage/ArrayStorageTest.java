package ru.basejava.webapp.storage;

public class ArrayStorageTest extends AbstractArrayStorageTest{
    @Override
    protected Storage createStorage() {
        return new ArrayStorage();
    }
}