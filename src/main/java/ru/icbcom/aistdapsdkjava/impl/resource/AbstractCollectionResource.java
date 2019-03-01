package ru.icbcom.aistdapsdkjava.impl.resource;

import ru.icbcom.aistdapsdkjava.api.resource.CollectionResource;
import ru.icbcom.aistdapsdkjava.api.resource.Resource;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;

import java.util.Iterator;

public abstract class AbstractCollectionResource<T extends Resource> extends AbstractResource implements CollectionResource<T> {

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public long getTotalElements() {
        return 0;
    }

    @Override
    public long getTotalPages() {
        return 0;
    }

    @Override
    public int getNumber() {
        return 0;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

}
