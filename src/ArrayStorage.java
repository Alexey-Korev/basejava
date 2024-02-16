import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int countResumes = 0;

    void clear() {
        Arrays.fill(storage, 0, countResumes, null);
        countResumes = 0;
    }

    void save(Resume resume) {
        if (countResumes >= storage.length) {
            System.out.println("Storage is full");
            return;
        }
        int index = getIndex(resume.getUuid());
        if (index > -1) {
            System.out.println("Resume " + resume + " is already in the storage");
        } else {
            storage[countResumes++] = resume;
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
        if (index > -1 && index <= countResumes) {
                System.arraycopy(storage, index + 1, storage, index, --countResumes - index);
            storage[countResumes] = null;
        } else {
            System.out.println("Resume " + uuid + " is not in the storage");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage,  countResumes);
    }

    int size() {
        return countResumes;
    }

    private int getIndex(String uuid) {
        for (int i = 0; i < countResumes; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
