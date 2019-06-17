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

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.hateoas.PagedResources;
import ru.icbcom.aistdapsdkjava.api.resource.CollectionResource;
import ru.icbcom.aistdapsdkjava.api.resource.Resource;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;

import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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
    public Stream<T> stream() {
        return StreamSupport.stream(spliterator(), false);
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
