package ru.icbcom.aistdapsdkjava.impl.resource;

import org.springframework.hateoas.Link;
import ru.icbcom.aistdapsdkjava.api.resource.Resource;

import java.util.Iterator;

class PaginatedIterator<T extends Resource> implements Iterator<T> {

    private AbstractCollectionResource<T> resource;
    private Iterator<T> currentPageIterator;

    PaginatedIterator(AbstractCollectionResource<T> resource) {
        this.resource = resource;
        this.currentPageIterator = resource.getPagedResources().iterator();
    }

    @Override
    public boolean hasNext() {
        boolean hasNext = currentPageIterator.hasNext();

        if (!hasNext) {
            if (resource.hasLink("next")) {
                Link nextLink = resource.getLink("next").orElseThrow();

                AbstractCollectionResource nextResource = resource.getDataStore().getResource(nextLink.expand().getHref(), this.resource.getClass());
                Iterator<T> nextIterator = nextResource.getPagedResources().iterator();
                if (nextIterator.hasNext()) {
                    hasNext = true;
                    this.resource = nextResource;
                    this.currentPageIterator = nextIterator;
                }
            }
        }

        return hasNext;
    }

    @Override
    public T next() {
        return currentPageIterator.next();
    }

}
