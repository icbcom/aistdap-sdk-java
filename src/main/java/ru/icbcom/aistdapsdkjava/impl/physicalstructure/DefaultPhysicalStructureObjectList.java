package ru.icbcom.aistdapsdkjava.impl.physicalstructure;

import com.fasterxml.jackson.annotation.JacksonInject;
import ru.icbcom.aistdapsdkjava.api.physicalstructure.PhysicalStructureObject;
import ru.icbcom.aistdapsdkjava.api.physicalstructure.PhysicalStructureObjectList;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.resource.AbstractCollectionResource;

public class DefaultPhysicalStructureObjectList extends AbstractCollectionResource<PhysicalStructureObject> implements PhysicalStructureObjectList {
    public DefaultPhysicalStructureObjectList(@JacksonInject DataStore dataStore) {
        super(dataStore);
    }
}
