package ru.basejava.webapp.storage;

import org.junit.Assert;
import ru.basejava.webapp.model.Resume;

import java.util.Arrays;
import java.util.HashMap;

public class MapStorageTest extends AbstractStorageTest{

    public MapStorageTest() {
        super(new MapStorage(new HashMap<>()));
    }

    @Override
    public void getAll() {
        Resume[] actual = storage.getAll();
        Resume[] expected = {RESUME1, RESUME2, RESUME3};
        Assert.assertTrue(Arrays.asList(actual).containsAll(Arrays.asList(expected)));
        Assert.assertTrue(Arrays.asList(expected).containsAll(Arrays.asList(actual)));
        Assert.assertEquals(3, actual.length);
    }
}
