package ru.icbcom.aistdapsdkjava.impl.datasource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import ru.icbcom.aistdapsdkjava.api.datasource.DataSource;
import ru.icbcom.aistdapsdkjava.impl.datastore.DummyDataStore;
import ru.icbcom.aistdapsdkjava.impl.datastore.objectmapper.DefaultObjectMapperFactory;

import static org.junit.jupiter.api.Assertions.*;

class DefaultDataSourceSerializationTest {

    private ObjectMapper objectMapper = new DefaultObjectMapperFactory().create(new DummyDataStore());

    @Test
    void serializationShouldWorkProperly() throws JsonProcessingException, JSONException {
        DataSource dataSource = new DefaultDataSource(null)
                .setDataSourceId(100L)
                .setObjectTypeId(1L)
                .setCaption("Название источника данных")
                .setMeasureItem("Единица измерения")
                .setDataSourceGroupId(1000L);

        String expected =
                "{\n" +
                "\t\"dataSourceId\": 100,\n" +
                "\t\"objectTypeId\": 1,\n" +
                "\t\"caption\": \"Название источника данных\",\n" +
                "\t\"measureItem\": \"Единица измерения\",\n" +
                "\t\"dataSourceGroupId\": 1000\n" +
                "}";
        JSONAssert.assertEquals(expected, objectMapper.writeValueAsString(dataSource), true);
    }

}