package ru.basejava.webapp.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.basejava.webapp.exception.ExistStorageException;
import ru.basejava.webapp.exception.NotExistStorageException;
import ru.basejava.webapp.model.Resume;

public abstract class AbstractStorageTest {
    protected final Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String UUID_NOT_EXIST = "dummy";

    protected static final Resume RESUME1;
    protected static final Resume RESUME2;
    protected static final Resume RESUME3;
    protected static final Resume RESUME4;

    static {
        RESUME1 = new Resume(UUID_1);
        RESUME2 = new Resume(UUID_2);
        RESUME3 = new Resume(UUID_3);
        RESUME4 = new Resume(UUID_4);
    }

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
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
    public abstract void getAll();

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