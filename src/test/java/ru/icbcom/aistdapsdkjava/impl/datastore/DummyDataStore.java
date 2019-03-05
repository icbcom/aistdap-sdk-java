package ru.icbcom.aistdapsdkjava.impl.datastore;

import org.springframework.hateoas.Link;
import ru.icbcom.aistdapsdkjava.api.query.Criteria;
import ru.icbcom.aistdapsdkjava.api.resource.Resource;

public class DummyDataStore implements DataStore {
    @Override
    public <T extends Resource> T getResource(Link link, Class<T> clazz) {
        throw new IllegalStateException("Not implemented");
    }

    @Override
    public <T extends Resource, C extends Criteria> T getResource(Link link, Class<T> clazz, C criteria) {
        throw new IllegalStateException("Not implemented");
    }
}
