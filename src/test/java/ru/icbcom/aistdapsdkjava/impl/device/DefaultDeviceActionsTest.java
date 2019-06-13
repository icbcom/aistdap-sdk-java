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

package ru.icbcom.aistdapsdkjava.impl.device;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.Link;
import ru.icbcom.aistdapsdkjava.api.device.Device;
import ru.icbcom.aistdapsdkjava.api.device.DeviceCriteria;
import ru.icbcom.aistdapsdkjava.api.device.DeviceList;
import ru.icbcom.aistdapsdkjava.api.device.Devices;
import ru.icbcom.aistdapsdkjava.api.exception.AistDapBackendException;
import ru.icbcom.aistdapsdkjava.api.exception.LinkNotFoundException;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.error.DefaultError;
import ru.icbcom.aistdapsdkjava.impl.resource.DefaultVoidResource;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultDeviceActionsTest {

    @Mock
    DataStore dataStore;

    private Link baseLink;
    private DefaultDeviceActions deviceActions;

    @BeforeEach
    void setup() {
        baseLink = new Link("http://127.0.0.1/");
        deviceActions = new DefaultDeviceActions(baseLink, dataStore);
    }

    @Test
    void getAllShouldWorkProperly() {
        DefaultVoidResource rootVoidResource = new DefaultVoidResource(dataStore);
        rootVoidResource.add(new Link("http://127.0.0.1/devices", "dap:devices"));
        doReturn(rootVoidResource).when(dataStore).getResource(baseLink, DefaultVoidResource.class);

        DefaultDeviceList deviceListToReturn = new DefaultDeviceList(dataStore);
        doReturn(deviceListToReturn).when(dataStore).getResource(any(), eq(DefaultDeviceList.class), any());

        DeviceList getAllResult = deviceActions.getAll();

        assertSame(deviceListToReturn, getAllResult);
        verify(dataStore).getResource(baseLink, DefaultVoidResource.class);
        verify(dataStore).getResource(eq(new Link("http://127.0.0.1/devices", "dap:devices")), eq(DefaultDeviceList.class), eq(Devices.criteria()));
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void getAllWithCriteriaShouldWorkProperly() {
        DefaultVoidResource rootVoidResource = new DefaultVoidResource(dataStore);
        rootVoidResource.add(new Link("http://127.0.0.1/devices", "dap:devices"));
        doReturn(rootVoidResource).when(dataStore).getResource(baseLink, DefaultVoidResource.class);

        DefaultDeviceList deviceListToReturn = new DefaultDeviceList(dataStore);
        doReturn(deviceListToReturn).when(dataStore).getResource(any(), eq(DefaultDeviceList.class), any());

        DeviceCriteria criteria = Devices.criteria()
                .pageSize(100)
                .orderByName().descending();
        DeviceList getAllResult = deviceActions.getAll(criteria);

        assertSame(deviceListToReturn, getAllResult);
        verify(dataStore).getResource(baseLink, DefaultVoidResource.class);
        verify(dataStore).getResource(eq(new Link("http://127.0.0.1/devices", "dap:devices")), eq(DefaultDeviceList.class), eq(criteria));
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void getByIdShouldWorkProperly() {
        DefaultVoidResource rootVoidResource = new DefaultVoidResource(dataStore);
        rootVoidResource.add(new Link("http://127.0.0.1/devices", "dap:devices"));
        doReturn(rootVoidResource).when(dataStore).getResource(baseLink, DefaultVoidResource.class);

        DefaultDevice deviceToReturn = new DefaultDevice(dataStore);
        doReturn(deviceToReturn).when(dataStore).getResource(new Link("http://127.0.0.1/devices/1", "dap:devices"), DefaultDevice.class);

        Optional<Device> deviceOptional = deviceActions.getById(1L);
        assertTrue(deviceOptional.isPresent());
        assertSame(deviceToReturn, deviceOptional.get());

        verify(dataStore).getResource(baseLink, DefaultVoidResource.class);
        verify(dataStore).getResource(new Link("http://127.0.0.1/devices/1", "dap:devices"), DefaultDevice.class);
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void getByIdExceptionShouldBeThrownWhenThereIsNoLink() {
        DefaultVoidResource rootVoidResource = new DefaultVoidResource(dataStore);
        doReturn(rootVoidResource).when(dataStore).getResource(baseLink, DefaultVoidResource.class);

        LinkNotFoundException exception = assertThrows(LinkNotFoundException.class, () -> deviceActions.getById(1L));
        assertThat(exception, allOf(
                hasProperty("message", is("Link with relation 'dap:devices' was not found in resource with href 'http://127.0.0.1/'")),
                hasProperty("resourceHref", is("http://127.0.0.1/")),
                hasProperty("rel", is("dap:devices"))
        ));
        verify(dataStore).getResource(baseLink, DefaultVoidResource.class);
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void getByIdShouldThrowExceptionWhenStatusCodeIsNot404() {
        DefaultVoidResource rootVoidResource = new DefaultVoidResource(dataStore);
        rootVoidResource.add(new Link("http://127.0.0.1/devices", "dap:devices"));
        doReturn(rootVoidResource).when(dataStore).getResource(baseLink, DefaultVoidResource.class);

        DefaultError defaultError = new DefaultError();
        defaultError.setStatus(406);
        AistDapBackendException exceptionToThrow = new AistDapBackendException(defaultError);
        doThrow(exceptionToThrow).when(dataStore).getResource(new Link("http://127.0.0.1/devices/1", "dap:devices"), DefaultDevice.class);

        AistDapBackendException exception = assertThrows(AistDapBackendException.class, () -> deviceActions.getById(1L));
        assertSame(exceptionToThrow, exception);

        verify(dataStore).getResource(baseLink, DefaultVoidResource.class);
        verify(dataStore).getResource(new Link("http://127.0.0.1/devices/1", "dap:devices"), DefaultDevice.class);
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void createDeviceShouldWorkProperly() {
        DefaultVoidResource rootVoidResource = new DefaultVoidResource(dataStore);
        rootVoidResource.add(new Link("http://127.0.0.1/devices", "dap:devices"));
        doReturn(rootVoidResource).when(dataStore).getResource(baseLink, DefaultVoidResource.class);

        Device deviceToCreate = new DefaultDevice(dataStore);

        DefaultDevice deviceCreationResult = new DefaultDevice(dataStore);
        when(dataStore.create(new Link("http://127.0.0.1/devices", "dap:devices"), deviceToCreate)).thenReturn(deviceCreationResult);

        Device createdDevice = deviceActions.create(deviceToCreate);
        assertSame(deviceCreationResult, createdDevice);

        verify(dataStore).getResource(baseLink, DefaultVoidResource.class);
        verify(dataStore).create(new Link("http://127.0.0.1/devices", "dap:devices"), deviceToCreate);
        verifyNoMoreInteractions(dataStore);
    }

}