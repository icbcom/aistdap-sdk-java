package ru.icbcom.aistdapsdkjava.api.exception;

/**
 * Исключение, используемое при проблемах создания экземпляров ресурсов.
 */
public class InstantiationException extends AistDapSdkException {

    public InstantiationException(String s, Throwable t) {
        super(s, t);
    }
}