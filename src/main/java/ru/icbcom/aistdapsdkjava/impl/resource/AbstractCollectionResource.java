package ru.icbcom.aistdapsdkjava.impl.resource;

import ru.icbcom.aistdapsdkjava.api.resource.CollectionResource;
import ru.icbcom.aistdapsdkjava.api.resource.Resource;

import java.util.Iterator;

public abstract class AbstractCollectionResource<T extends Resource, C extends CollectionResource<T>> extends AbstractResource implements CollectionResource<T> {

    // Что находится здесь?
    // Это аналог PagedResource.

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
    public boolean hasFirst() {
        return hasLink("first");
    }

    @Override
    public boolean hasPrevious() {
        return hasLink("prev");
    }

    @Override
    public boolean hasNext() {
        return hasLink("next");
    }

    @Override
    public boolean hasLast() {
        return hasLink("last");
    }

    @Override
    public C first() {
        return null;
    }

    @Override
    public C previous() {
        return null;
    }

    @Override
    public C next() {
        return null;
    }

    @Override
    public C last() {
        return null;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

}
