package ru.icbcom.aistdapsdkjava.impl.resource;

import ru.icbcom.aistdapsdkjava.api.resource.Deletable;
import ru.icbcom.aistdapsdkjava.api.resource.Savable;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;

public abstract class AbstractInstanceResource extends AbstractResource implements Savable, Deletable {
    protected AbstractInstanceResource(DataStore dataStore) {
        super(dataStore);
    }

    @Override
    public void save() {
        getDataStore().save(this);
    }

    @Override
    public void delete() {
        getDataStore().delete(this);
    }
}
