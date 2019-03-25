package ru.icbcom.aistdapsdkjava.api.exception;

public class NotPersistedException extends AistDapSdkException {
    public NotPersistedException() {
    }

    public NotPersistedException(String message) {
        super(message);
    }

    public NotPersistedException(String message, Throwable cause) {
        super(message, cause);
    }
}
