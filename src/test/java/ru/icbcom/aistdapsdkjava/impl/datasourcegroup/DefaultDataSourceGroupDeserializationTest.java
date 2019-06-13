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

package ru.icbcom.aistdapsdkjava.impl.datasourcegroup;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import ru.icbcom.aistdapsdkjava.api.datasource.DataSource;
import ru.icbcom.aistdapsdkjava.api.datasourcegroup.DataSourceGroup;
import ru.icbcom.aistdapsdkjava.impl.datasource.DefaultDataSource;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.datastore.DummyDataStore;
import ru.icbcom.aistdapsdkjava.impl.datastore.objectmapper.DefaultObjectMapperFactory;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class DefaultDataSourceGroupDeserializationTest {

    private DataStore dataStore = new DummyDataStore();
    private ObjectMapper objectMapper = new DefaultObjectMapperFactory().create(dataStore);

    @Test
    void deserializationShouldWorkProperly() throws IOException {
        String json =
                "{\n" +
                        "  \"dataSourceGroupId\": 1,\n" +
                        "  \"objectTypeId\": 100,\n" +
                        "  \"caption\": \"Параметры электроэнергии A+\",\n" +
                        "  \"_links\": {\n" +
                        "    \"self\": {\n" +
                        "      \"href\": \"http://127.0.0.1:8080/objectTypes/100/dataSourceGroups/1\"\n" +
                        "    },\n" +
                        "    \"dap:dataSourceGroup\": {\n" +
                        "      \"href\": \"http://127.0.0.1:8080/objectTypes/100/dataSourceGroups/1\"\n" +
                        "    },\n" +
                        "    \"dap:objectType\": {\n" +
                        "      \"href\": \"http://127.0.0.1:8080/objectTypes/100\"\n" +
                        "    },\n" +
                        "    \"dap:dataSourcesInGroup\": {\n" +
                        "      \"href\": \"http://127.0.0.1:8080/objectTypes/100/dataSourceGroups/1/dataSources{?page,size,sort}\",\n" +
                        "      \"templated\": true\n" +
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

        DataSourceGroup dataSourceGroup = objectMapper.readValue(json, DefaultDataSourceGroup.class);

        assertThat(dataSourceGroup, allOf(
                hasProperty("dataSourceGroupId", is(1L)),
                hasProperty("objectTypeId", is(100L)),
                hasProperty("caption", is("Параметры электроэнергии A+")),
                hasProperty("links", contains(
                        allOf(
                                hasProperty("rel", is("self")),
                                hasProperty("href", is("http://127.0.0.1:8080/objectTypes/100/dataSourceGroups/1")),
                                hasProperty("templated", is(false))
                        ),
                        allOf(
                                hasProperty("rel", is("dap:dataSourceGroup")),
                                hasProperty("href", is("http://127.0.0.1:8080/objectTypes/100/dataSourceGroups/1")),
                                hasProperty("templated", is(false))
                        ),
                        allOf(
                                hasProperty("rel", is("dap:objectType")),
                                hasProperty("href", is("http://127.0.0.1:8080/objectTypes/100")),
                                hasProperty("templated", is(false))
                        ),
                        allOf(
                                hasProperty("rel", is("dap:dataSourcesInGroup")),
                                hasProperty("href", is("http://127.0.0.1:8080/objectTypes/100/dataSourceGroups/1/dataSources{?page,size,sort}")),
                                hasProperty("templated", is(true))
                        ),
                        allOf(
                                hasProperty("rel", is("curies")),
                                hasProperty("href", is("http://127.0.0.1:8080/documentation/{rel}.html")),
                                hasProperty("templated", is(true))
                        )
                ))
        ));
    }

}