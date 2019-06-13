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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import ru.icbcom.aistdapsdkjava.api.datasourcegroup.DataSourceGroup;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.datastore.DummyDataStore;
import ru.icbcom.aistdapsdkjava.impl.datastore.objectmapper.DefaultObjectMapperFactory;

class DefaultDataSourceGroupSerializationTest {

    private DataStore dataStore = new DummyDataStore();
    private ObjectMapper objectMapper = new DefaultObjectMapperFactory().create(dataStore);

    @Test
    void serializationShouldWorkProperly() throws JsonProcessingException, JSONException {
        DataSourceGroup dataSourceGroup = new DefaultDataSourceGroup(dataStore)
                .setDataSourceGroupId(1000L)
                .setObjectTypeId(100L)
                .setCaption("Название группы источников данных");

        String expected =
                "{\n" +
                        "  \"dataSourceGroupId\": 1000,\n" +
                        "  \"objectTypeId\": 100,\n" +
                        "  \"caption\": \"Название группы источников данных\"\n" +
                        "}";
        JSONAssert.assertEquals(expected, objectMapper.writeValueAsString(dataSourceGroup), true);
    }

}