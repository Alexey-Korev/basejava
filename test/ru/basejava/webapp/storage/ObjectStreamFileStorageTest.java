package ru.basejava.webapp.storage;

public class ObjectStreamFileStorageTest  extends AbstractStorageTest {

    public ObjectStreamFileStorageTest() {
        super(new ObjectStreamFileStorage(STORAGE_DIR, new ObjectStreamStrategy()));
    }
}
