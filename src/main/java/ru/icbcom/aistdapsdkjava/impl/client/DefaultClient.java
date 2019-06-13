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

package ru.icbcom.aistdapsdkjava.impl.client;

import org.springframework.hateoas.Link;
import ru.icbcom.aistdapsdkjava.api.client.Client;
import ru.icbcom.aistdapsdkjava.api.device.DeviceActions;
import ru.icbcom.aistdapsdkjava.api.measureddata.MeasuredDataActions;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectTypeActions;
import ru.icbcom.aistdapsdkjava.api.physicalstructure.PhysicalStructureObjectActions;
import ru.icbcom.aistdapsdkjava.api.resource.Resource;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.datastore.DefaultDataStore;
import ru.icbcom.aistdapsdkjava.impl.datastore.auth.AuthenticationServiceFactory;
import ru.icbcom.aistdapsdkjava.impl.datastore.auth.DefaultAuthenticationServiceFactory;
import ru.icbcom.aistdapsdkjava.impl.datastore.objectmapper.DefaultObjectMapperFactory;
import ru.icbcom.aistdapsdkjava.impl.datastore.objectmapper.ObjectMapperFactory;
import ru.icbcom.aistdapsdkjava.impl.datastore.resttemplate.DefaultRestTemplateFactory;
import ru.icbcom.aistdapsdkjava.impl.datastore.resttemplate.RestTemplateFactory;
import ru.icbcom.aistdapsdkjava.impl.device.DefaultDeviceActions;
import ru.icbcom.aistdapsdkjava.impl.measureddata.DefaultMeasuredDataActions;
import ru.icbcom.aistdapsdkjava.impl.objectType.DefaultObjectTypeActions;
import ru.icbcom.aistdapsdkjava.impl.physicalstructure.DefaultPhysicalStructureObjectActions;
import ru.icbcom.aistdapsdkjava.impl.resource.DefaultResourceFactory;
import ru.icbcom.aistdapsdkjava.impl.resource.ResourceFactory;

// TODO: Рефакториинг и тестирование.

public class DefaultClient implements Client {

    private final static ObjectMapperFactory objectMapperFactory = new DefaultObjectMapperFactory();
    private final static AuthenticationServiceFactory authenticationServiceFactory = new DefaultAuthenticationServiceFactory();
    private final static RestTemplateFactory restTemplateFactory = new DefaultRestTemplateFactory();

    private final DataStore dataStore;
    private final ResourceFactory resourceFactory;
    private final ObjectTypeActions objectTypeActions;
    private final DeviceActions deviceActions;
    private final PhysicalStructureObjectActions physicalStructureObjectActions;
    private final MeasuredDataActions measuredDataActions;

    DefaultClient(String baseUrl, String login, String password) {
        this.dataStore = new DefaultDataStore(baseUrl, login, password, objectMapperFactory, authenticationServiceFactory, restTemplateFactory);
        this.resourceFactory = new DefaultResourceFactory(this.dataStore);
        this.objectTypeActions = new DefaultObjectTypeActions(new Link(baseUrl), dataStore);
        this.deviceActions = new DefaultDeviceActions(new Link(baseUrl), dataStore);
        this.physicalStructureObjectActions = new DefaultPhysicalStructureObjectActions(new Link(baseUrl), dataStore);
        this.measuredDataActions = new DefaultMeasuredDataActions(new Link(baseUrl), dataStore);
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

    @Override
    public MeasuredDataActions measuredData() {
        return measuredDataActions;
    }
}
