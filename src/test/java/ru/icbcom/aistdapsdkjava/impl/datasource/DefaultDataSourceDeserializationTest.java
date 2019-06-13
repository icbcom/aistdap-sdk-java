/*
 * Copyright © 2018-2019 Icbcom
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package ru.icbcom.aistdapsdkjava.impl.datasource;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import ru.icbcom.aistdapsdkjava.api.datasource.DataSource;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.datastore.DummyDataStore;
import ru.icbcom.aistdapsdkjava.impl.datastore.objectmapper.DefaultObjectMapperFactory;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class DefaultDataSourceDeserializationTest {

    private DataStore dataStore = new DummyDataStore();
    private ObjectMapper objectMapper = new DefaultObjectMapperFactory().create(dataStore);

    @Test
    void deserializationShouldWorkProperly() throws IOException {
        String json =
                "{\n" +
                        "        \"dataSourceId\": 2,\n" +
                        "        \"objectTypeId\": 1,\n" +
                        "        \"caption\": \"Электроэнергия, A+ Тариф 1\",\n" +
                        "        \"measureItem\": \"кВт*ч\",\n" +
                        "        \"dataSourceGroupId\": 1,\n" +
                        "        \"_links\": {\n" +
                        "          \"self\": {\n" +
                        "            \"href\": \"http://127.0.0.1:8080/objectTypes/1/dataSources/2\"\n" +
                        "          },\n" +
                        "          \"dap:dataSource\": {\n" +
                        "            \"href\": \"http://127.0.0.1:8080/objectTypes/1/dataSources/2\"\n" +
                        "          },\n" +
                        "          \"dap:objectType\": {\n" +
                        "            \"href\": \"http://127.0.0.1:8080/objectTypes/1\"\n" +
                        "          },\n" +
                        "          \"dap:dataSourceGroup\": {\n" +
                        "            \"href\": \"http://127.0.0.1:8080/objectTypes/1/dataSourceGroups/1\"\n" +
                        "          }\n" +
                        "        }\n" +
                        "      }";

        DataSource dataSource = objectMapper.readValue(json, DefaultDataSource.class);

        assertThat(dataSource, allOf(
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
        ));
    }

}