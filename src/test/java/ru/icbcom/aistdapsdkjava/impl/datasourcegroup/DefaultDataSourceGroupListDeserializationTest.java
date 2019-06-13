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
import org.springframework.core.io.ClassPathResource;
import ru.icbcom.aistdapsdkjava.api.datasource.DataSource;
import ru.icbcom.aistdapsdkjava.api.datasourcegroup.DataSourceGroup;
import ru.icbcom.aistdapsdkjava.api.datasourcegroup.DataSourceGroupList;
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
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.jupiter.api.Assertions.*;
import static ru.icbcom.aistdapsdkjava.helper.ResourceHelper.loadResource;

class DefaultDataSourceGroupListDeserializationTest {

    private DataStore dataStore = new DummyDataStore();
    private ObjectMapper objectMapper = new DefaultObjectMapperFactory().create(dataStore);

    @Test
    void deserializationShouldWorkProperly() throws IOException {
        String json = loadResource("deserialization/dataSourceGroupList.json");

        DataSourceGroupList dataSourceGroupList = objectMapper.readValue(json, DefaultDataSourceGroupList.class);

        assertThat(dataSourceGroupList, allOf(
                hasProperty("size", is(3L)),
                hasProperty("totalElements", is(6L)),
                hasProperty("totalPages", is(2L)),
                hasProperty("number", is(0L)),
                hasProperty("links", contains(
                        allOf(
                                hasProperty("rel", is("first")),
                                hasProperty("href", is("http://127.0.0.1:8080/objectTypes/1/dataSourceGroups?page=0&size=3")),
                                hasProperty("templated", is(false))
                        ),
                        allOf(
                                hasProperty("rel", is("self")),
                                hasProperty("href", is("http://127.0.0.1:8080/objectTypes/1/dataSourceGroups?page=0&size=3")),
                                hasProperty("templated", is(false))
                        ),
                        allOf(
                                hasProperty("rel", is("next")),
                                hasProperty("href", is("http://127.0.0.1:8080/objectTypes/1/dataSourceGroups?page=1&size=3")),
                                hasProperty("templated", is(false))
                        ),
                        allOf(
                                hasProperty("rel", is("last")),
                                hasProperty("href", is("http://127.0.0.1:8080/objectTypes/1/dataSourceGroups?page=1&size=3")),
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

        List<DataSourceGroup> dataSourceGroupsInCurrentPortion = new ArrayList<>((int) dataSourceGroupList.getSize());
        Iterator<DataSourceGroup> iterator = dataSourceGroupList.iterator();
        for (int i = 0; i < dataSourceGroupList.getSize(); i++) {
            if (iterator.hasNext()) {
                DataSourceGroup dataSourceGroup = iterator.next();
                dataSourceGroupsInCurrentPortion.add(dataSourceGroup);
            }
        }

        assertThat(dataSourceGroupsInCurrentPortion, contains(
                allOf(
                        hasProperty("dataSourceGroupId", is(1L)),
                        hasProperty("objectTypeId", is(1L)),
                        hasProperty("caption", is("Параметры электроэнергии A+")),
                        hasProperty("links", contains(
                                allOf(
                                        hasProperty("rel", is("self")),
                                        hasProperty("href", is("http://127.0.0.1:8080/objectTypes/1/dataSourceGroups/1")),
                                        hasProperty("templated", is(false))
                                ),
                                allOf(
                                        hasProperty("rel", is("dap:dataSourceGroup")),
                                        hasProperty("href", is("http://127.0.0.1:8080/objectTypes/1/dataSourceGroups/1")),
                                        hasProperty("templated", is(false))
                                ),
                                allOf(
                                        hasProperty("rel", is("dap:objectType")),
                                        hasProperty("href", is("http://127.0.0.1:8080/objectTypes/1")),
                                        hasProperty("templated", is(false))
                                ),
                                allOf(
                                        hasProperty("rel", is("dap:dataSourcesInGroup")),
                                        hasProperty("href", is("http://127.0.0.1:8080/objectTypes/1/dataSourceGroups/1/dataSources{?page,size,sort}")),
                                        hasProperty("templated", is(true))
                                )
                        ))
                ),
                allOf(
                        hasProperty("dataSourceGroupId", is(2L)),
                        hasProperty("objectTypeId", is(1L)),
                        hasProperty("caption", is("Параметры электроэнергии A-")),
                        hasProperty("links", contains(
                                allOf(
                                        hasProperty("rel", is("self")),
                                        hasProperty("href", is("http://127.0.0.1:8080/objectTypes/1/dataSourceGroups/2")),
                                        hasProperty("templated", is(false))
                                ),
                                allOf(
                                        hasProperty("rel", is("dap:dataSourceGroup")),
                                        hasProperty("href", is("http://127.0.0.1:8080/objectTypes/1/dataSourceGroups/2")),
                                        hasProperty("templated", is(false))
                                ),
                                allOf(
                                        hasProperty("rel", is("dap:objectType")),
                                        hasProperty("href", is("http://127.0.0.1:8080/objectTypes/1")),
                                        hasProperty("templated", is(false))
                                ),
                                allOf(
                                        hasProperty("rel", is("dap:dataSourcesInGroup")),
                                        hasProperty("href", is("http://127.0.0.1:8080/objectTypes/1/dataSourceGroups/2/dataSources{?page,size,sort}")),
                                        hasProperty("templated", is(true))
                                )
                        ))
                ),
                allOf(
                        hasProperty("dataSourceGroupId", is(3L)),
                        hasProperty("objectTypeId", is(1L)),
                        hasProperty("caption", is("Параметры электроэнергии R+")),
                        hasProperty("links", contains(
                                allOf(
                                        hasProperty("rel", is("self")),
                                        hasProperty("href", is("http://127.0.0.1:8080/objectTypes/1/dataSourceGroups/3")),
                                        hasProperty("templated", is(false))
                                ),
                                allOf(
                                        hasProperty("rel", is("dap:dataSourceGroup")),
                                        hasProperty("href", is("http://127.0.0.1:8080/objectTypes/1/dataSourceGroups/3")),
                                        hasProperty("templated", is(false))
                                ),
                                allOf(
                                        hasProperty("rel", is("dap:objectType")),
                                        hasProperty("href", is("http://127.0.0.1:8080/objectTypes/1")),
                                        hasProperty("templated", is(false))
                                ),
                                allOf(
                                        hasProperty("rel", is("dap:dataSourcesInGroup")),
                                        hasProperty("href", is("http://127.0.0.1:8080/objectTypes/1/dataSourceGroups/3/dataSources{?page,size,sort}")),
                                        hasProperty("templated", is(true))
                                )
                        ))
                )
        ));
    }

}