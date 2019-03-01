package ru.icbcom.aistdapsdkjava.impl.datastore;

import ru.icbcom.aistdapsdkjava.api.resource.Resource;

public interface DataStore {

    <T extends Resource> T getResource(String href, Class<T> clazz);

}
