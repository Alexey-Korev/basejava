package ru.basejava.webapp.storage;

import ru.basejava.webapp.storage.Serializers.ObjectStreamStrategy;

public class ObjectStreamPathStorageTest extends AbstractStorageTest{


    public ObjectStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.toString(), new ObjectStreamStrategy()));
    }
}
