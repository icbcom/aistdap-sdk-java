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
