package ru.icbcom.aistdapsdkjava.impl.datastore;

import ru.icbcom.aistdapsdkjava.api.resource.Resource;

public class DummyDataStore implements DataStore {
    @Override
    public <T extends Resource> T getResource(String href, Class<T> clazz) {
        throw new IllegalStateException("Not implemented");
    }
}
