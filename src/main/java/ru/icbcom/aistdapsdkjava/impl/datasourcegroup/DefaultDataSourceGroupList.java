package ru.icbcom.aistdapsdkjava.impl.datasourcegroup;

import com.fasterxml.jackson.annotation.JacksonInject;
import ru.icbcom.aistdapsdkjava.api.datasourcegroup.DataSourceGroup;
import ru.icbcom.aistdapsdkjava.api.datasourcegroup.DataSourceGroupList;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.resource.AbstractCollectionResource;

public class DefaultDataSourceGroupList extends AbstractCollectionResource<DataSourceGroup> implements DataSourceGroupList {
    public DefaultDataSourceGroupList(@JacksonInject DataStore dataStore) {
        super(dataStore);
    }
}
