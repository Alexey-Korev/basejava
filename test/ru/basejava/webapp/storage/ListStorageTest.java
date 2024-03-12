package ru.basejava.webapp.storage;

import org.junit.Assert;
import ru.basejava.webapp.model.Resume;

import java.util.ArrayList;

public class ListStorageTest extends AbstractStorageTest{

    public ListStorageTest() {
        super(new ListStorage(new ArrayList<>()));
    }

    @Override
    public void getAll() {
        Resume[] actual = storage.getAll();
        Resume[] expected = {RESUME1, RESUME2, RESUME3};
        Assert.assertArrayEquals(actual, expected);
        Assert.assertEquals(3, actual.length);
    }
}
