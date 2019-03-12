package ru.icbcom.aistdapsdkjava.impl.objectType;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectType;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectTypeList;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.datastore.DummyDataStore;
import ru.icbcom.aistdapsdkjava.impl.datastore.objectmapper.DefaultObjectMapperFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

class DefaultObjectTypeListDeserializationTest {

    private DataStore dataStore;
    private ObjectMapper objectMapper;

    public DefaultObjectTypeListDeserializationTest() {
        this.dataStore = new DummyDataStore();
        this.objectMapper = new DefaultObjectMapperFactory().create(dataStore);
    }

    @Test
    void deserializationShouldWorkProperly() throws IOException {
        ClassPathResource resource = new ClassPathResource("deserialization/objectTypeList.json");
        String json = new String(Files.readAllBytes(resource.getFile().toPath()));

        ObjectTypeList objectTypes = objectMapper.readValue(json, DefaultObjectTypeList.class);

        assertThat(objectTypes, allOf(
                hasProperty("size", is(3L)),
                hasProperty("totalElements", is(34L)),
                hasProperty("totalPages", is(12L)),
                hasProperty("number", is(0L)),
                hasProperty("links", contains(
                        allOf(
                                hasProperty("rel", is("first")),
                                hasProperty("href", is("http://127.0.0.1:8080/objectTypes?page=0&size=3")),
                                hasProperty("templated", is(false))
                        ),
                        allOf(
                                hasProperty("rel", is("self")),
                                hasProperty("href", is("http://127.0.0.1:8080/objectTypes?page=0&size=3")),
                                hasProperty("templated", is(false))
                        ),
                        allOf(
                                hasProperty("rel", is("next")),
                                hasProperty("href", is("http://127.0.0.1:8080/objectTypes?page=1&size=3")),
                                hasProperty("templated", is(false))
                        ),
                        allOf(
                                hasProperty("rel", is("last")),
                                hasProperty("href", is("http://127.0.0.1:8080/objectTypes?page=11&size=3")),
                                hasProperty("templated", is(false))
                        ),
                        allOf(
                                hasProperty("rel", is("search")),
                                hasProperty("href", is("http://127.0.0.1:8080/objectTypes/search")),
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

        List<ObjectType> objectTypesInCurrentPortion =  new ArrayList<>((int)objectTypes.getSize());
        Iterator<ObjectType> iterator = objectTypes.iterator();
        for (int i = 0; i < objectTypes.getSize(); i++) {
            ObjectType objectType = iterator.next();
            objectTypesInCurrentPortion.add(objectType);
        }

        assertThat(objectTypesInCurrentPortion, contains(
                allOf(
                        hasProperty("id", is(1L)),
                        hasProperty("name", is("Mercury230/233")),
                        hasProperty("caption", is("Счетчик э/э Меркурий 230/233")),
                        hasProperty("device", is(true)),
                        hasProperty("enabled", is(false)),
                        hasProperty("dataStore", sameInstance(dataStore))
                ),
                allOf(
                        hasProperty("id", is(3L)),
                        hasProperty("name", is("Puma")),
                        hasProperty("caption", is("УСПД \"Пума\"")),
                        hasProperty("device", is(false)),
                        hasProperty("enabled", is(true)),
                        hasProperty("dataStore", sameInstance(dataStore))
                ),
                allOf(
                        hasProperty("id", is(4L)),
                        hasProperty("name", is("Puma-Can")),
                        hasProperty("caption", is("CAN")),
                        hasProperty("device", is(false)),
                        hasProperty("enabled", is(true)),
                        hasProperty("dataStore", sameInstance(dataStore))
                )
        ));

    }

    @Test
    void emptyListDeserializationShouldWorkProperly() throws IOException {
        String json =
                "{\n" +
                "  \"_embedded\": {\n" +
                "    \"dap:objectTypes\": [ ]\n" +
                "  },\n" +
                "  \"_links\": {\n" +
                "     \"self\": {\n" +
                "      \"href\": \"http://127.0.0.1:8080/objectTypes?page=0&size=3\"\n" +
                "    },\n" +
                "    \"search\": {\n" +
                "      \"href\": \"http://127.0.0.1:8080/objectTypes/search\"\n" +
                "    },\n" +
                "    \"curies\": [\n" +
                "      {\n" +
                "        \"href\": \"http://127.0.0.1:8080/documentation/{rel}.html\",\n" +
                "        \"name\": \"dap\",\n" +
                "        \"templated\": true\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  \"page\": {\n" +
                "    \"size\": 0,\n" +
                "    \"totalElements\": 0,\n" +
                "    \"totalPages\": 1,\n" +
                "    \"number\": 0\n" +
                "  }\n" +
                "}";

        ObjectTypeList objectTypes = objectMapper.readValue(json, DefaultObjectTypeList.class);
        assertThat(objectTypes, allOf(
                hasProperty("size", is(0L)),
                hasProperty("totalElements", is(0L)),
                hasProperty("totalPages", is(1L)),
                hasProperty("number", is(0L)),
                hasProperty("links", contains(
                        allOf(
                                hasProperty("rel", is("self")),
                                hasProperty("href", is("http://127.0.0.1:8080/objectTypes?page=0&size=3")),
                                hasProperty("templated", is(false))
                        ),
                        allOf(
                                hasProperty("rel", is("search")),
                                hasProperty("href", is("http://127.0.0.1:8080/objectTypes/search")),
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
        assertFalse(objectTypes.iterator().hasNext());
    }

}