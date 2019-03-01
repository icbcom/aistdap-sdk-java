package ru.icbcom.aistdapsdkjava.impl.datastore;

import ru.icbcom.aistdapsdkjava.api.resource.Resource;

public class DefaultDataStore implements DataStore {
    @Override
    public <T extends Resource> T getResource(String href, Class<T> clazz) {
        // Получение ресурса заданного класса по заданной ссылке.


        return null;
    }
}
