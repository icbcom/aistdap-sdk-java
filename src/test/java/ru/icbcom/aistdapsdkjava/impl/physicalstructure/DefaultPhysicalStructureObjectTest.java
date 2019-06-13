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

package ru.icbcom.aistdapsdkjava.impl.physicalstructure;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.Link;
import ru.icbcom.aistdapsdkjava.api.device.DeviceCriteria;
import ru.icbcom.aistdapsdkjava.api.device.DeviceList;
import ru.icbcom.aistdapsdkjava.api.device.Devices;
import ru.icbcom.aistdapsdkjava.api.exception.LinkNotFoundException;
import ru.icbcom.aistdapsdkjava.api.exception.NotPersistedException;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectType;
import ru.icbcom.aistdapsdkjava.api.physicalstructure.PhysicalStructureObject;
import ru.icbcom.aistdapsdkjava.api.physicalstructure.PhysicalStructureObjectCriteria;
import ru.icbcom.aistdapsdkjava.api.physicalstructure.PhysicalStructureObjectList;
import ru.icbcom.aistdapsdkjava.api.physicalstructure.PhysicalStructureObjects;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.device.DefaultDeviceList;
import ru.icbcom.aistdapsdkjava.impl.objectType.DefaultObjectType;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultPhysicalStructureObjectTest {

    @Mock
    DataStore dataStore;

    @Test
    void fieldsInitializationShouldWorkProperly() {
        PhysicalStructureObject physicalStructureObject = new DefaultPhysicalStructureObject(dataStore)
                .setId(100L)
                .setObjectTypeId(1L)
                .setName("Название объекта физической структуры")
                .setAttributeValue("Server", "puma.icbcom.ru")
                .setAttributeValue("Port", "2755")
                .setAttributeValue("AdditionalAttribute", "some attribute value")
                .setDescendantsCount(5L)
                .setDevicesCount(3L);

        assertThat(physicalStructureObject, allOf(
                hasProperty("id", is(100L)),
                hasProperty("objectTypeId", is(1L)),
                hasProperty("name", is("Название объекта физической структуры")),
                hasProperty("attributeValues", allOf(
                        hasEntry("Server", "puma.icbcom.ru"),
                        hasEntry("Port", "2755"),
                        hasEntry("AdditionalAttribute", "some attribute value")
                )),
                hasProperty("descendantsCount", is(5L)),
                hasProperty("devicesCount", is(3L))
        ));

        Optional<String> attributeValueOptional = physicalStructureObject.getAttributeValueByName("Server");
        assertTrue(attributeValueOptional.isPresent());
        assertEquals("puma.icbcom.ru", attributeValueOptional.get());
        assertFalse(physicalStructureObject.getAttributeValueByName("SomeAnotherAttribute").isPresent());

        physicalStructureObject.removeAttributeValueByName("AdditionalAttribute");
        assertThat(physicalStructureObject.getAttributeValues(), not(hasEntry("AdditionalAttribute", "some attribute value")));

        assertTrue(physicalStructureObject.hasDescendants());
        assertTrue(physicalStructureObject.hasAttachedDevices());
    }

    @Test
    void saveShouldWorkProperly() {
        PhysicalStructureObject physicalStructureObject = new DefaultPhysicalStructureObject(dataStore);
        physicalStructureObject.save();

        verify(dataStore).save(same(physicalStructureObject));
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void deleteShouldWorkProperly() {
        PhysicalStructureObject physicalStructureObject = new DefaultPhysicalStructureObject(dataStore);
        physicalStructureObject.delete();

        verify(dataStore).delete(same(physicalStructureObject));
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void getObjectTypeShouldWorkProperly() {
        PhysicalStructureObject physicalStructureObject = new DefaultPhysicalStructureObject(dataStore);
        physicalStructureObject.add(new Link("http://127.0.0.1/objectTypes/1", "dap:objectType"));

        DefaultObjectType objectTypeToReturn = new DefaultObjectType(dataStore);
        when(dataStore.getResource(new Link("http://127.0.0.1/objectTypes/1", "dap:objectType"), DefaultObjectType.class)).thenReturn(objectTypeToReturn);

        ObjectType objectType = physicalStructureObject.getObjectType();
        assertSame(objectTypeToReturn, objectType);

        verify(dataStore).getResource(new Link("http://127.0.0.1/objectTypes/1", "dap:objectType"), DefaultObjectType.class);
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void getObjectTypeExceptionShouldBeThrownWhenThereIsNoLink() {
        PhysicalStructureObject physicalStructureObject = new DefaultPhysicalStructureObject(dataStore);

        LinkNotFoundException exception = assertThrows(LinkNotFoundException.class, () -> physicalStructureObject.getObjectType());

        assertThat(exception, allOf(
                hasProperty("message", is("Link 'dap:objectType' was not found in the current PhysicalStructureObject. Method 'getObjectType()' " +
                        "may only be called on PhysicalStructureObjects that have already been persisted and have an existing 'dap:objectType' link.")),
                hasProperty("resourceHref", is(nullValue())),
                hasProperty("rel", is("dap:objectType"))
        ));
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void getDescendantsShouldWorkProperly() {
        PhysicalStructureObject physicalStructureObject = new DefaultPhysicalStructureObject(dataStore);
        physicalStructureObject.add(new Link("http://127.0.0.1:8080/physicalStructure/10027/descendants", "dap:descendants"));

        DefaultPhysicalStructureObjectList physicalStructureObjectListToReturn = new DefaultPhysicalStructureObjectList(dataStore);
        doReturn(physicalStructureObjectListToReturn).when(dataStore).getResource(any(), eq(DefaultPhysicalStructureObjectList.class), any());

        PhysicalStructureObjectList getDescendantsResult = physicalStructureObject.getDescendants();

        assertSame(physicalStructureObjectListToReturn, getDescendantsResult);
        verify(dataStore).getResource(eq(new Link("http://127.0.0.1:8080/physicalStructure/10027/descendants", "dap:descendants")), eq(DefaultPhysicalStructureObjectList.class), eq(PhysicalStructureObjects.criteria()));
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void getDescendantsWithCriteriaShouldWorkProperly() {
        PhysicalStructureObject physicalStructureObject = new DefaultPhysicalStructureObject(dataStore);
        physicalStructureObject.add(new Link("http://127.0.0.1:8080/physicalStructure/10027/descendants", "dap:descendants"));

        DefaultPhysicalStructureObjectList physicalStructureObjectListToReturn = new DefaultPhysicalStructureObjectList(dataStore);
        doReturn(physicalStructureObjectListToReturn).when(dataStore).getResource(any(), eq(DefaultPhysicalStructureObjectList.class), any());

        PhysicalStructureObjectCriteria criteria = PhysicalStructureObjects.criteria()
                .pageSize(100)
                .orderByName().descending();
        PhysicalStructureObjectList getDescendantsResult = physicalStructureObject.getDescendants(criteria);

        assertSame(physicalStructureObjectListToReturn, getDescendantsResult);
        verify(dataStore).getResource(eq(new Link("http://127.0.0.1:8080/physicalStructure/10027/descendants", "dap:descendants")), eq(DefaultPhysicalStructureObjectList.class), eq(criteria));
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void getAttachedDevicesShouldWorkProperly() {
        PhysicalStructureObject physicalStructureObject = new DefaultPhysicalStructureObject(dataStore);
        physicalStructureObject.add(new Link("http://127.0.0.1:8080/physicalStructure/10027/attachedDevices", "dap:attachedDevices"));

        DefaultDeviceList deviceListToReturn = new DefaultDeviceList(dataStore);
        doReturn(deviceListToReturn).when(dataStore).getResource(any(), eq(DefaultDeviceList.class), any());

        DeviceList getAllResult = physicalStructureObject.getAttachedDevices();

        assertSame(deviceListToReturn, getAllResult);
        verify(dataStore).getResource(eq(new Link("http://127.0.0.1:8080/physicalStructure/10027/attachedDevices", "dap:attachedDevices")), eq(DefaultDeviceList.class), eq(Devices.criteria()));
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void getAttachedDevicesWithCriteriaShouldWorkProperly() {
        PhysicalStructureObject physicalStructureObject = new DefaultPhysicalStructureObject(dataStore);
        physicalStructureObject.add(new Link("http://127.0.0.1:8080/physicalStructure/10027/attachedDevices", "dap:attachedDevices"));

        DefaultDeviceList deviceListToReturn = new DefaultDeviceList(dataStore);
        doReturn(deviceListToReturn).when(dataStore).getResource(any(), eq(DefaultDeviceList.class), any());

        DeviceCriteria criteria = Devices.criteria()
                .pageSize(100)
                .orderByName().descending();
        DeviceList getAllResult = physicalStructureObject.getAttachedDevices(criteria);

        assertSame(deviceListToReturn, getAllResult);
        verify(dataStore).getResource(eq(new Link("http://127.0.0.1:8080/physicalStructure/10027/attachedDevices", "dap:attachedDevices")), eq(DefaultDeviceList.class), eq(criteria));
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void createDescendantShouldWorkProperly() {
        PhysicalStructureObject physicalStructureObject = new DefaultPhysicalStructureObject(dataStore);
        physicalStructureObject.add(new Link("http://127.0.0.1:8080/physicalStructure/10027/descendants", "dap:descendants"));

        PhysicalStructureObject physicalStructureObjectToCreate = new DefaultPhysicalStructureObject(dataStore);

        DefaultPhysicalStructureObject physicalStructureObjectCreationResult = new DefaultPhysicalStructureObject(dataStore);
        when(dataStore.create(new Link("http://127.0.0.1:8080/physicalStructure/10027/descendants", "dap:descendants"), physicalStructureObjectToCreate)).thenReturn(physicalStructureObjectCreationResult);

        PhysicalStructureObject createdPhysicalStructureObject = physicalStructureObject.createDescendant(physicalStructureObjectToCreate);
        assertSame(physicalStructureObjectCreationResult, createdPhysicalStructureObject);

        verify(dataStore).create(new Link("http://127.0.0.1:8080/physicalStructure/10027/descendants", "dap:descendants"), physicalStructureObjectToCreate);
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void isInRootShouldWorkProperly() {
        PhysicalStructureObject physicalStructureObject1 = new DefaultPhysicalStructureObject(dataStore);
        physicalStructureObject1.add(new Link("http://127.0.0.1:8080/physicalStructure/1", "self"));
        assertTrue(physicalStructureObject1.isInRoot());

        PhysicalStructureObject physicalStructureObject2 = new DefaultPhysicalStructureObject(dataStore);
        physicalStructureObject2.add(new Link("http://127.0.0.1:8080/physicalStructure/1", "self"));
        physicalStructureObject2.add(new Link("http://127.0.0.1:8080/physicalStructure/2", "dap:parent"));
        assertFalse(physicalStructureObject2.isInRoot());
    }

    @Test
    void getParentShouldWorkProperly() {
        PhysicalStructureObject physicalStructureObject = new DefaultPhysicalStructureObject(dataStore);
        physicalStructureObject.add(new Link("http://127.0.0.1:8080/physicalStructure/1", "self"));
        physicalStructureObject.add(new Link("http://127.0.0.1:8080/physicalStructure/2", "dap:parent"));

        DefaultPhysicalStructureObject physicalStructureObjectToReturn = new DefaultPhysicalStructureObject(dataStore);
        doReturn(physicalStructureObjectToReturn).when(dataStore).getResource(new Link("http://127.0.0.1:8080/physicalStructure/2", "dap:parent"), DefaultPhysicalStructureObject.class);

        Optional<PhysicalStructureObject> physicalStructureObjectOptional = physicalStructureObject.getParent();
        assertTrue(physicalStructureObjectOptional.isPresent());
        assertSame(physicalStructureObjectToReturn, physicalStructureObjectOptional.get());

        verify(dataStore).getResource(new Link("http://127.0.0.1:8080/physicalStructure/2", "dap:parent"), DefaultPhysicalStructureObject.class);
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void getParentWhenInRootShouldWorkProperly() {
        PhysicalStructureObject physicalStructureObject = new DefaultPhysicalStructureObject(dataStore);
        physicalStructureObject.add(new Link("http://127.0.0.1:8080/physicalStructure/1", "self"));

        Optional<PhysicalStructureObject> physicalStructureObjectOptional = physicalStructureObject.getParent();
        assertFalse(physicalStructureObjectOptional.isPresent());

        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void getParentShouldThrowExceptionsWhenObjectNotPersisted() {
        PhysicalStructureObject physicalStructureObject = new DefaultPhysicalStructureObject(dataStore);

        NotPersistedException exception = assertThrows(NotPersistedException.class, physicalStructureObject::getParent);
        assertEquals("There are no any existing links in this PhysicalStructureObject. " +
                "Method 'getParent()' may only be called on PhysicalStructureObjects that have already been persisted.", exception.getMessage());

        verifyNoMoreInteractions(dataStore);
    }

}