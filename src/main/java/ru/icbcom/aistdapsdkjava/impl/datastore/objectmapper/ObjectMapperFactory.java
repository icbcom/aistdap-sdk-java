package ru.icbcom.aistdapsdkjava.impl.datastore.objectmapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;

public interface ObjectMapperFactory {
    ObjectMapper create(DataStore dataStore);
}
