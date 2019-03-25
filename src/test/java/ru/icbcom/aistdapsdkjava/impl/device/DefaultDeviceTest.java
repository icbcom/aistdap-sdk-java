package ru.icbcom.aistdapsdkjava.impl.device;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.Link;
import ru.icbcom.aistdapsdkjava.api.device.Device;
import ru.icbcom.aistdapsdkjava.api.exception.LinkNotFoundException;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectType;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.objectType.DefaultObjectType;

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

}