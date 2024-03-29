package ru.basejava.webapp.storage;

import org.junit.Assert;
import org.junit.Test;
import ru.basejava.webapp.exception.StorageException;
import ru.basejava.webapp.model.Resume;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest{

    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveOverFlow() {
        storage.clear();
        try {
            while (storage.size() != AbstractArrayStorage.STORAGE_LIMIT) {
                storage.save(new Resume("someName"));
            }
        } catch (StorageException e) {
            Assert.fail("overflow ahead of time");
        }
        storage.save(new Resume("overflow_resume", "overflow_resume"));
    }
}