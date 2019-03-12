package ru.icbcom.aistdapsdkjava.impl.datastore;

        import org.springframework.hateoas.Link;
        import ru.icbcom.aistdapsdkjava.api.query.Criteria;
        import ru.icbcom.aistdapsdkjava.api.resource.Resource;
        import ru.icbcom.aistdapsdkjava.api.resource.Savable;

public class DummyDataStore implements DataStore {
    @Override
    public <T extends Resource> T getResource(Link link, Class<T> clazz) {
        throw new IllegalStateException("Not implemented");
    }

    @Override
    public <T extends Resource, C extends Criteria> T getResource(Link link, Class<T> clazz, C criteria) {
        throw new IllegalStateException("Not implemented");
    }

    @Override
    public <T extends Resource> T create(Link parentLink, T resource) {
        throw new IllegalStateException("Not implemented");
    }

    @Override
    public <T extends Resource & Savable> T save(T resource) {
        throw new IllegalStateException("Not implemented");
    }

    @Override
    public <T extends Resource> void delete(T resource) {
        throw new IllegalStateException("Not implemented");
    }
}
