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
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectType;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.datastore.DummyDataStore;
import ru.icbcom.aistdapsdkjava.impl.datastore.objectmapper.DefaultObjectMapperFactory;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class DefaultObjectTypeDeserializationTest {

    private DataStore dataStore;
    private ObjectMapper objectMapper;

    public DefaultObjectTypeDeserializationTest() {
        this.dataStore = new DummyDataStore();
        this.objectMapper = new DefaultObjectMapperFactory().create(dataStore);
    }

    @Test
    void deserializationShouldWorkProperly() throws IOException {
        String json =
                "{\n" +
                        "\t\"id\":1,\n" +
                        "\t\"name\": \"ObjectTypeName\",\n" +
                        "\t\"caption\": \"Заголовок типа объекта\",\n" +
                        "\t\"device\": false,\n" +
                        "\t\"sections\": [\n" +
                        "\t\t{  \n" +
                        "         \"name\":\"SectionName\",\n" +
                        "         \"caption\":\"Заголовок секции\",\n" +
                        "         \"comment\":\"Комментариий к секции\",\n" +
                        "         \"attributes\":[  \n" +
                        "            {  \n" +
                        "               \"name\":\"InterbyteTimeout\",\n" +
                        "               \"caption\":\"Таймаут межсимвольного интервала (мс)\",\n" +
                        "               \"type\":\"Integer\",\n" +
                        "               \"min\":\"0\",\n" +
                        "               \"max\":\"500\"\n" +
                        "            },\n" +
                        "            {  \n" +
                        "               \"name\":\"Password\",\n" +
                        "               \"caption\":\"Пароль\",\n" +
                        "               \"type\":\"String\",\n" +
                        "               \"mask\":\"\\\\d{6}\",\n" +
                        "               \"defaultValue\":\"defaultPassword\",\n" +
                        "               \"comment\":\"Комментарий к атрибуту\"\n" +
                        "            }\n" +
                        "         ]\n" +
                        "      }\n" +
                        "\t],\n" +
                        "\t\"enabled\": true \n" +
                        "}";

        ObjectType objectType = objectMapper.readValue(json, DefaultObjectType.class);
        assertThat(objectType, allOf(
                hasProperty("id", is(1L)),
                hasProperty("name", is("ObjectTypeName")),
                hasProperty("caption", is("Заголовок типа объекта")),
                hasProperty("device", is(false)),
                hasProperty("sections", hasItem(
                        allOf(
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
                                                hasProperty("comment", is(nullValue()))
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
                                                hasProperty("comment", is("Комментарий к атрибуту"))
                                        )
                                )),
                                hasProperty("comment", is("Комментариий к секции"))
                        )
                )),
                hasProperty("enabled", is(true)),
                hasProperty("links", is(empty())),
                hasProperty("dataStore", sameInstance(dataStore))
        ));
    }

    @Test
    void deserializationWithLinksShouldWorkProperly() throws IOException {
        String json =
                "{\n" +
                        "        \"id\": 18,\n" +
                        "        \"name\": \"Imab-24.02\",\n" +
                        "        \"caption\": \"ИМАБ-24.02\",\n" +
                        "        \"device\": true,\n" +
                        "        \"sections\": [\n" +
                        "          {\n" +
                        "            \"name\": \"Settings\",\n" +
                        "            \"caption\": \"Настройки\",\n" +
                        "            \"attributes\": [\n" +
                        "              {\n" +
                        "                \"name\": \"Address\",\n" +
                        "                \"caption\": \"Сетевой адрес\",\n" +
                        "                \"type\": \"Integer\"\n" +
                        "              }\n" +
                        "            ]\n" +
                        "          }\n" +
                        "        ],\n" +
                        "        \"enabled\": false,\n" +
                        "        \"_links\": {\n" +
                        "          \"self\": {\n" +
                        "            \"href\": \"http://127.0.0.1:8080/objectTypes/18\"\n" +
                        "          },\n" +
                        "          \"dap:objectType\": {\n" +
                        "            \"href\": \"http://127.0.0.1:8080/objectTypes/18\"\n" +
                        "          },\n" +
                        "          \"dap:dataSources\": {\n" +
                        "            \"href\": \"http://127.0.0.1:8080/objectTypes/18/dataSources{?page,size,sort}\",\n" +
                        "            \"templated\": true\n" +
                        "          },\n" +
                        "          \"dap:dataSourceGroups\": {\n" +
                        "            \"href\": \"http://127.0.0.1:8080/objectTypes/18/dataSourceGroups{?page,size,sort}\",\n" +
                        "            \"templated\": true\n" +
                        "          }\n" +
                        "        }\n" +
                        "      }";

        ObjectType objectType = objectMapper.readValue(json, DefaultObjectType.class);
        assertThat(objectType, allOf(
                hasProperty("id", is(18L)),
                hasProperty("name", is("Imab-24.02")),
                hasProperty("caption", is("ИМАБ-24.02")),
                hasProperty("device", is(true)),
                hasProperty("sections", hasItem(
                        allOf(
                                hasProperty("name", is("Settings")),
                                hasProperty("caption", is("Настройки")),
                                hasProperty("attributes", contains(
                                        allOf(
                                                hasProperty("name", is("Address")),
                                                hasProperty("caption", is("Сетевой адрес")),
                                                hasProperty("type", is(AttributeType.INTEGER)),
                                                hasProperty("min", is(nullValue())),
                                                hasProperty("max", is(nullValue())),
                                                hasProperty("mask", is(nullValue())),
                                                hasProperty("enumSetValues", is(empty())),
                                                hasProperty("defaultValue", is(nullValue())),
                                                hasProperty("comment", is(nullValue()))
                                        )
                                )),
                                hasProperty("comment", is(nullValue()))
                        )
                )),
                hasProperty("enabled", is(false)),
                hasProperty("links", contains(
                        allOf(
                                hasProperty("rel", is("self")),
                                hasProperty("href", is("http://127.0.0.1:8080/objectTypes/18")),
                                hasProperty("templated", is(false))
                        ),
                        allOf(
                                hasProperty("rel", is("dap:objectType")),
                                hasProperty("href", is("http://127.0.0.1:8080/objectTypes/18")),
                                hasProperty("templated", is(false))
                        ),
                        allOf(
                                hasProperty("rel", is("dap:dataSources")),
                                hasProperty("href", is("http://127.0.0.1:8080/objectTypes/18/dataSources{?page,size,sort}")),
                                hasProperty("templated", is(true))
                        ),
                        allOf(
                                hasProperty("rel", is("dap:dataSourceGroups")),
                                hasProperty("href", is("http://127.0.0.1:8080/objectTypes/18/dataSourceGroups{?page,size,sort}")),
                                hasProperty("templated", is(true))
                        )
                ))
        ));
    }

    @Test
    void deserializationOfObjectTypeWithEmptySectionShouldWorkProperly() throws IOException {
        String json =
                "{\n" +
                        "\t\"id\":1,\n" +
                        "\t\"name\": \"ObjectTypeName\",\n" +
                        "\t\"caption\": \"Заголовок типа объекта\",\n" +
                        "\t\"device\": false,\n" +
                        "\t\"sections\": [],\n" +
                        "\t\"enabled\": true \n" +
                        "}";

        ObjectType objectType = objectMapper.readValue(json, DefaultObjectType.class);
        assertThat(objectType, allOf(
                hasProperty("id", is(1L)),
                hasProperty("name", is("ObjectTypeName")),
                hasProperty("caption", is("Заголовок типа объекта")),
                hasProperty("device", is(false)),
                hasProperty("sections", is(empty())),
                hasProperty("enabled", is(true)),
                hasProperty("links", is(empty())),
                hasProperty("dataStore", sameInstance(dataStore))
        ));
    }

}