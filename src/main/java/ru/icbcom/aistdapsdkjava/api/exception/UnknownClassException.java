package ru.icbcom.aistdapsdkjava.api.exception;

import ru.icbcom.aistdapsdkjava.api.resource.Resource;

/**
 * Исключение, используемое при ошибах инстанцирования ресурсов {@link Resource}.
 */
public class UnknownClassException extends AistDapSdkException {
    public UnknownClassException(String message) {
        super(message);
    }

    public UnknownClassException(String message, Throwable cause) {
        super(message, cause);
    }
}
