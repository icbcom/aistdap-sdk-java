package ru.icbcom.aistdapsdkjava.impl.objectType;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import ru.icbcom.aistdapsdkjava.integrationtest.objecttype.EnumSetValue;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.datastore.DummyDataStore;
import ru.icbcom.aistdapsdkjava.impl.datastore.objectmapper.DefaultObjectMapperFactory;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class DefaultEnumSetValueDeserializationTest {

    private DataStore dataStore;
    private ObjectMapper objectMapper;

    DefaultEnumSetValueDeserializationTest() {
        dataStore = new DummyDataStore();
        objectMapper = new DefaultObjectMapperFactory().create(dataStore);
    }

    @Test
    void deserializationShouldWorkProperly() throws IOException {
        String json =
                "{\n" +
                        "   \"number\": 1,\n" +
                        "   \"caption\": \"Чтение (01h)\"\n" +
                        "}";

        EnumSetValue enumSetValue = objectMapper.readValue(json, DefaultEnumSetValue.class);
        assertThat(enumSetValue, allOf(
                hasProperty("number", is(1)),
                hasProperty("caption", is("Чтение (01h)")),
                hasProperty("dataStore", sameInstance(dataStore))
        ));
    }

}
