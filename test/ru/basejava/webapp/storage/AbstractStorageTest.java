package ru.basejava.webapp.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.basejava.ResumeTestData;
import ru.basejava.Config;
import ru.basejava.webapp.exception.ExistStorageException;
import ru.basejava.webapp.exception.NotExistStorageException;
import ru.basejava.webapp.model.ContactType;
import ru.basejava.webapp.model.Resume;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractStorageTest {

    protected static final File STORAGE_DIR = Config.get().getStorageDir();
    protected final Storage storage;
    private static final String UUID_1 = "uuid1";
    protected static final String UUID_2 = "uuid2";
    protected static final String UUID_3 = "uuid3";
    protected static final String UUID_4 = "uuid4";
    protected static final String UUID_NOT_EXIST = "dummy";
    protected static final String FULLNAME_1 = "name1";
    protected static final String FULLNAME_2 = "name2";
    protected static final String FULLNAME_3 = "name3";
    protected static final String FULLNAME_4 = "name4";
    protected static final String FULLNAME_NOT_EXIST = "dummy_name";

    protected static final Resume RESUME1;
    protected static final Resume RESUME2;
    protected static final Resume RESUME3;
    protected static final Resume RESUME4;

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    static {
        RESUME1 = ResumeTestData.resumeCreate(UUID_1, FULLNAME_1);
        RESUME2 = ResumeTestData.resumeCreate(UUID_2, FULLNAME_2);
        RESUME3 = ResumeTestData.resumeCreate(UUID_3, FULLNAME_3);
        RESUME4 = ResumeTestData.resumeCreate(UUID_4, FULLNAME_4);
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
        Assert.assertArrayEquals(storage.getAllSorted().toArray(), expected);
    }

    @Test
    public void update() {
        Resume newResume = new Resume(UUID_1, "New Name");
        newResume.getContacts().put(ContactType.NUMBER, "+7(921) 855-0482");
        newResume.getContacts().put(ContactType.SKYPE, "skype:grigory.kislin");
        newResume.getContacts().put(ContactType.MAIL, "gkislin@yandex.ru");
        newResume.getContacts().put(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin/");
        newResume.getContacts().put(ContactType.GITHUB, "https://github.com/gkislin");
        newResume.getContacts().put(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473/grigory-kislin");
        storage.update(newResume);
        Assert.assertTrue(newResume.equals(storage.get(UUID_1)));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume(UUID_NOT_EXIST, FULLNAME_NOT_EXIST));
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
    public void getAllSorted() {
        List<Resume> allSorted = storage.getAllSorted();
        Assert.assertEquals(3, allSorted.size());
        Assert.assertEquals(Arrays.asList(RESUME1, RESUME2, RESUME3), allSorted);
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