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
import ru.icbcom.aistdapsdkjava.api.objecttype.Attribute;
import ru.icbcom.aistdapsdkjava.api.objecttype.AttributeType;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.datastore.DummyDataStore;
import ru.icbcom.aistdapsdkjava.impl.datastore.objectmapper.DefaultObjectMapperFactory;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;

class DefaultAttributeDeserializationTest {

    private DataStore dataStore;
    private ObjectMapper objectMapper;

    DefaultAttributeDeserializationTest() {
        dataStore = new DummyDataStore();
        objectMapper = new DefaultObjectMapperFactory().create(dataStore);
    }

    @Test
    void integerAttributeDeserializationShouldWorkProperly() throws IOException {
        String json =
                "{" +
                        "\"name\": \"InterbyteTimeout\"," +
                        "\"caption\": \"Таймаут межсимвольного интервала (мс)\"," +
                        "\"type\": \"Integer\"," +
                        "\"min\": \"0\"," +
                        "\"max\": \"500\"" +
                        "}";

        Attribute attribute = objectMapper.readValue(json, DefaultAttribute.class);
        assertThat(attribute, allOf(
                hasProperty("name", is("InterbyteTimeout")),
                hasProperty("caption", is("Таймаут межсимвольного интервала (мс)")),
                hasProperty("type", is(AttributeType.INTEGER)),
                hasProperty("min", is("0")),
                hasProperty("max", is("500")),
                hasProperty("mask", is(nullValue())),
                hasProperty("enumSetValues", is(empty())),
                hasProperty("defaultValue", is(nullValue())),
                hasProperty("comment", is(nullValue())),
                hasProperty("links", is(empty())),
                hasProperty("dataStore", sameInstance(dataStore))
        ));
    }

    @Test
    void stringAttributeDeserializationShouldWorkProperly() throws IOException {
        String json =
                "{" +
                        "\"name\": \"Password\"," +
                        "\"caption\": \"Пароль\"," +
                        "\"type\": \"String\"," +
                        "\"mask\": \"\\\\d{6}\"," +
                        "\"defaultValue\": \"defaultPassword\"," +
                        "\"comment\": \"Комментарий к атрибуту\"" +
                        "}";

        Attribute attribute = objectMapper.readValue(json, DefaultAttribute.class);
        assertThat(attribute, allOf(
                hasProperty("name", is("Password")),
                hasProperty("caption", is("Пароль")),
                hasProperty("type", is(AttributeType.STRING)),
                hasProperty("min", is(nullValue())),
                hasProperty("max", is(nullValue())),
                hasProperty("mask", is("\\d{6}")),
                hasProperty("enumSetValues", is(empty())),
                hasProperty("defaultValue", is("defaultPassword")),
                hasProperty("comment", is("Комментарий к атрибуту")),
                hasProperty("links", is(empty())),
                hasProperty("dataStore", sameInstance(dataStore))

        ));
    }

    @Test
    void booleanAttributeDeserializationShouldWorkProperly() throws IOException {
        String json =
                "{" +
                        "\"name\": \"UseServerUtc\"," +
                        "\"caption\": \"Использовать часовой пояс сервера\"," +
                        "\"type\": \"Boolean\"" +
                        "}";

        Attribute attribute = objectMapper.readValue(json, DefaultAttribute.class);
        assertThat(attribute, allOf(
                hasProperty("name", is("UseServerUtc")),
                hasProperty("caption", is("Использовать часовой пояс сервера")),
                hasProperty("type", is(AttributeType.BOOLEAN)),
                hasProperty("min", is(nullValue())),
                hasProperty("max", is(nullValue())),
                hasProperty("mask", is(nullValue())),
                hasProperty("enumSetValues", is(empty())),
                hasProperty("defaultValue", is(nullValue())),
                hasProperty("comment", is(nullValue())),
                hasProperty("links", is(empty())),
                hasProperty("dataStore", sameInstance(dataStore))
        ));
    }

    @Test
    void floatAttributeDeserializationShouldWorkProperly() throws IOException {
        String json =
                "{" +
                        "\"name\": \"Latitude\"," +
                        "\"caption\": \"Широта\"," +
                        "\"type\": \"Float\"" +
                        "}";

        Attribute attribute = objectMapper.readValue(json, DefaultAttribute.class);
        assertThat(attribute, allOf(
                hasProperty("name", is("Latitude")),
                hasProperty("caption", is("Широта")),
                hasProperty("type", is(AttributeType.FLOAT)),
                hasProperty("min", is(nullValue())),
                hasProperty("max", is(nullValue())),
                hasProperty("mask", is(nullValue())),
                hasProperty("enumSetValues", is(empty())),
                hasProperty("defaultValue", is(nullValue())),
                hasProperty("comment", is(nullValue())),
                hasProperty("links", is(empty())),
                hasProperty("dataStore", sameInstance(dataStore))
        ));
    }

    @Test
    void timeAttributeDeserializationShouldWorkProperly() throws IOException {
        String json =
                "{" +
                        "\"name\": \"AlarmTime\"," +
                        "\"caption\": \"Время аларма\"," +
                        "\"type\": \"Time\"" +
                        "}";

        Attribute attribute = objectMapper.readValue(json, DefaultAttribute.class);
        assertThat(attribute, allOf(
                hasProperty("name", is("AlarmTime")),
                hasProperty("caption", is("Время аларма")),
                hasProperty("type", is(AttributeType.TIME)),
                hasProperty("min", is(nullValue())),
                hasProperty("max", is(nullValue())),
                hasProperty("mask", is(nullValue())),
                hasProperty("enumSetValues", is(empty())),
                hasProperty("defaultValue", is(nullValue())),
                hasProperty("comment", is(nullValue())),
                hasProperty("links", is(empty())),
                hasProperty("dataStore", sameInstance(dataStore))
        ));
    }

