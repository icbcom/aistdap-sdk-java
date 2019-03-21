package ru.icbcom.aistdapsdkjava.impl.device;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.icbcom.aistdapsdkjava.api.device.Device;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

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

}