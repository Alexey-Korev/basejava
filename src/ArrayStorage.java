import java.util.Arrays;

import static java.util.Arrays.copyOfRange;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int lastResumeIndex = 0;

    void clear() {
        Arrays.fill(storage, 0, lastResumeIndex, null);
        lastResumeIndex = 0;
    }

    void save(Resume resume) {
        if (lastResumeIndex == storage.length) {
            System.out.println("Storage is full");
            return;
        }
        int index = getIndex(resume.getUuid());
        if (index > -1) {
            System.out.println("Resume " + resume + " is already in the storage");
        } else {
            storage[lastResumeIndex] = resume;
            lastResumeIndex++;
        }
    }

    Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index > -1) {
            return storage[index];
        }
        System.out.println("Resume " + uuid + " is not in the storage");
        return null;
    }

    void delete(String uuid) {
        int index = getIndex(uuid);
        storage[getIndex(uuid)] = null;
        System.arraycopy(storage, index + 1, storage, index, size());
        lastResumeIndex--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return copyOfRange(storage, 0, lastResumeIndex);
    }

    int size() {
        return lastResumeIndex;
    }

    private int getIndex(String uuid) {
        for (int i = 0; i < lastResumeIndex; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
