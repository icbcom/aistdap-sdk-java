package ru.icbcom.aistdapsdkjava.api.exception;

/**
 * Базовое исключение используемое при работе с SDK платформы AistDap.
 */
public class AistDapSdkException extends RuntimeException {
    public AistDapSdkException() {
    }

    public AistDapSdkException(String message) {
        super(message);
    }

    public AistDapSdkException(String message, Throwable cause) {
        super(message, cause);
    }
}
