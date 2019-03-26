package ru.icbcom.aistdapsdkjava.impl.device;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import ru.icbcom.aistdapsdkjava.api.device.Device;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.datastore.DummyDataStore;
import ru.icbcom.aistdapsdkjava.impl.datastore.objectmapper.DefaultObjectMapperFactory;

class DefaultDeviceSerializationTest {

    private DataStore dataStore;
    private ObjectMapper objectMapper;

    public DefaultDeviceSerializationTest() {
        this.dataStore = new DummyDataStore();
        this.objectMapper = new DefaultObjectMapperFactory().create(dataStore);
    }

    @Test
    void serializationShouldWorkProperly() throws JsonProcessingException, JSONException {
        Device device = new DefaultDevice(dataStore)
                .setId(100L)
                .setObjectTypeId(1L)
                .setName("Название устройства")
                .setAttributeValue("Server", "puma.icbcom.ru")
                .setAttributeValue("Port", "2755")
                .setAttributeValue("AdditionalAttribute", "some attribute value");

        String expected =
                "{" +
                        "   \"id\": 100," +
                        "   \"objectTypeId\": 1," +
                        "   \"name\": \"Название устройства\"," +
                        "   \"attributeValues\": {" +
                        "       \"Server\": \"puma.icbcom.ru\"," +
                        "       \"Port\": \"2755\"," +
                        "       \"AdditionalAttribute\": \"some attribute value\"" +
                        "   }" +
                        "}";
        JSONAssert.assertEquals(expected, objectMapper.writeValueAsString(device), true);
    }

    @Test
    void emptyAttributeValuesSerializationShouldWorkProperly() throws JsonProcessingException, JSONException {
        Device device = new DefaultDevice(dataStore)
                .setId(100L)
                .setObjectTypeId(1L)
                .setName("Название устройства");

        String expected =
                "{" +
                        "   \"id\": 100," +
                        "   \"objectTypeId\": 1," +
                        "   \"name\": \"Название устройства\"," +
                        "   \"attributeValues\": {}" +
                        "}";
        JSONAssert.assertEquals(expected, objectMapper.writeValueAsString(device), true);
    }

    @Test
    void nullIdShouldNotBeSerialized() throws JsonProcessingException, JSONException {
        Device device = new DefaultDevice(dataStore)
                .setObjectTypeId(1L)
                .setName("Название устройства")
                .setAttributeValue("Server", "puma.icbcom.ru")
                .setAttributeValue("Port", "2755")
                .setAttributeValue("AdditionalAttribute", "some attribute value");

        String expected =
                "{" +
                        "   \"objectTypeId\": 1," +
                        "   \"name\": \"Название устройства\"," +
                        "   \"attributeValues\": {" +
                        "       \"Server\": \"puma.icbcom.ru\"," +
                        "       \"Port\": \"2755\"," +
                        "       \"AdditionalAttribute\": \"some attribute value\"" +
                        "   }" +
                        "}";
        JSONAssert.assertEquals(expected, objectMapper.writeValueAsString(device), true);
    }

}