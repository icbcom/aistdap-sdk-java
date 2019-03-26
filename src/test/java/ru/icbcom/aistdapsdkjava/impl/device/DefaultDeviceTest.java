package ru.icbcom.aistdapsdkjava.impl.device;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.Link;
import ru.icbcom.aistdapsdkjava.api.device.Device;
import ru.icbcom.aistdapsdkjava.api.exception.AistDapSdkException;
import ru.icbcom.aistdapsdkjava.api.exception.LinkNotFoundException;
import ru.icbcom.aistdapsdkjava.api.exception.NotPersistedException;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectType;
import ru.icbcom.aistdapsdkjava.api.physicalstructure.PhysicalStructureObject;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.objectType.DefaultObjectType;
import ru.icbcom.aistdapsdkjava.impl.physicalstructure.DefaultPhysicalStructureObject;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
    class DefaultDeviceTest {

        @Mock
        DataStore dataStore;

    @Test
    void fieldsInitializationShouldWorkProperly() {
        Device device = new DefaultDevice(dataStore)
                .setId(100L)
                .setObjectTypeId(1L)
                .setName("Название устройства")
                .setAttributeValue("Server", "puma.icbcom.ru")
                .setAttributeValue("Port", "2755")
                .setAttributeValue("AdditionalAttribute", "some attribute value");

        assertThat(device, allOf(
                hasProperty("id", is(100L)),
                hasProperty("objectTypeId", is(1L)),
                hasProperty("name", is("Название устройства")),
                hasProperty("attributeValues", allOf(
                        hasEntry("Server", "puma.icbcom.ru"),
                        hasEntry("Port", "2755"),
                        hasEntry("AdditionalAttribute", "some attribute value")
                ))
        ));

        Optional<String> attributeValueOptional = device.getAttributeValueByName("Server");
        assertTrue(attributeValueOptional.isPresent());
        assertEquals("puma.icbcom.ru", attributeValueOptional.get());
        assertFalse(device.getAttributeValueByName("SomeAnotherAttribute").isPresent());

        device.removeAttributeValueByName("AdditionalAttribute");
        assertThat(device.getAttributeValues(), not(hasEntry("AdditionalAttribute", "some attribute value")));
    }

    @Test
    void saveShouldWorkProperly() {
        Device device = new DefaultDevice(dataStore);
        device.save();

        verify(dataStore).save(same(device));
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void deleteShouldWorkProperly() {
        Device device = new DefaultDevice(dataStore);
        device.delete();

        verify(dataStore).delete(same(device));
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void getObjectTypeShouldWorkProperly() {
        Device device = new DefaultDevice(dataStore);
        device.add(new Link("http://127.0.0.1/objectTypes/1", "dap:objectType"));

        DefaultObjectType objectTypeToReturn = new DefaultObjectType(dataStore);
        when(dataStore.getResource(new Link("http://127.0.0.1/objectTypes/1", "dap:objectType"), DefaultObjectType.class)).thenReturn(objectTypeToReturn);

        ObjectType objectType = device.getObjectType();
        assertSame(objectTypeToReturn, objectType);

        verify(dataStore).getResource(new Link("http://127.0.0.1/objectTypes/1", "dap:objectType"), DefaultObjectType.class);
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void getObjectTypeExceptionShouldBeThrownWhenThereIsNoLink() {
        Device device = new DefaultDevice(dataStore);

        LinkNotFoundException exception = assertThrows(LinkNotFoundException.class, () -> device.getObjectType());

        assertThat(exception, allOf(
                hasProperty("message", is("Link 'dap:objectType' was not found in the current Device object. Method 'getObjectType()' " +
                        "may only be called on Device objects that have already been persisted and have an existing 'dap:objectType' link.")),
                hasProperty("resourceHref", is(nullValue())),
                hasProperty("rel", is("dap:objectType"))
        ));
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void isAttachedShouldWorkProperly() {
        Device device = new DefaultDevice(dataStore);
        device.add(new Link("http://127.0.0.1:8080/devices/1", "self"));
        assertFalse(device.isAttached());
        device.add(new Link("http://127.0.0.1:8080/physicalStructure/10032", "dap:physicalStructureObjectAttachedTo"));
        assertTrue(device.isAttached());
    }

    @Test
    void getPhysicalStructureObjectDeviceAttachedToShouldWorkProperly() {
        Device device = new DefaultDevice(dataStore);
        device.add(new Link("http://127.0.0.1:8080/devices/1", "self"));
        device.add(new Link("http://127.0.0.1:8080/physicalStructure/10032", "dap:physicalStructureObjectAttachedTo"));

        DefaultPhysicalStructureObject physicalStructureObjectToReturn = new DefaultPhysicalStructureObject(dataStore);
        doReturn(physicalStructureObjectToReturn).when(dataStore).getResource(new Link("http://127.0.0.1:8080/physicalStructure/10032", "dap:physicalStructureObjectAttachedTo"), DefaultPhysicalStructureObject.class);

        Optional<PhysicalStructureObject> physicalStructureObjectOptional = device.getPhysicalStructureObjectDeviceAttachedTo();
        assertTrue(physicalStructureObjectOptional.isPresent());
        assertSame(physicalStructureObjectToReturn, physicalStructureObjectOptional.get());

        verify(dataStore).getResource(new Link("http://127.0.0.1:8080/physicalStructure/10032", "dap:physicalStructureObjectAttachedTo"), DefaultPhysicalStructureObject.class);
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void getPhysicalStructureObjectDeviceAttachedToWhenNotAttachedShouldWorkProperly() {
        Device device = new DefaultDevice(dataStore);
        device.add(new Link("http://127.0.0.1:8080/devices/1", "self"));

        Optional<PhysicalStructureObject> physicalStructureObjectOptional = device.getPhysicalStructureObjectDeviceAttachedTo();
        assertFalse(physicalStructureObjectOptional.isPresent());

        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void getPhysicalStructureObjectDeviceAttachedToShouldThrowExceptionsWhenObjectNotPersisted() {
        Device device = new DefaultDevice(dataStore);

        NotPersistedException exception = assertThrows(NotPersistedException.class, device::getPhysicalStructureObjectDeviceAttachedTo);
        assertEquals("There are no any existing links in this Device object. " +
                "Method 'getPhysicalStructureObjectDeviceAttachedTo()' may only be called on Device objects that have already been persisted.", exception.getMessage());

        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void detachShouldWorkProperly() {
        Device device = new DefaultDevice(dataStore);
        device.add(new Link("http://127.0.0.1:8080/devices/1", "self"));
        device.add(new Link("http://127.0.0.1:8080/devices/10033/detach", "dap:detach"));

        device.detach();

        verify(dataStore).callMethod(new Link("http://127.0.0.1:8080/devices/10033/detach", "dap:detach"));
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void detachWhenNotPersistedShouldThrowException() {
        Device device = new DefaultDevice(dataStore);

        NotPersistedException exception = assertThrows(NotPersistedException.class, device::detach);
        assertEquals("There are no any existing links in this Device object. " +
                "Method 'detach()' may only be called on Device objects that have already been persisted.", exception.getMessage());
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void detachWhenNotAttachedShouldThrowException() {
        Device device = new DefaultDevice(dataStore);
        device.add(new Link("http://127.0.0.1:8080/devices/1", "self"));

        AistDapSdkException exception = assertThrows(AistDapSdkException.class, device::detach);
        assertEquals("Device is not attached to any physical structure object", exception.getMessage());
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void attachShouldWorkProperly() {
        Device device = new DefaultDevice(dataStore);
        device.add(new Link("http://127.0.0.1:8080/devices/1", "self"));
        device.add(new Link("http://127.0.0.1:8080/devices/10033/attach", "dap:attach"));

        device.attach(123L);

        AttachDeviceMethodArgumentResource methodArgument = new DefaultAttachDeviceMethodArgumentResource(dataStore).setPhysicalStructureObjectId(123L);
        verify(dataStore).callMethod(new Link("http://127.0.0.1:8080/devices/10033/attach", "dap:attach"), methodArgument);
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void attachWhenNotPersistedShouldThrowException() {
        Device device = new DefaultDevice(dataStore);

        NotPersistedException exception = assertThrows(NotPersistedException.class, () -> device.attach(123L));
        assertEquals("There are no any existing links in this Device object. " +
                "Method 'attach()' may only be called on Device objects that have already been persisted.", exception.getMessage());
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void attachWhenAlreadyAttachedShouldThrowException() {
        Device device = new DefaultDevice(dataStore);
        device.add(new Link("http://127.0.0.1:8080/devices/1", "self"));

        AistDapSdkException exception = assertThrows(AistDapSdkException.class, () -> device.attach(123L));
        assertEquals("Device is already attached to physical structure object", exception.getMessage());
        verifyNoMoreInteractions(dataStore);
    }

}