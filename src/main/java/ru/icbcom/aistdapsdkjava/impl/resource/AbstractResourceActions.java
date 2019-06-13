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
import ru.icbcom.aistdapsdkjava.api.exception.AistDapBackendException;
import ru.icbcom.aistdapsdkjava.api.exception.LinkNotFoundException;
import ru.icbcom.aistdapsdkjava.api.resource.Resource;
import ru.icbcom.aistdapsdkjava.api.resource.VoidResource;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.utils.LinkUtils;

import java.util.Optional;

public abstract class AbstractResourceActions {

    protected final Link baseLink;
    protected final DataStore dataStore;

    protected AbstractResourceActions(Link baseLink, DataStore dataStore) {
        this.baseLink = baseLink;
        this.dataStore = dataStore;
    }

    protected Link getRootResourceLink(String rel) {
        return getResourceLink(baseLink, rel);
    }

    protected Link getResourceLink(Link parentLink, String rel) {
        VoidResource resource = dataStore.getResource(parentLink, DefaultVoidResource.class);
        return resource.getLink(rel).orElseThrow(() -> new LinkNotFoundException(parentLink.getHref(), rel));
    }

    protected <T extends Resource> Optional<T> getLongIdResource(Link collectionResourceLink, Long id, Class<? extends T> clazz) {
        Link singleResourceLink = LinkUtils.appendLongIdToLink(collectionResourceLink, id);
        return getSingleResource(singleResourceLink, clazz);
    }

    protected <T extends Resource> Optional<T> getSingleResource(Link singleResourceLink, Class<? extends T> clazz) {
        try {
            T resource = dataStore.getResource(singleResourceLink, clazz);
            return Optional.ofNullable(resource);
        } catch (AistDapBackendException e) {
            if (e.getStatus() == 404) {
                return Optional.empty();
            } else {
                throw e;
            }
        }
    }


}
