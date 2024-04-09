package ru.basejava.webapp.storage;

import ru.basejava.webapp.storage.serializers.DataStreamSerializer;

public class DataPathStorageTest extends AbstractStorageTest{
    public DataPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new DataStreamSerializer()));
    }
}
