package ru.icbcom.aistdapsdkjava.impl.datastore;

import org.springframework.hateoas.Link;
import ru.icbcom.aistdapsdkjava.api.query.Criteria;
import ru.icbcom.aistdapsdkjava.api.resource.Resource;
import ru.icbcom.aistdapsdkjava.api.resource.Savable;

public interface DataStore {

    <T extends Resource> T getResource(Link link, Class<T> clazz);

    <T extends Resource, C extends Criteria> T getResource(Link link, Class<T> clazz, C criteria);

    <T extends Resource> T create(Link parentLink, T resource);

    <T extends Resource & Savable> T save(T resource);

    <T extends Resource> void delete(T resource);

}
