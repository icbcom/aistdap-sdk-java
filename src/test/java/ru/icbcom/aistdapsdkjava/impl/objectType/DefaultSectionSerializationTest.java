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
import ru.icbcom.aistdapsdkjava.api.objecttype.Section;
import ru.icbcom.aistdapsdkjava.impl.datastore.DummyDataStore;
import ru.icbcom.aistdapsdkjava.impl.datastore.objectmapper.DefaultObjectMapperFactory;

class DefaultSectionSerializationTest {

    private ObjectMapper objectMapper = new DefaultObjectMapperFactory().create(new DummyDataStore());

    @Test
    void serializationShouldWorkProperly() throws JsonProcessingException, JSONException {
        Section section = new DefaultSection(null)
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
                .setComment("Комментарий к секции");

        String expected =
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
        JSONAssert.assertEquals(expected, objectMapper.writeValueAsString(section), true);
    }

}