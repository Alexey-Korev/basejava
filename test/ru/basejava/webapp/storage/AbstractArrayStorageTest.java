package ru.basejava.webapp.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.basejava.webapp.exception.ExistStorageException;
import ru.basejava.webapp.exception.NotExistStorageException;
import ru.basejava.webapp.exception.StorageException;
import ru.basejava.webapp.model.Resume;

public abstract class AbstractArrayStorageTest {
    private Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final Resume RESUME1 = new Resume(UUID_1);
    private static final Resume RESUME2 = new Resume(UUID_2);
    private static final Resume RESUME3 = new Resume(UUID_3);

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME1);
        storage.save(RESUME2);
        storage.save(RESUME3);
    }
    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        Resume newResume = new Resume("uuid1");
        storage.update(newResume);
        Assert.assertSame(newResume, storage.get("uuid1"));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume("dummy"));
    }


    @Test(expected = ExistStorageException.class)
    public void saveAlreadyExist() {
        storage.save(RESUME1);
    }

    @Test
    public void save() {
        Resume resume = new Resume("uuid4");
        storage.save(resume);
        Assert.assertEquals(resume, storage.get("uuid4"));
        Assert.assertEquals(4, storage.size());
    }

    @Test(expected = StorageException.class)
    public void saveOverFlow() throws Exception {
        storage.clear();
        try {
            while (storage.size() != AbstractArrayStorage.STORAGE_LIMIT){
                storage.save(new Resume());
            }
        } catch (StorageException e) {
            Assert.fail("overflow ahead of time");
        }
        storage.save(new Resume("overflow_resume"));
    }

    @Test
    public void get() {
        Assert.assertTrue(storage.get("uuid1").equals(RESUME1));
    }
    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test
    public void delete() {
        Assert.assertEquals(3, storage.size());
        storage.get("uuid1");
        storage.delete("uuid1");
        Assert.assertEquals(2, storage.size());
    }
    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("dummy");
    }

    @Test
    public void getAll() {
        Resume[] resumes = storage.getAll();
        Resume[] resumesToCompare = new Resume[3];
        resumesToCompare[0]= RESUME1;
        resumesToCompare[1]= RESUME2;
        resumesToCompare[2]= RESUME3;
        Assert.assertArrayEquals(resumes, resumesToCompare);
        Assert.assertEquals(3, resumes.length);
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }


}