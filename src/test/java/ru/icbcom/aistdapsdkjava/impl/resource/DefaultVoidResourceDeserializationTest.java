package ru.icbcom.aistdapsdkjava.impl.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import ru.icbcom.aistdapsdkjava.api.resource.VoidResource;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.datastore.DummyDataStore;
import ru.icbcom.aistdapsdkjava.impl.mapper.ObjectMappers;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class DefaultVoidResourceDeserializationTest {

    private DataStore dataStore;
    private ObjectMapper objectMapper;

    public DefaultVoidResourceDeserializationTest() {
        this.dataStore = new DummyDataStore();
        this.objectMapper = ObjectMappers.create(dataStore);
    }

    @Test
    void deserializationShouldWorkProperly() throws IOException {
        String json =
                "{\n" +
                        "  \"_links\": {\n" +
                        "    \"self\": {\n" +
                        "      \"href\": \"http://127.0.0.1:8080/\"\n" +
                        "    },\n" +
                        "    \"dap:login\": {\n" +
                        "      \"href\": \"http://127.0.0.1:8080/auth/login\"\n" +
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

        VoidResource voidResource = objectMapper.readValue(json, DefaultVoidResource.class);
        assertThat(voidResource, hasProperty("links", contains(
                allOf(
                        hasProperty("rel", is("self")),
                        hasProperty("href", is("http://127.0.0.1:8080/")),
                        hasProperty("templated", is(false))
                ),
                allOf(
                        hasProperty("rel", is("dap:login")),
                        hasProperty("href", is("http://127.0.0.1:8080/auth/login")),
                        hasProperty("templated", is(false))
                ),
                allOf(
                        hasProperty("rel", is("curies")),
                        hasProperty("href", is("http://127.0.0.1:8080/documentation/{rel}.html")),
                        hasProperty("templated", is(true))
                )
        )));
    }

}