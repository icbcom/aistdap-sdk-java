package ru.icbcom.aistdapsdkjava.impl.client;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Link;
import ru.icbcom.aistdapsdkjava.api.client.Client;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectTypeCriteria;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectTypeList;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectTypes;
import ru.icbcom.aistdapsdkjava.api.resource.Resource;
import ru.icbcom.aistdapsdkjava.api.resource.ResourceFactory;
import ru.icbcom.aistdapsdkjava.api.resource.VoidResource;
import ru.icbcom.aistdapsdkjava.impl.auth.DefaultAuthenticationKey;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.datastore.DefaultDataStore;
import ru.icbcom.aistdapsdkjava.impl.objectType.DefaultObjectTypeList;
import ru.icbcom.aistdapsdkjava.impl.resource.DefaultResourceFactory;
import ru.icbcom.aistdapsdkjava.impl.resource.DefaultVoidResource;

@Slf4j
public class DefaultClient implements Client {

    private final String baseUrl;
    private final DataStore dataStore;
    private final ResourceFactory resourceFactory;

    public DefaultClient(String baseUrl, String login, String password) {
        this.baseUrl = baseUrl;
        this.dataStore = new DefaultDataStore(baseUrl, new DefaultAuthenticationKey(login, password));
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

    // TODO: Рефакториинг и тестирование.

    @Override
    @SneakyThrows
    public ObjectTypeList getObjectTypes(ObjectTypeCriteria criteria) {
        // Получение корневой страницы.
        VoidResource rootResource = dataStore.getResource(new Link(baseUrl), DefaultVoidResource.class);
        Link objectTypesLink = rootResource.getLink("dap:objectTypes").orElseThrow();

        // Получение списка типов объектов.
        return dataStore.getResource(objectTypesLink, DefaultObjectTypeList.class, criteria);
    }

}
