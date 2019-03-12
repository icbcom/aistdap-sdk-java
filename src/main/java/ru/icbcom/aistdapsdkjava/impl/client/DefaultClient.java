package ru.icbcom.aistdapsdkjava.impl.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Link;
import org.springframework.util.Assert;
import ru.icbcom.aistdapsdkjava.api.client.Client;
import ru.icbcom.aistdapsdkjava.api.exception.LinkNotFoundException;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectType;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectTypeCriteria;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectTypeList;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectTypes;
import ru.icbcom.aistdapsdkjava.api.resource.Resource;
import ru.icbcom.aistdapsdkjava.api.resource.ResourceFactory;
import ru.icbcom.aistdapsdkjava.api.resource.VoidResource;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.datastore.DefaultDataStore;
import ru.icbcom.aistdapsdkjava.impl.datastore.auth.AuthenticationServiceFactory;
import ru.icbcom.aistdapsdkjava.impl.datastore.auth.DefaultAuthenticationServiceFactory;
import ru.icbcom.aistdapsdkjava.impl.datastore.objectmapper.DefaultObjectMapperFactory;
import ru.icbcom.aistdapsdkjava.impl.datastore.objectmapper.ObjectMapperFactory;
import ru.icbcom.aistdapsdkjava.impl.datastore.resttemplate.DefaultRestTemplateFactory;
import ru.icbcom.aistdapsdkjava.impl.datastore.resttemplate.RestTemplateFactory;
import ru.icbcom.aistdapsdkjava.impl.objectType.DefaultObjectTypeList;
import ru.icbcom.aistdapsdkjava.impl.resource.DefaultResourceFactory;
import ru.icbcom.aistdapsdkjava.impl.resource.DefaultVoidResource;

// TODO: Рефакториинг и тестирование.

@Slf4j
public class DefaultClient implements Client {

    private final static ObjectMapperFactory objectMapperFactory = new DefaultObjectMapperFactory();
    private final static AuthenticationServiceFactory authenticationServiceFactory = new DefaultAuthenticationServiceFactory();
    private final static RestTemplateFactory restTemplateFactory = new DefaultRestTemplateFactory();

    private final String baseUrl;
    private final DataStore dataStore;
    private final ResourceFactory resourceFactory;

    DefaultClient(String baseUrl, String login, String password) {
        this.baseUrl = baseUrl;
        this.dataStore = new DefaultDataStore(baseUrl, login, password, objectMapperFactory, authenticationServiceFactory, restTemplateFactory);
        this.resourceFactory = new DefaultResourceFactory(this.dataStore);
    }

    @Override
    public <T extends Resource> T instantiate(Class<T> clazz) {
        return resourceFactory.instantiate(clazz);
    }

    @Override
    public ObjectTypeList getObjectTypes() {
        return getObjectTypes(ObjectTypes.criteria());
    }

    @Override
    public ObjectTypeList getObjectTypes(ObjectTypeCriteria criteria) {
        Link objectTypesLink = getRootResourceLink("dap:objectTypes");
        return dataStore.getResource(objectTypesLink, DefaultObjectTypeList.class, criteria);
    }

    @Override
    public ObjectType createObjectType(ObjectType objectType) {
        Link objectTypesLink = getRootResourceLink("dap:objectTypes");
        return dataStore.create(objectTypesLink, objectType);
    }

    private Link getRootResourceLink(String rel) {
        VoidResource rootResource = dataStore.getResource(new Link(baseUrl), DefaultVoidResource.class);
        return rootResource.getLink(rel).orElseThrow(() -> new LinkNotFoundException(rel, baseUrl));
    }

}
