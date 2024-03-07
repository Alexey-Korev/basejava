package ru.basejava.webapp.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.basejava.webapp.exception.ExistStorageException;
import ru.basejava.webapp.exception.NotExistStorageException;
import ru.basejava.webapp.model.Resume;

import java.util.ArrayList;

public class ListStorageTest {

    private final Storage storage = new ListStorage(new ArrayList<>());
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String UUID_NOT_EXIST = "dummy";

    private static final Resume RESUME1;
    private static final Resume RESUME2;
    private static final Resume RESUME3;
    private static final Resume RESUME4;

    static {
        RESUME1 = new Resume(UUID_1);
        RESUME2 = new Resume(UUID_2);
        RESUME3 = new Resume(UUID_3);
        RESUME4 = new Resume(UUID_4);
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME1);
        storage.save(RESUME2);
        storage.save(RESUME3);
    }

    @Test
    public void clear() {
        Resume[] expected = {};
        storage.clear();
        assertSize(0);
        Assert.assertArrayEquals(storage.getAll(), expected);
    }
    @Test
    public void update() {
        storage.update(RESUME1);
        Assert.assertSame(RESUME1, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume(UUID_NOT_EXIST));
    }


    @Test(expected = ExistStorageException.class)
    public void saveAlreadyExist() {
        storage.save(RESUME1);
    }

    @Test
    public void save() {
        storage.save(RESUME4);
        assertGet(RESUME4);
        assertSize(4);
    }

    @Test
    public void get() {
        assertGet(RESUME1);
        assertGet(RESUME2);
        assertGet(RESUME3);
    }
    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(UUID_NOT_EXIST);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        assertSize(3);
        assertGet(RESUME1);
        storage.delete(UUID_1);
        assertSize(2);
        assertGet(RESUME1);
    }
    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID_NOT_EXIST);
    }

    @Test
    public void getAll() {
        Resume[] actual = storage.getAll();
        Resume[] expected = {RESUME1, RESUME2, RESUME3};
        Assert.assertArrayEquals(actual, expected);
        Assert.assertEquals(3, actual.length);
    }

    @Test
    public void size() {
        assertSize(3);
    }
    public void assertSize(int size) {
        Assert.assertEquals(size, storage.size());
    }
    public void assertGet(Resume resume) {
        Assert.assertEquals(resume, storage.get(resume.getUuid()));
    }
}
