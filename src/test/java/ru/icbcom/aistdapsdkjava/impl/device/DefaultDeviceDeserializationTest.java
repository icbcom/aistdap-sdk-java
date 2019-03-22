package ru.icbcom.aistdapsdkjava.impl.device;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import ru.icbcom.aistdapsdkjava.api.device.Device;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.datastore.DummyDataStore;
import ru.icbcom.aistdapsdkjava.impl.datastore.objectmapper.DefaultObjectMapperFactory;

import java.io.IOException;

import static java.util.Collections.emptyMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class DefaultDeviceDeserializationTest {

    private DataStore dataStore;
    private ObjectMapper objectMapper;

    public DefaultDeviceDeserializationTest() {
        this.dataStore = new DummyDataStore();
        this.objectMapper = new DefaultObjectMapperFactory().create(dataStore);
    }

    @Test
    void deserializationShouldWorkProperly() throws IOException {
        String json =
                "{\n" +
                        "  \"id\": 10009,\n" +
                        "  \"objectTypeId\": 22,\n" +
                        "  \"name\": \"Счетчик э/э Аист A100\",\n" +
                        "  \"attributeValues\": {\n" +
                        "    \"Serial\": \"000000000000\",\n" +
                        "    \"AccessLevel\": \"3\",\n" +
                        "    \"Password\": \"000000\",\n" +
                        "    \"RatesNumber\": \"4\",\n" +
                        "    \"Latitude\": \"0\",\n" +
                        "    \"Longitude\": \"0\"\n" +
                        "  },\n" +
                        "  \"_links\": {\n" +
                        "    \"self\": {\n" +
                        "      \"href\": \"http://127.0.0.1:8080/devices/10009\"\n" +
                        "    },\n" +
                        "    \"dap:device\": {\n" +
                        "      \"href\": \"http://127.0.0.1:8080/devices/10009\"\n" +
                        "    },\n" +
                        "    \"dap:objectType\": {\n" +
                        "      \"href\": \"http://127.0.0.1:8080/objectTypes/22\"\n" +
                        "    },\n" +
                        "    \"dap:dapObjectMeasuredDataLast\": {\n" +
                        "      \"href\": \"http://127.0.0.1:8080/measuredData/10009/last\"\n" +
                        "    },\n" +
                        "    \"dap:physicalStructureObjectAttachedTo\": {\n" +
                        "      \"href\": \"http://127.0.0.1:8080/physicalStructure/10008\"\n" +
                        "    },\n" +
                        "    \"dap:detach\": {\n" +
                        "      \"href\": \"http://127.0.0.1:8080/devices/10009/detach\"\n" +
                        "    },\n" +
                        "    \"curies\": [\n" +
                        "      {\n" +
                        "        \"href\": \"http://127.0.0.1:8080/documentation/{rel}.html\",\n" +
                        "        \"name\": \"dap\",\n" +
                        "        \"templated\": true\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  }\n" +
                        "}";

        Device device = objectMapper.readValue(json, DefaultDevice.class);

        assertThat(device, allOf(
                hasProperty("id", is(10009L)),
                hasProperty("objectTypeId", is(22L)),
                hasProperty("name", is("Счетчик э/э Аист A100")),
                hasProperty("attributeValues", allOf(
                        hasEntry("Serial", "000000000000"),
                        hasEntry("AccessLevel", "3"),
                        hasEntry("Password", "000000"),
                        hasEntry("RatesNumber", "4"),
                        hasEntry("Latitude", "0"),
                        hasEntry("Longitude", "0")
                )),
                hasProperty("links", contains(
                        allOf(
                                hasProperty("rel", is("self")),
                                hasProperty("href", is("http://127.0.0.1:8080/devices/10009")),
                                hasProperty("templated", is(false))
                        ),
                        allOf(
                                hasProperty("rel", is("dap:device")),
                                hasProperty("href", is("http://127.0.0.1:8080/devices/10009")),
                                hasProperty("templated", is(false))
                        ),
                        allOf(
                                hasProperty("rel", is("dap:objectType")),
                                hasProperty("href", is("http://127.0.0.1:8080/objectTypes/22")),
                                hasProperty("templated", is(false))
                        ),
                        allOf(
                                hasProperty("rel", is("dap:dapObjectMeasuredDataLast")),
                                hasProperty("href", is("http://127.0.0.1:8080/measuredData/10009/last")),
                                hasProperty("templated", is(false))
                        ),
                        allOf(
                                hasProperty("rel", is("dap:physicalStructureObjectAttachedTo")),
                                hasProperty("href", is("http://127.0.0.1:8080/physicalStructure/10008")),
                                hasProperty("templated", is(false))
                        ),
                        allOf(
                                hasProperty("rel", is("dap:detach")),
                                hasProperty("href", is("http://127.0.0.1:8080/devices/10009/detach")),
                                hasProperty("templated", is(false))
                        ),
                        allOf(
                                hasProperty("rel", is("curies")),
                                hasProperty("href", is("http://127.0.0.1:8080/documentation/{rel}.html")),
                                hasProperty("templated", is(true))
                        )
                ))
        ));
    }

    @Test
    void deserializationWithEmptyAttributeValuesShouldWorkProperly() throws IOException {
        String json =
                "{\n" +
                        "  \"id\": 10009,\n" +
                        "  \"objectTypeId\": 22,\n" +
                        "  \"name\": \"Счетчик э/э Аист A100\",\n" +
                        "  \"attributeValues\": {}\n" +
                        "}";

        Device device = objectMapper.readValue(json, DefaultDevice.class);

        assertThat(device, allOf(
                hasProperty("id", is(10009L)),
                hasProperty("objectTypeId", is(22L)),
                hasProperty("name", is("Счетчик э/э Аист A100")),
                hasProperty("attributeValues", is(emptyMap()))
        ));
    }

}