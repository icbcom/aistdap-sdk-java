package ru.icbcom.aistdapsdkjava.impl.resource;

import ru.icbcom.aistdapsdkjava.api.resource.Savable;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;

public abstract class AbstractSavableResource extends AbstractResource implements Savable {
    protected AbstractSavableResource(DataStore dataStore) {
        super(dataStore);
    }

    @Override
    public void save() {
        getDataStore().save(this);
    }
}
