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

package ru.icbcom.aistdapsdkjava.impl.objectType;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import ru.icbcom.aistdapsdkjava.api.objecttype.EnumSetValue;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.datastore.DummyDataStore;
import ru.icbcom.aistdapsdkjava.impl.datastore.objectmapper.DefaultObjectMapperFactory;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class DefaultEnumSetValueDeserializationTest {

    private DataStore dataStore;
    private ObjectMapper objectMapper;

    DefaultEnumSetValueDeserializationTest() {
        dataStore = new DummyDataStore();
        objectMapper = new DefaultObjectMapperFactory().create(dataStore);
    }

    @Test
    void deserializationShouldWorkProperly() throws IOException {
        String json =
                "{\n" +
                        "   \"number\": 1,\n" +
                        "   \"caption\": \"Чтение (01h)\"\n" +
                        "}";

        EnumSetValue enumSetValue = objectMapper.readValue(json, DefaultEnumSetValue.class);
        assertThat(enumSetValue, allOf(
                hasProperty("number", is(1)),
                hasProperty("caption", is("Чтение (01h)")),
                hasProperty("dataStore", sameInstance(dataStore))
        ));
    }

}
