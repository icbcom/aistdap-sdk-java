package ru.icbcom.aistdapsdkjava.impl.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.datastore.DummyDataStore;
import ru.icbcom.aistdapsdkjava.impl.mapper.ObjectMappers;

import java.io.IOException;

import static java.util.Collections.emptyMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class DefaultErrorDeserializationTest {

    private DataStore dataStore;
    private ObjectMapper objectMapper;

    DefaultErrorDeserializationTest() {
        dataStore = new DummyDataStore();
        objectMapper = ObjectMappers.create(dataStore);
    }


    @Test
    void deserializationShouldWorkProperly() throws IOException {
        String json =
                "{\n" +
                        "    \"title\": \"Unauthorized\",\n" +
                        "    \"status\": 401,\n" +
                        "    \"detail\": \"Token expired\"\n" +
                        "}";

        DefaultError error = objectMapper.readValue(json, DefaultError.class);
        assertThat(error, allOf(
                hasProperty("title", is("Unauthorized")),
                hasProperty("status", is(401)),
                hasProperty("detail", is("Token expired")),
                hasProperty("moreInfo", is(emptyMap()))
        ));
    }

    @Test
    void additionalInfoDeserializationShouldWorkProperly() throws IOException {
        String json =
                "{\n" +
                        "    \"objectTypeId\": 123123,\n" +
                        "    \"title\": \"Object type not found\",\n" +
                        "    \"status\": 404,\n" +
                        "    \"detail\": \"Could not find object type with objectTypeId '123123'\"\n" +
                        "}";

        DefaultError error = objectMapper.readValue(json, DefaultError.class);
        assertThat(error, allOf(
                hasProperty("title", is("Object type not found")),
                hasProperty("status", is(404)),
                hasProperty("detail", is("Could not find object type with objectTypeId '123123'")),
                hasProperty("moreInfo", is(hasEntry("objectTypeId", 123123)))
        ));
    }

    @Test
    void deserializationForConstraintViolationShouldWorkProperly() throws IOException {
        String json =
                "{\n" +
                        "    \"violations\": [\n" +
                        "        {\n" +
                        "            \"message\": \"Object type name cannot be null\",\n" +
                        "            \"field\": \"name\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"message\": \"Object type device cannot be null\",\n" +
                        "            \"field\": \"device\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"message\": \"Object type sections cannot be null\",\n" +
                        "            \"field\": \"sections\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"message\": \"Object type enabled cannot be null\",\n" +
                        "            \"field\": \"enabled\"\n" +
                        "        }\n" +
                        "    ],\n" +
                        "    \"status\": 400,\n" +
                        "    \"title\": \"Constraint Violation\"\n" +
                        "}";

        DefaultError error = objectMapper.readValue(json, DefaultError.class);
        System.out.println(error);
        assertThat(error, allOf(
                hasProperty("title", is("Constraint Violation")),
                hasProperty("status", is(400)),
                hasProperty("detail", is(nullValue())),
                hasProperty("moreInfo",
                        hasEntry(is("violations"), containsInAnyOrder(
                                allOf(
                                        hasEntry("message", "Object type name cannot be null"),
                                        hasEntry("field", "name")
                                ),
                                allOf(
                                        hasEntry("message", "Object type device cannot be null"),
                                        hasEntry("field", "device")
                                ),
                                allOf(
                                        hasEntry("message", "Object type sections cannot be null"),
                                        hasEntry("field", "sections")
                                ),
                                allOf(
                                        hasEntry("message", "Object type enabled cannot be null"),
                                        hasEntry("field", "enabled")
                                )
                        ))
                )
        ));
    }

}

/*
hasEntry(is("violations"), containsInAnyOrder(
                                allOf(
                                        hasProperty("message", is("Object type name cannot be null")),
                                        hasProperty("field", is("name"))
                                ),
                                allOf(
                                        hasProperty("message", is("Object type device cannot be null")),
                                        hasProperty("field", is("device"))
                                ),
                                allOf(
                                        hasProperty("message", is("Object type sections cannot be null")),
                                        hasProperty("field", is("sections"))
                                ),
                                allOf(
                                        hasProperty("message", is("Object type enabled cannot be null")),
                                        hasProperty("field", is("enabled"))
                                )
                        ))
*/