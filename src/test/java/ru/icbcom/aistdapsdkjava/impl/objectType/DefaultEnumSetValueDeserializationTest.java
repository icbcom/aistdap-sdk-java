package ru.icbcom.aistdapsdkjava.impl.objectType;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import ru.icbcom.aistdapsdkjava.api.objecttype.EnumSetValue;
import ru.icbcom.aistdapsdkjava.impl.objectmapper.ObjectMappers;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class DefaultEnumSetValueDeserializationTest {

    private ObjectMapper objectMapper = ObjectMappers.create();

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
            hasProperty("caption", is("Чтение (01h)"))
        ));
    }

}
