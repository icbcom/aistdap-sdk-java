package ru.icbcom.aistdapsdkjava.impl.objectType;

import com.fasterxml.jackson.annotation.JacksonInject;
import ru.icbcom.aistdapsdkjava.integrationtest.objecttype.ObjectType;
import ru.icbcom.aistdapsdkjava.integrationtest.objecttype.ObjectTypeList;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.resource.AbstractCollectionResource;

public class DefaultObjectTypeList extends AbstractCollectionResource<ObjectType> implements ObjectTypeList {
    public DefaultObjectTypeList(@JacksonInject DataStore dataStore) {
        super(dataStore);
    }
}
