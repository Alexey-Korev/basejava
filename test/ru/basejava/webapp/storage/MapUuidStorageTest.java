package ru.basejava.webapp.storage;

import java.util.HashMap;

public class MapUuidStorageTest extends AbstractStorageTest{

    public MapUuidStorageTest() {
        super(new MapUuidStorage(new HashMap<>()));
    }
}
