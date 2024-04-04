package ru.basejava.webapp.storage;

import ru.basejava.webapp.storage.serializers.ObjectStreamStrategy;

public class ObjectStreamFileStorageTest extends AbstractStorageTest {

    public ObjectStreamFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamStrategy()));
    }
}
