/*
 * Copyright © 2018-2019 Icbcom
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

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
                AbstractCollectionResource nextResource = resource.getDataStore().getResource(nextLink, this.resource.getClass());
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
