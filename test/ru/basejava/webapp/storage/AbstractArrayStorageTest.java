package ru.basejava.webapp.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.basejava.webapp.exception.ExistStorageException;
import ru.basejava.webapp.exception.NotExistStorageException;
import ru.basejava.webapp.model.Resume;

public abstract class AbstractArrayStorageTest {

    private Storage storage;

    protected abstract Storage createStorage();
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    @Before
    public void setUp() throws Exception {
        storage = createStorage();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_3));
        storage.save(new Resume(UUID_2));
    }
    @Test
    public void clear() {
        storage.clear();
        Assert.assertTrue(storage.size() == 0);
    }

    @Test
    public void update() {
        storage.update(storage.get("uuid1"));
    }

    @Test(expected = ExistStorageException.class)
    public void saveAlreadyExist() {
        storage.save(new Resume(UUID_1));
    }

    @Test
    public void get() {
        Assert.assertTrue(storage.get("uuid1").equals(new Resume(UUID_1)));
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete("dummy");
    }

    @Test
    public void getAll() {

    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }
}