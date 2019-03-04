package ru.icbcom.aistdapsdkjava.impl.resource;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.hateoas.Link;
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

    // TODO: Протестировать данный ииитератор.

    private class PaginatedIterator<T extends Resource> implements Iterator<T> {

        private AbstractCollectionResource<T> resource;
        private Iterator<T> currentPageIterator;
        private int currentItemIndex;

        public PaginatedIterator(AbstractCollectionResource<T> resource) {
            this.resource = resource;
            this.currentPageIterator = resource.pagedResources.iterator();
            this.currentItemIndex = 0;
        }

        @Override
        public boolean hasNext() {
            boolean hasNext = currentPageIterator.hasNext();

            if (!hasNext) {

                if (resource.hasLink("next")) {
                    hasNext = true;

                    Link nextLink = resource.getLink("next").orElseThrow();

                    AbstractCollectionResource nextResource = getDataStore().getResource(nextLink.expand().getHref(), this.resource.getClass());
                    Iterator<T> nextIterator = nextResource.pagedResources.iterator();

                    if (nextIterator.hasNext()) {
                        hasNext = true;
                        this.resource = nextResource;
                        this.currentPageIterator = nextIterator;
                        this.currentItemIndex = 0;
                    }

                }


            }

            return hasNext;
        }


        @Override
        public T next() {
            T item = currentPageIterator.next();
            currentItemIndex++;
            return item;
        }

    }

}
