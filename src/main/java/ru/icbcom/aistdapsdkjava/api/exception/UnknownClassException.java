package ru.icbcom.aistdapsdkjava.api.exception;

/**
 *
 */
public class UnknownClassException extends RuntimeException {
    public UnknownClassException(String message) {
        super(message);
    }

    public UnknownClassException(String message, Throwable cause) {
        super(message, cause);
    }
}
