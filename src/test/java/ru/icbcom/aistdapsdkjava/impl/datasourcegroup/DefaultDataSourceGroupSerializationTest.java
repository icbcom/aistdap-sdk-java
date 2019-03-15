package ru.icbcom.aistdapsdkjava.impl.datasourcegroup;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import ru.icbcom.aistdapsdkjava.api.datasourcegroup.DataSourceGroup;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.datastore.DummyDataStore;
import ru.icbcom.aistdapsdkjava.impl.datastore.objectmapper.DefaultObjectMapperFactory;

class DefaultDataSourceGroupSerializationTest {

    private DataStore dataStore = new DummyDataStore();
    private ObjectMapper objectMapper = new DefaultObjectMapperFactory().create(dataStore);

    @Test
    void serializationShouldWorkProperly() throws JsonProcessingException, JSONException {
        DataSourceGroup dataSourceGroup = new DefaultDataSourceGroup(dataStore)
                .setDataSourceGroupId(1000L)
                .setObjectTypeId(100L)
                .setCaption("Название группы источников данных");

        String expected =
                "{\n" +
                        "  \"dataSourceGroupId\": 1000,\n" +
                        "  \"objectTypeId\": 100,\n" +
                        "  \"caption\": \"Название группы источников данных\"\n" +
                        "}";
        JSONAssert.assertEquals(expected, objectMapper.writeValueAsString(dataSourceGroup), true);
    }

}