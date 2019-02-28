package ru.icbcom.aistdapsdkjava.api.lang;

public class UnknownClassException extends RuntimeException {
    public UnknownClassException(String message) {
        super(message);
    }

    public UnknownClassException(String message, Throwable cause) {
        super(message, cause);
    }
}
