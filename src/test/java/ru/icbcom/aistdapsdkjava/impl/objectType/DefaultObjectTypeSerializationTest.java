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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import ru.icbcom.aistdapsdkjava.api.objecttype.AttributeType;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectType;
import ru.icbcom.aistdapsdkjava.impl.datastore.DummyDataStore;
import ru.icbcom.aistdapsdkjava.impl.datastore.objectmapper.DefaultObjectMapperFactory;

class DefaultObjectTypeSerializationTest {

    private ObjectMapper objectMapper = new DefaultObjectMapperFactory().create(new DummyDataStore());

    @Test
    void serializationShouldWorkProperly() throws JsonProcessingException, JSONException {
        ObjectType objectType = new DefaultObjectType(null)
                .setId(1L)
                .setName("ObjectTypeName")
                .setCaption("Заголовок типа объекта")
                .setDevice(false)
                .addSection(
                        new DefaultSection(null)
                                .setName("SectionName")
                                .setCaption("Заголовок секции")
                                .addAttribute(
                                        new DefaultAttribute(null)
                                                .setName("InterbyteTimeout")
                                                .setCaption("Таймаут межсимвольного интервала (мс)")
                                                .setType(AttributeType.INTEGER)
                                                .setMin("0")
                                                .setMax("500")
                                )
                                .addAttribute(
                                        new DefaultAttribute(null)
                                                .setName("Password")
                                                .setCaption("Пароль")
                                                .setType(AttributeType.STRING)
                                                .setMask("\\d{6}")
                                                .setDefaultValue("defaultPassword")
                                                .setComment("Комментарий к атрибуту")
                                )
                                .setComment("Комментариий к секции")
                )
                .setEnabled(true);

        String expected =
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
        JSONAssert.assertEquals(expected, objectMapper.writeValueAsString(objectType), true);
    }

    @Test
    void serializationOfObjectTypeWithEmptySectionsShouldWorkProperly() throws JsonProcessingException, JSONException {
        ObjectType objectType = new DefaultObjectType(null)
                .setId(1L)
                .setName("ObjectTypeName")
                .setCaption("Заголовок типа объекта")
                .setDevice(false)
                .setEnabled(true);

        String expected =
                "{\n" +
                        "\t\"id\":1,\n" +
                        "\t\"name\": \"ObjectTypeName\",\n" +
                        "\t\"caption\": \"Заголовок типа объекта\",\n" +
                        "\t\"device\": false,\n" +
                        "\t\"sections\": [],\n" +
                        "\t\"enabled\": true \n" +
                        "}";
        JSONAssert.assertEquals(expected, objectMapper.writeValueAsString(objectType), true);
    }

    @Test
    void nullIdShouldNotBeSerialized() throws JsonProcessingException, JSONException {
        ObjectType objectType = new DefaultObjectType(null)
                .setName("ObjectTypeName")
                .setCaption("Заголовок типа объекта")
                .setDevice(false)
                .addSection(
                        new DefaultSection(null)
                                .setName("SectionName")
                                .setCaption("Заголовок секции")
                                .addAttribute(
                                        new DefaultAttribute(null)
                                                .setName("InterbyteTimeout")
                                                .setCaption("Таймаут межсимвольного интервала (мс)")
                                                .setType(AttributeType.INTEGER)
                                                .setMin("0")
                                                .setMax("500")
                                )
                                .addAttribute(
                                        new DefaultAttribute(null)
                                                .setName("Password")
                                                .setCaption("Пароль")
                                                .setType(AttributeType.STRING)
                                                .setMask("\\d{6}")
                                                .setDefaultValue("defaultPassword")
                                                .setComment("Комментарий к атрибуту")
                                )
                                .setComment("Комментариий к секции")
                )
                .setEnabled(true);

        String expected =
                "{\n" +
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
        JSONAssert.assertEquals(expected, objectMapper.writeValueAsString(objectType), true);
    }

}