package ru.basejava.webapp.exception;

public class ExistStorageException extends StorageException{
    public ExistStorageException(String  uuid) {
        super("Resume " + uuid + " is already in the storage", uuid);
    }
}
