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

package ru.icbcom.aistdapsdkjava.impl.physicalstructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import ru.icbcom.aistdapsdkjava.api.physicalstructure.PhysicalStructureObject;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.datastore.DummyDataStore;
import ru.icbcom.aistdapsdkjava.impl.datastore.objectmapper.DefaultObjectMapperFactory;

class DefaultPhysicalStructureObjectSerializationTest {

    private DataStore dataStore;
    private ObjectMapper objectMapper;

    public DefaultPhysicalStructureObjectSerializationTest() {
        this.dataStore = new DummyDataStore();
        this.objectMapper = new DefaultObjectMapperFactory().create(dataStore);
    }

    @Test
    void serializationShouldWorkProperly() throws JsonProcessingException, JSONException {
        PhysicalStructureObject physicalStructureObject = new DefaultPhysicalStructureObject(dataStore)
                .setId(100L)
                .setObjectTypeId(1L)
                .setName("Название объекта физической структуры")
                .setAttributeValue("Server", "puma.icbcom.ru")
                .setAttributeValue("Port", "2755")
                .setAttributeValue("AdditionalAttribute", "some attribute value")
                .setDescendantsCount(5L)
                .setDevicesCount(3L);

        String expected =
                "{" +
                        "   \"id\": 100," +
                        "   \"objectTypeId\": 1," +
                        "   \"name\": \"Название объекта физической структуры\"," +
                        "   \"attributeValues\": {" +
                        "       \"Server\": \"puma.icbcom.ru\"," +
                        "       \"Port\": \"2755\"," +
                        "       \"AdditionalAttribute\": \"some attribute value\"" +
                        "   }" +
                        "}";
        JSONAssert.assertEquals(expected, objectMapper.writeValueAsString(physicalStructureObject), true);
    }

    @Test
    void emptyAttributeValuesSerializationShouldWorkProperly() throws JsonProcessingException, JSONException {
        PhysicalStructureObject physicalStructureObject = new DefaultPhysicalStructureObject(dataStore)
                .setId(100L)
                .setObjectTypeId(1L)
                .setName("Название объекта физической структуры")
                .setDescendantsCount(5L)
                .setDevicesCount(3L);

        String expected =
                "{" +
                        "   \"id\": 100," +
                        "   \"objectTypeId\": 1," +
                        "   \"name\": \"Название объекта физической структуры\"," +
                        "   \"attributeValues\": {}" +
                        "}";
        JSONAssert.assertEquals(expected, objectMapper.writeValueAsString(physicalStructureObject), true);
    }

    @Test
    void nullIdShouldNotBeSerialized() throws JsonProcessingException, JSONException {
        PhysicalStructureObject physicalStructureObject = new DefaultPhysicalStructureObject(dataStore)
                .setObjectTypeId(1L)
                .setName("Название объекта физической структуры")
                .setAttributeValue("Server", "puma.icbcom.ru")
                .setAttributeValue("Port", "2755")
                .setAttributeValue("AdditionalAttribute", "some attribute value")
                .setDescendantsCount(5L)
                .setDevicesCount(3L);

        String expected =
                "{" +
                        "   \"objectTypeId\": 1," +
                        "   \"name\": \"Название объекта физической структуры\"," +
                        "   \"attributeValues\": {" +
                        "       \"Server\": \"puma.icbcom.ru\"," +
                        "       \"Port\": \"2755\"," +
                        "       \"AdditionalAttribute\": \"some attribute value\"" +
                        "   }" +
                        "}";
        JSONAssert.assertEquals(expected, objectMapper.writeValueAsString(physicalStructureObject), true);
    }



}