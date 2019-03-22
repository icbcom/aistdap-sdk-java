package ru.icbcom.aistdapsdkjava.impl.datasource;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import ru.icbcom.aistdapsdkjava.api.datasource.DataSource;
import ru.icbcom.aistdapsdkjava.api.datasource.DataSourceList;
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

class DefaultDataSourceListDeserializationTest {

    private DataStore dataStore = new DummyDataStore();
    private ObjectMapper objectMapper = new DefaultObjectMapperFactory().create(dataStore);

    @Test
    void deserializationShouldWorkProperly() throws IOException {
        ClassPathResource resource = new ClassPathResource("deserialization/dataSourceList.json");
        String json = new String(Files.readAllBytes(resource.getFile().toPath()));

        DataSourceList dataSourceList = objectMapper.readValue(json, DefaultDataSourceList.class);

        assertThat(dataSourceList, allOf(
                hasProperty("size", is(3L)),
                hasProperty("totalElements", is(46L)),
                hasProperty("totalPages", is(16L)),
                hasProperty("number", is(0L)),
                hasProperty("links", contains(
                        allOf(
                                hasProperty("rel", is("first")),
                                hasProperty("href", is("http://127.0.0.1:8080/objectTypes/1/dataSources?page=0&size=3")),
                                hasProperty("templated", is(false))
                        ),
                        allOf(
                                hasProperty("rel", is("self")),
                                hasProperty("href", is("http://127.0.0.1:8080/objectTypes/1/dataSources?page=0&size=3")),
                                hasProperty("templated", is(false))
                        ),
                        allOf(
                                hasProperty("rel", is("next")),
                                hasProperty("href", is("http://127.0.0.1:8080/objectTypes/1/dataSources?page=1&size=3")),
                                hasProperty("templated", is(false))
                        ),
                        allOf(
                                hasProperty("rel", is("last")),
                                hasProperty("href", is("http://127.0.0.1:8080/objectTypes/1/dataSources?page=15&size=3")),
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

        List<DataSource> dataSourcesInCurrentPortion = new ArrayList<>((int) dataSourceList.getSize());
        Iterator<DataSource> iterator = dataSourceList.iterator();
        for (int i = 0; i < dataSourceList.getSize(); i++) {
            if (iterator.hasNext()) {
                DataSource dataSource = iterator.next();
                dataSourcesInCurrentPortion.add(dataSource);
            }
        }

        assertThat(dataSourcesInCurrentPortion, contains(
                allOf(
                        hasProperty("dataSourceId", is(2L)),
                        hasProperty("objectTypeId", is(1L)),
                        hasProperty("caption", is("Электроэнергия, A+ Тариф 1")),
                        hasProperty("measureItem", is("кВт*ч")),
                        hasProperty("dataSourceGroupId", is(1L)),
                        hasProperty("dataStore", is(sameInstance(dataStore))),
                        hasProperty("links", contains(
                                allOf(
                                        hasProperty("rel", is("self")),
                                        hasProperty("href", is("http://127.0.0.1:8080/objectTypes/1/dataSources/2")),
                                        hasProperty("templated", is(false))
                                ),
                                allOf(
                                        hasProperty("rel", is("dap:dataSource")),
                                        hasProperty("href", is("http://127.0.0.1:8080/objectTypes/1/dataSources/2")),
                                        hasProperty("templated", is(false))
                                ),
                                allOf(
                                        hasProperty("rel", is("dap:objectType")),
                                        hasProperty("href", is("http://127.0.0.1:8080/objectTypes/1")),
                                        hasProperty("templated", is(false))
                                ),
                                allOf(
                                        hasProperty("rel", is("dap:dataSourceGroup")),
                                        hasProperty("href", is("http://127.0.0.1:8080/objectTypes/1/dataSourceGroups/1")),
                                        hasProperty("templated", is(false))
                                )
                        ))
                ),
                allOf(
                        hasProperty("dataSourceId", is(3L)),
                        hasProperty("objectTypeId", is(1L)),
                        hasProperty("caption", is("Электроэнергия, A+ Тариф 2")),
                        hasProperty("measureItem", is("кВт*ч")),
                        hasProperty("dataSourceGroupId", is(1L)),
                        hasProperty("dataStore", is(sameInstance(dataStore))),
                        hasProperty("links", contains(
                                allOf(
                                        hasProperty("rel", is("self")),
                                        hasProperty("href", is("http://127.0.0.1:8080/objectTypes/1/dataSources/3")),
                                        hasProperty("templated", is(false))
                                ),
                                allOf(
                                        hasProperty("rel", is("dap:dataSource")),
                                        hasProperty("href", is("http://127.0.0.1:8080/objectTypes/1/dataSources/3")),
                                        hasProperty("templated", is(false))
                                ),
                                allOf(
                                        hasProperty("rel", is("dap:objectType")),
                                        hasProperty("href", is("http://127.0.0.1:8080/objectTypes/1")),
                                        hasProperty("templated", is(false))
                                ),
                                allOf(
                                        hasProperty("rel", is("dap:dataSourceGroup")),
                                        hasProperty("href", is("http://127.0.0.1:8080/objectTypes/1/dataSourceGroups/1")),
                                        hasProperty("templated", is(false))
                                )
                        ))
                ),
                allOf(
                        hasProperty("dataSourceId", is(4L)),
                        hasProperty("objectTypeId", is(1L)),
                        hasProperty("caption", is("Электроэнергия, A+ Тариф 3")),
                        hasProperty("measureItem", is("кВт*ч")),
                        hasProperty("dataSourceGroupId", is(1L)),
                        hasProperty("dataStore", is(sameInstance(dataStore))),
                        hasProperty("links", contains(
                                allOf(
                                        hasProperty("rel", is("self")),
                                        hasProperty("href", is("http://127.0.0.1:8080/objectTypes/1/dataSources/4")),
                                        hasProperty("templated", is(false))
                                ),
                                allOf(
                                        hasProperty("rel", is("dap:dataSource")),
                                        hasProperty("href", is("http://127.0.0.1:8080/objectTypes/1/dataSources/4")),
                                        hasProperty("templated", is(false))
                                ),
                                allOf(
                                        hasProperty("rel", is("dap:objectType")),
                                        hasProperty("href", is("http://127.0.0.1:8080/objectTypes/1")),
                                        hasProperty("templated", is(false))
                                ),
                                allOf(
                                        hasProperty("rel", is("dap:dataSourceGroup")),
                                        hasProperty("href", is("http://127.0.0.1:8080/objectTypes/1/dataSourceGroups/1")),
                                        hasProperty("templated", is(false))
                                )
                        ))
                )
        ));
    }

}