package ru.icbcom.aistdapsdkjava.impl.resource;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.hateoas.PagedResources;
import ru.icbcom.aistdapsdkjava.api.resource.CollectionResource;
import ru.icbcom.aistdapsdkjava.api.resource.Resource;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;

import java.util.Iterator;

public abstract class AbstractCollectionResource<T extends Resource> extends AbstractResource implements CollectionResource<T> {

    @JsonUnwrapped
    private PagedResources<T> pagedResources;

    public AbstractCollectionResource(DataStore dataStore) {
        super(dataStore);
    }

    @Override
    public long getSize() {
        return pagedResources.getMetadata().getSize();
    }

    @Override
    public long getTotalElements() {
        return pagedResources.getMetadata().getTotalElements();
    }

    @Override
    public long getTotalPages() {
        return pagedResources.getMetadata().getTotalPages();
    }

    @Override
    public long getNumber() {
        return pagedResources.getMetadata().getNumber();
    }

    @Override
    public Iterator<T> iterator() {
        return new PaginatedIterator<>(this);
    }

    PagedResources<T> getPagedResources() {
        return pagedResources;
    }

    void setPagedResources(PagedResources<T> pagedResources) {
        this.pagedResources = pagedResources;
    }
}
