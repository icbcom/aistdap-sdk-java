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

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import ru.icbcom.aistdapsdkjava.api.device.Device;
import ru.icbcom.aistdapsdkjava.api.physicalstructure.PhysicalStructureObject;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.datastore.DummyDataStore;
import ru.icbcom.aistdapsdkjava.impl.datastore.objectmapper.DefaultObjectMapperFactory;
import ru.icbcom.aistdapsdkjava.impl.device.DefaultDevice;

import java.io.IOException;

import static java.util.Collections.emptyMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class DefaultPhysicalStructureObjectDeserializationTest {

    private DataStore dataStore;
    private ObjectMapper objectMapper;

    public DefaultPhysicalStructureObjectDeserializationTest() {
        this.dataStore = new DummyDataStore();
        this.objectMapper = new DefaultObjectMapperFactory().create(dataStore);
    }

    @Test
    void deserializationShouldWorkProperly() throws IOException {
        String json = "{\n" +
                "  \"id\": 10008,\n" +
                "  \"objectTypeId\": 42,\n" +
                "  \"name\": \"Устройство в АСКУЭ\",\n" +
                "  \"attributeValues\": {\n" +
                "    \"Bsid\": \"1\",\n" +
                "    \"DeviceNumber\": \"1\",\n" +
                "    \"ProfSerNum\": \"123456789\"\n" +
                "  },\n" +
                "  \"descendantsCount\": 2,\n" +
                "  \"devicesCount\": 4,\n" +
                "  \"_links\": {\n" +
                "    \"self\": {\n" +
                "      \"href\": \"http://127.0.0.1:8080/physicalStructure/10008\"\n" +
                "    },\n" +
                "    \"dap:physicalStructureObject\": {\n" +
                "      \"href\": \"http://127.0.0.1:8080/physicalStructure/10008\"\n" +
                "    },\n" +
                "    \"dap:objectType\": {\n" +
                "      \"href\": \"http://127.0.0.1:8080/objectTypes/42\"\n" +
                "    },\n" +
                "    \"dap:descendants\": {\n" +
                "      \"href\": \"http://127.0.0.1:8080/physicalStructure/10008/descendants{?page,size,sort}\",\n" +
                "      \"templated\": true\n" +
                "    },\n" +
                "    \"dap:parent\": {\n" +
                "      \"href\": \"http://127.0.0.1:8080/physicalStructure/10007\"\n" +
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

        PhysicalStructureObject physicalStructureObject = objectMapper.readValue(json, DefaultPhysicalStructureObject.class);

        assertThat(physicalStructureObject, allOf(
                hasProperty("id", is(10008L)),
                hasProperty("objectTypeId", is(42L)),
                hasProperty("name", is("Устройство в АСКУЭ")),
                hasProperty("attributeValues", allOf(
                        hasEntry("Bsid", "1"),
                        hasEntry("DeviceNumber", "1"),
                        hasEntry("ProfSerNum", "123456789")
                )),
                hasProperty("descendantsCount", is(2L)),
                hasProperty("devicesCount", is(4L)),
                hasProperty("links", contains(
                        allOf(
                                hasProperty("rel", is("self")),
                                hasProperty("href", is("http://127.0.0.1:8080/physicalStructure/10008")),
                                hasProperty("templated", is(false))
                        ),
                        allOf(
                                hasProperty("rel", is("dap:physicalStructureObject")),
                                hasProperty("href", is("http://127.0.0.1:8080/physicalStructure/10008")),
                                hasProperty("templated", is(false))
                        ),
                        allOf(
                                hasProperty("rel", is("dap:objectType")),
                                hasProperty("href", is("http://127.0.0.1:8080/objectTypes/42")),
                                hasProperty("templated", is(false))
                        ),
                        allOf(
                                hasProperty("rel", is("dap:descendants")),
                                hasProperty("href", is("http://127.0.0.1:8080/physicalStructure/10008/descendants{?page,size,sort}")),
                                hasProperty("templated", is(true))
                        ),
                        allOf(
                                hasProperty("rel", is("dap:parent")),
                                hasProperty("href", is("http://127.0.0.1:8080/physicalStructure/10007")),
                                hasProperty("templated", is(false))
                        ),
                        allOf(
                                hasProperty("rel", is("curies")),
                                hasProperty("href", is("http://127.0.0.1:8080/documentation/{rel}.html")),
                                hasProperty("templated", is(true))
                        )
                ))
        ));
    }

    @Test
    void deserializationWithEmptyAttributeValuesShouldWorkProperly() throws IOException {
        String json =
                "{\n" +
                        "  \"id\": 10008,\n" +
                        "  \"objectTypeId\": 42,\n" +
                        "  \"name\": \"Устройство в АСКУЭ\",\n" +
                        "  \"attributeValues\": {},\n" +
                        "  \"descendantsCount\": 1,\n" +
                        "  \"devicesCount\": 2\n" +
                        "}";

        PhysicalStructureObject physicalStructureObject = objectMapper.readValue(json, DefaultPhysicalStructureObject.class);

        assertThat(physicalStructureObject, allOf(
                hasProperty("id", is(10008L)),
                hasProperty("objectTypeId", is(42L)),
                hasProperty("name", is("Устройство в АСКУЭ")),
                hasProperty("attributeValues", is(emptyMap())),
                hasProperty("descendantsCount", is(1L)),
                hasProperty("devicesCount", is(2L))
        ));
    }

}