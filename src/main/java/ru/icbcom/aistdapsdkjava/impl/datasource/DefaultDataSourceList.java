package ru.icbcom.aistdapsdkjava.impl.datasource;

import com.fasterxml.jackson.annotation.JacksonInject;
import ru.icbcom.aistdapsdkjava.api.datasource.DataSource;
import ru.icbcom.aistdapsdkjava.api.datasource.DataSourceList;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.resource.AbstractCollectionResource;

public class DefaultDataSourceList extends AbstractCollectionResource<DataSource> implements DataSourceList {
    public DefaultDataSourceList(@JacksonInject DataStore dataStore) {
        super(dataStore);
    }
}