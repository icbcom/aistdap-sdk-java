package ru.icbcom.aistdapsdkjava.impl.device;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import ru.icbcom.aistdapsdkjava.api.device.Device;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.datastore.DummyDataStore;
import ru.icbcom.aistdapsdkjava.impl.datastore.objectmapper.DefaultObjectMapperFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.util.Collections.emptyMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static ru.icbcom.aistdapsdkjava.helper.ResourceHelper.loadResource;

class DefaultDeviceListDeserializationTest {

    private DataStore dataStore;
    private ObjectMapper objectMapper;

    public DefaultDeviceListDeserializationTest() {
        this.dataStore = new DummyDataStore();
        this.objectMapper = new DefaultObjectMapperFactory().create(dataStore);
    }

    @Test
    void deserializationShouldWorkProperly() throws IOException {
        String json = loadResource("deserialization/deviceList.json");

        DefaultDeviceList deviceList = objectMapper.readValue(json, DefaultDeviceList.class);

        assertThat(deviceList, allOf(
                hasProperty("size", is(20L)),
                hasProperty("totalElements", is(2L)),
                hasProperty("totalPages", is(1L)),
                hasProperty("number", is(0L)),
                hasProperty("links", contains(
                        allOf(
                                hasProperty("rel", is("self")),
                                hasProperty("href", is("http://127.0.0.1:8080/devices?page=0&size=20")),
                                hasProperty("templated", is(false))
                        ),
                        allOf(
                                hasProperty("rel", is("curies")),
                                hasProperty("href", is("http://127.0.0.1:8080/documentation/{rel}.html")),
                                hasProperty("templated", is(true))
                        )
                )),
                hasProperty("dataStore", sameInstance(dataStore))
        ));
        List<Device> devicesInCurrentPortion = new ArrayList<>((int) deviceList.getSize());
        Iterator<Device> iterator = deviceList.iterator();
        for (int i = 0; i < deviceList.getSize(); i++) {
            if (iterator.hasNext()) {
                Device device = iterator.next();
                devicesInCurrentPortion.add(device);
            }
        }
        assertThat(devicesInCurrentPortion, contains(
                allOf(
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
                                )
                        )),
                        hasProperty("dataStore", sameInstance(dataStore))
                ),
                allOf(
                        hasProperty("id", is(10015L)),
                        hasProperty("objectTypeId", is(200L)),
                        hasProperty("name", is("Some device without attribute values")),
                        hasProperty("attributeValues", is(emptyMap())),
                        hasProperty("links", contains(
                                allOf(
                                        hasProperty("rel", is("self")),
                                        hasProperty("href", is("http://127.0.0.1:8080/devices/10015")),
                                        hasProperty("templated", is(false))
                                ),
                                allOf(
                                        hasProperty("rel", is("dap:device")),
                                        hasProperty("href", is("http://127.0.0.1:8080/devices/10015")),
                                        hasProperty("templated", is(false))
                                ),
                                allOf(
                                        hasProperty("rel", is("dap:objectType")),
                                        hasProperty("href", is("http://127.0.0.1:8080/objectTypes/200")),
                                        hasProperty("templated", is(false))
                                ),
                                allOf(
                                        hasProperty("rel", is("dap:dapObjectMeasuredDataLast")),
                                        hasProperty("href", is("http://127.0.0.1:8080/measuredData/10015/last")),
                                        hasProperty("templated", is(false))
                                ),
                                allOf(
                                        hasProperty("rel", is("dap:attach")),
                                        hasProperty("href", is("http://127.0.0.1:8080/devices/10015/attach")),
                                        hasProperty("templated", is(false))
                                )
                        )),
                        hasProperty("dataStore", sameInstance(dataStore))
                )
        ));
    }

    @Test
    void emptyListDeserializationShouldWorkProperly() throws IOException {
        String json =
                "{\n" +
                        "  \"_links\": {\n" +
                        "    \"self\": {\n" +
                        "      \"href\": \"http://127.0.0.1:8080/devices?page=0&size=20\"\n" +
                        "    }\n" +
                        "  },\n" +
                        "  \"page\": {\n" +
                        "    \"size\": 20,\n" +
                        "    \"totalElements\": 0,\n" +
                        "    \"totalPages\": 0,\n" +
                        "    \"number\": 0\n" +
                        "  }\n" +
                        "}";

        DefaultDeviceList deviceList = objectMapper.readValue(json, DefaultDeviceList.class);

        assertThat(deviceList, allOf(
                hasProperty("size", is(20L)),
                hasProperty("totalElements", is(0L)),
                hasProperty("totalPages", is(0L)),
                hasProperty("number", is(0L)),
                hasProperty("links", contains(
                        allOf(
                                hasProperty("rel", is("self")),
                                hasProperty("href", is("http://127.0.0.1:8080/devices?page=0&size=20")),
                                hasProperty("templated", is(false))
                        )
                )),
                hasProperty("dataStore", sameInstance(dataStore))
        ));
        assertFalse(deviceList.iterator().hasNext());
    }

}