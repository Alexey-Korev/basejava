package ru.basejava.webapp.storage;

import ru.basejava.webapp.model.Resume;

import java.util.List;

public class ListStorage extends AbstractStorage{

    protected List<Resume> storage;

    public ListStorage(List<Resume> storage) {
        this.storage = storage;
    }

    @Override
    public void clear() {
        storage.clear();
    }
    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected int getIndex(String uuid) {
        Resume resumeToGetIndex = new Resume(uuid);
        return storage.indexOf(resumeToGetIndex);
    }

    @Override
    protected void toSave(Resume resume, int index) {
        storage.add(resume);
    }

    @Override
    protected Resume toGet(int index) {
        return storage.get(index);
    }

    @Override
    protected void toDelete(int index) {
        storage.remove(index);
    }

    @Override
    protected void toUpdate(Resume resume, int index) {
        storage.set(index, resume);
    }

}
