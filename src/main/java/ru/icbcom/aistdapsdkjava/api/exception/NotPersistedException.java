package ru.icbcom.aistdapsdkjava.api.exception;

import ru.icbcom.aistdapsdkjava.api.resource.Resource;

/**
 * Исключение, используемое при попытках вызвать методы на ресурсах {@link Resource}, которые не были сохранены в
 * платформе (т.е. не persisted ресурсы).
 */
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
