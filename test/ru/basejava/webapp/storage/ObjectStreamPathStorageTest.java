package ru.basejava.webapp.storage;

import ru.basejava.webapp.storage.serializers.ObjectStreamStrategy;

public class ObjectStreamPathStorageTest extends AbstractStorageTest{


    public ObjectStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.toString(), new ObjectStreamStrategy()));
    }
}
