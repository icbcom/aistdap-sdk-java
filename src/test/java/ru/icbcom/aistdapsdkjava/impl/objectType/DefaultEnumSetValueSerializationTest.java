package ru.icbcom.aistdapsdkjava.impl.objectType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import ru.icbcom.aistdapsdkjava.api.objecttype.EnumSetValue;
import ru.icbcom.aistdapsdkjava.impl.objectmapper.ObjectMappers;

public class DefaultEnumSetValueSerializationTest {

    private ObjectMapper objectMapper = ObjectMappers.create();

    @Test
    void serializationShouldWorkProperly() throws JsonProcessingException, JSONException {
        EnumSetValue enumSetValue = new DefaultEnumSetValue()
                .setNumber(10)
                .setCaption("Some Caption");

        String json = objectMapper.writeValueAsString(enumSetValue);
        JSONAssert.assertEquals("{\"number\":10,\"caption\":\"Some Caption\"}", json, true);
    }

}