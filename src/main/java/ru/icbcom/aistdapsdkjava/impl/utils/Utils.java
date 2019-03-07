package ru.icbcom.aistdapsdkjava.impl.utils;

import ru.icbcom.aistdapsdkjava.api.exception.AistDapSdkException;

public final class Utils {

    public static void assertResourceNotNull(Object resource, String message) {
        if (resource == null) {
            throw new AistDapSdkException(message);
        }
    }

}
