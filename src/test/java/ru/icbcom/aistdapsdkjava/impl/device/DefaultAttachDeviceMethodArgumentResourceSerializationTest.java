package ru.icbcom.aistdapsdkjava.impl.device;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.datastore.DummyDataStore;
import ru.icbcom.aistdapsdkjava.impl.datastore.objectmapper.DefaultObjectMapperFactory;

import java.io.IOException;

class DefaultAttachDeviceMethodArgumentResourceSerializationTest {

    private DataStore dataStore;
    private ObjectMapper objectMapper;

    public DefaultAttachDeviceMethodArgumentResourceSerializationTest() {
        this.dataStore = new DummyDataStore();
        this.objectMapper = new DefaultObjectMapperFactory().create(dataStore);
    }

    @Test
    void serializationShouldWorkProperly() throws IOException, JSONException {
        AttachDeviceMethodArgumentResource attachDeviceMethodArgumentResource = new DefaultAttachDeviceMethodArgumentResource(dataStore)
                .setPhysicalStructureObjectId(123L);

        String expected = "{ \"physicalStructureObjectId\": 123 }";
        JSONAssert.assertEquals(expected, objectMapper.writeValueAsString(attachDeviceMethodArgumentResource), true);
    }

}