    @Test
    void dateAttributeDeserializationShouldWorkProperly() throws IOException {
        String json =
                "{" +
                        "\"name\": \"AlarmDate\"," +
                        "\"caption\": \"Дата аларма\"," +
                        "\"type\": \"Date\"" +
                        "}";

        Attribute attribute = objectMapper.readValue(json, DefaultAttribute.class);
        assertThat(attribute, allOf(
                hasProperty("name", is("AlarmDate")),
                hasProperty("caption", is("Дата аларма")),
                hasProperty("type", is(AttributeType.DATE)),
                hasProperty("min", is(nullValue())),
                hasProperty("max", is(nullValue())),
                hasProperty("mask", is(nullValue())),
                hasProperty("enumSetValues", is(empty())),
                hasProperty("defaultValue", is(nullValue())),
                hasProperty("comment", is(nullValue())),
                hasProperty("links", is(empty())),
                hasProperty("dataStore", sameInstance(dataStore))
        ));
    }

    @Test
    void dateTimeAttributeDeserializationShouldWorkProperly() throws IOException {
        String json =
                "{" +
                        "\"name\": \"AlarmDateTime\"," +
                        "\"caption\": \"Дата и время аларма\"," +
                        "\"type\": \"DateTime\"" +
                        "}";

        Attribute attribute = objectMapper.readValue(json, DefaultAttribute.class);
        assertThat(attribute, allOf(
                hasProperty("name", is("AlarmDateTime")),
                hasProperty("caption", is("Дата и время аларма")),
                hasProperty("type", is(AttributeType.DATETIME)),
                hasProperty("min", is(nullValue())),
                hasProperty("max", is(nullValue())),
                hasProperty("mask", is(nullValue())),
                hasProperty("enumSetValues", is(empty())),
                hasProperty("defaultValue", is(nullValue())),
                hasProperty("comment", is(nullValue())),
                hasProperty("links", is(empty())),
                hasProperty("dataStore", sameInstance(dataStore))
        ));
    }

    @Test
    void enumerationAttributeDeserializationShouldWorkProperly() throws IOException {
        String json =
                "{" +
                        "\"name\": \"Baud\"," +
                        "\"caption\": \"Скорость порта\"," +
                        "\"type\": \"Enumeration\"," +
                        "\"enumSetValues\": [" +
                        "   {" +
                        "       \"number\": 1," +
                        "       \"caption\": \"1200\"" +
                        "   }," +
                        "   {" +
                        "       \"number\": 2," +
                        "       \"caption\": \"2400\"" +
                        "   }," +
                        "   {" +
                        "       \"number\": 3," +
                        "       \"caption\": \"4800\"" +
                        "   }" +
                        "]," +
                        "\"defaultValue\": \"1\"" +
                        "}";

        Attribute attribute = objectMapper.readValue(json, DefaultAttribute.class);
        assertThat(attribute, allOf(
                hasProperty("name", is("Baud")),
                hasProperty("caption", is("Скорость порта")),
                hasProperty("type", is(AttributeType.ENUMERATION)),
                hasProperty("min", is(nullValue())),
                hasProperty("max", is(nullValue())),
                hasProperty("mask", is(nullValue())),
                hasProperty("enumSetValues", is(contains(
                        allOf(
                                hasProperty("number", is(1)),
                                hasProperty("caption", is("1200"))
                        ),
                        allOf(
                                hasProperty("number", is(2)),
                                hasProperty("caption", is("2400"))
                        ),
                        allOf(
                                hasProperty("number", is(3)),
                                hasProperty("caption", is("4800"))
                        )
                ))),
                hasProperty("defaultValue", is("1")),
                hasProperty("comment", is(nullValue())),
                hasProperty("links", is(empty())),
                hasProperty("dataStore", sameInstance(dataStore))
        ));
    }

    @Test
    void setAttributeDeserializationShouldWorkProperly() throws IOException {
        String json =
                "{" +
                        "\"name\": \"Parity\"," +
                        "\"caption\": \"Четность\"," +
                        "\"type\": \"Set\"," +
                        "\"enumSetValues\": [" +
                        "   {" +
                        "       \"number\": 1," +
                        "       \"caption\": \"None\"" +
                        "   }," +
                        "   {" +
                        "       \"number\": 2," +
                        "       \"caption\": \"Even\"" +
                        "   }," +
                        "   {" +
                        "       \"number\": 3," +
                        "       \"caption\": \"Odd\"" +
                        "   }" +
                        "]" +
                        "}";

        Attribute attribute = objectMapper.readValue(json, DefaultAttribute.class);
        assertThat(attribute, allOf(
                hasProperty("name", is("Parity")),
                hasProperty("caption", is("Четность")),
                hasProperty("type", is(AttributeType.SET)),
                hasProperty("min", is(nullValue())),
                hasProperty("max", is(nullValue())),
                hasProperty("mask", is(nullValue())),
                hasProperty("enumSetValues", is(contains(
                        allOf(
                                hasProperty("number", is(1)),
                                hasProperty("caption", is("None"))
                        ),
                        allOf(
                                hasProperty("number", is(2)),
                                hasProperty("caption", is("Even"))
                        ),
                        allOf(
                                hasProperty("number", is(3)),
                                hasProperty("caption", is("Odd"))
                        )
                ))),
                hasProperty("defaultValue", is(nullValue())),
                hasProperty("comment", is(nullValue())),
                hasProperty("links", is(empty())),
                hasProperty("dataStore", sameInstance(dataStore))
        ));
    }

}