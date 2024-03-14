package ru.basejava.webapp.storage;

import java.util.HashMap;

public class MapResumeStorageTest extends AbstractStorageTest{

    public MapResumeStorageTest() {
        super(new MapResumeStorage(new HashMap<>()));
    }
}
