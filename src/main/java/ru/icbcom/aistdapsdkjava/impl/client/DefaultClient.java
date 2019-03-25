package ru.icbcom.aistdapsdkjava.impl.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Link;
import ru.icbcom.aistdapsdkjava.api.client.Client;
import ru.icbcom.aistdapsdkjava.api.device.DeviceActions;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectTypeActions;
import ru.icbcom.aistdapsdkjava.api.physicalstructure.PhysicalStructureObjectActions;
import ru.icbcom.aistdapsdkjava.api.resource.Resource;
import ru.icbcom.aistdapsdkjava.impl.device.DefaultDeviceActions;
import ru.icbcom.aistdapsdkjava.impl.physicalstructure.DefaultPhysicalStructureObjectActions;
import ru.icbcom.aistdapsdkjava.impl.resource.ResourceFactory;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.datastore.DefaultDataStore;
import ru.icbcom.aistdapsdkjava.impl.datastore.auth.AuthenticationServiceFactory;
import ru.icbcom.aistdapsdkjava.impl.datastore.auth.DefaultAuthenticationServiceFactory;
import ru.icbcom.aistdapsdkjava.impl.datastore.objectmapper.DefaultObjectMapperFactory;
import ru.icbcom.aistdapsdkjava.impl.datastore.objectmapper.ObjectMapperFactory;
import ru.icbcom.aistdapsdkjava.impl.datastore.resttemplate.DefaultRestTemplateFactory;
import ru.icbcom.aistdapsdkjava.impl.datastore.resttemplate.RestTemplateFactory;
import ru.icbcom.aistdapsdkjava.impl.objectType.DefaultObjectTypeActions;
import ru.icbcom.aistdapsdkjava.impl.resource.DefaultResourceFactory;

// TODO: Рефакториинг и тестирование.

@Slf4j
public class DefaultClient implements Client {

    private final static ObjectMapperFactory objectMapperFactory = new DefaultObjectMapperFactory();
    private final static AuthenticationServiceFactory authenticationServiceFactory = new DefaultAuthenticationServiceFactory();
    private final static RestTemplateFactory restTemplateFactory = new DefaultRestTemplateFactory();

    private final DataStore dataStore;
    private final ResourceFactory resourceFactory;
    private final ObjectTypeActions objectTypeActions;
    private final DeviceActions deviceActions;
    private final PhysicalStructureObjectActions physicalStructureObjectActions;

    DefaultClient(String baseUrl, String login, String password) {
        this.dataStore = new DefaultDataStore(baseUrl, login, password, objectMapperFactory, authenticationServiceFactory, restTemplateFactory);
        this.resourceFactory = new DefaultResourceFactory(this.dataStore);
        this.objectTypeActions = new DefaultObjectTypeActions(new Link(baseUrl), dataStore);
        this.deviceActions = new DefaultDeviceActions(new Link(baseUrl), dataStore);
        this.physicalStructureObjectActions = new DefaultPhysicalStructureObjectActions(new Link(baseUrl), dataStore);
    }

    @Override
    public <T extends Resource> T instantiate(Class<T> clazz) {
        return resourceFactory.instantiate(clazz);
    }

    @Override
    public ObjectTypeActions objectTypes() {
        return objectTypeActions;
    }

    @Override
    public DeviceActions devices() {
        return deviceActions;
    }

    @Override
    public PhysicalStructureObjectActions physicalStructure() {
        return physicalStructureObjectActions;
    }
}
