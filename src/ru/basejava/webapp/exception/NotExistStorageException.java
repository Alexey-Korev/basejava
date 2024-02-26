package ru.basejava.webapp.exception;

public class NotExistStorageException extends StorageException{
    public NotExistStorageException(String  uuid) {
        super("Resume " + uuid + " is not in the storage", uuid);
    }
}
