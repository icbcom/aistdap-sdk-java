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
import ru.icbcom.aistdapsdkjava.api.objecttype.AttributeType;
import ru.icbcom.aistdapsdkjava.api.objecttype.Section;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.datastore.DummyDataStore;
import ru.icbcom.aistdapsdkjava.impl.datastore.objectmapper.DefaultObjectMapperFactory;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class DefaultSectionDeserializationTest {

    private DataStore dataStore;
    private ObjectMapper objectMapper;

    public DefaultSectionDeserializationTest() {
        this.dataStore = new DummyDataStore();
        this.objectMapper = new DefaultObjectMapperFactory().create(dataStore);
    }

    @Test
    void deserializationShouldWorkProperly() throws IOException {
        String json =
                "{" +
                        "\"name\": \"SectionName\"," +
                        "\"caption\": \"Заголовок секции\"," +
                        "\"attributes\": [" +
                        "  {" +
                        "\"name\": \"InterbyteTimeout\"," +
                        "\"caption\": \"Таймаут межсимвольного интервала (мс)\"," +
                        "\"type\": \"Integer\"," +
                        "\"min\": \"0\"," +
                        "\"max\": \"500\"" +
                        "  }," +
                        "  {" +
                        "\"name\": \"Password\"," +
                        "\"caption\": \"Пароль\"," +
                        "\"type\": \"String\"," +
                        "\"mask\": \"\\\\d{6}\"," +
                        "\"defaultValue\": \"defaultPassword\"," +
                        "\"comment\": \"Комментарий к атрибуту\"" +
                        "  }" +
                        "]," +
                        "\"comment\": \"Комментарий к секции\"" +
                        "}";

        Section section = objectMapper.readValue(json, DefaultSection.class);
        assertThat(section, allOf(
                hasProperty("name", is("SectionName")),
                hasProperty("caption", is("Заголовок секции")),
                hasProperty("attributes", contains(
                        allOf(
                                hasProperty("name", is("InterbyteTimeout")),
                                hasProperty("caption", is("Таймаут межсимвольного интервала (мс)")),
                                hasProperty("type", is(AttributeType.INTEGER)),
                                hasProperty("min", is("0")),
                                hasProperty("max", is("500")),
                                hasProperty("mask", is(nullValue())),
                                hasProperty("enumSetValues", is(empty())),
                                hasProperty("defaultValue", is(nullValue())),
                                hasProperty("comment", is(nullValue())),
                                hasProperty("links", is(empty()))
                        ),
                        allOf(
                                hasProperty("name", is("Password")),
                                hasProperty("caption", is("Пароль")),
                                hasProperty("type", is(AttributeType.STRING)),
                                hasProperty("min", is(nullValue())),
                                hasProperty("max", is(nullValue())),
                                hasProperty("mask", is("\\d{6}")),
                                hasProperty("enumSetValues", is(empty())),
                                hasProperty("defaultValue", is("defaultPassword")),
                                hasProperty("comment", is("Комментарий к атрибуту")),
                                hasProperty("links", is(empty()))
                        )
                )),
                hasProperty("comment", is("Комментарий к секции")),
                hasProperty("dataStore", sameInstance(dataStore))
        ));
    }

}