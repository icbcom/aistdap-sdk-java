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

import org.junit.jupiter.api.Test;
import ru.icbcom.aistdapsdkjava.api.objecttype.Attribute;
import ru.icbcom.aistdapsdkjava.api.objecttype.AttributeType;
import ru.icbcom.aistdapsdkjava.api.objecttype.EnumSetValue;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DefaultAttributeTest {

    @Test
    void integerAttributeFieldsInitializationShouldWorkProperly() {
        Attribute attribute = new DefaultAttribute(null)
                .setName("InterbyteTimeout")
                .setCaption("Таймаут межсимвольного интервала (мс)")
                .setType(AttributeType.INTEGER)
                .setMin("0")
                .setMax("500");

        assertThat(attribute, allOf(
                hasProperty("name", is("InterbyteTimeout")),
                hasProperty("caption", is("Таймаут межсимвольного интервала (мс)")),
                hasProperty("type", is(AttributeType.INTEGER)),
                hasProperty("min", is("0")),
                hasProperty("max", is("500")),
                hasProperty("mask", is(nullValue())),
                hasProperty("enumSetValues", is(empty())),
                hasProperty("defaultValue", is(nullValue())),
                hasProperty("comment", is(nullValue()))
        ));
    }

    @Test
    void stringAttributeFieldsInitializationShouldWorkProperly() {
        Attribute attribute = new DefaultAttribute(null)
                .setName("Password")
                .setCaption("Пароль")
                .setType(AttributeType.STRING)
                .setMask("\\d{6}")
                .setDefaultValue("defaultPassword")
                .setComment("Комментарий к атрибуту");

        assertThat(attribute, allOf(
                hasProperty("name", is("Password")),
                hasProperty("caption", is("Пароль")),
                hasProperty("type", is(AttributeType.STRING)),
                hasProperty("min", is(nullValue())),
                hasProperty("max", is(nullValue())),
                hasProperty("mask", is("\\d{6}")),
                hasProperty("enumSetValues", is(empty())),
                hasProperty("defaultValue", is("defaultPassword")),
                hasProperty("comment", is("Комментарий к атрибуту"))
        ));
    }

    @Test
    void enumerationAttributeFieldsInitializationShouldWorkProperly() {
        Attribute attribute = new DefaultAttribute(null)
                .setName("Baud")
                .setCaption("Скорость порта")
                .setType(AttributeType.ENUMERATION)
                .addEnumSetValue(new DefaultEnumSetValue(null).setNumber(1).setCaption("1200"))
                .addEnumSetValue(new DefaultEnumSetValue(null).setNumber(2).setCaption("2400"))
                .addEnumSetValue(new DefaultEnumSetValue(null).setNumber(3).setCaption("4800"));

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
                )))
        ));
        Optional<EnumSetValue> enumSetValueOptional = attribute.getEnumSetValueByNumber(1);
        assertTrue(enumSetValueOptional.isPresent());
        assertThat(enumSetValueOptional.get(), allOf(
                hasProperty("number", is(1)),
                hasProperty("caption", is("1200"))
        ));
        assertFalse(attribute.getEnumSetValueByNumber(20).isPresent());
    }

    @Test
    void setAttributeFieldsInitializationShouldWorkProperly() {
        Attribute attribute = new DefaultAttribute(null)
                .setName("Parity")
                .setCaption("Четность")
                .setType(AttributeType.SET)
                .addEnumSetValue(new DefaultEnumSetValue(null).setNumber(1).setCaption("None"))
                .addEnumSetValue(new DefaultEnumSetValue(null).setNumber(2).setCaption("Even"))
                .addEnumSetValue(new DefaultEnumSetValue(null).setNumber(3).setCaption("Odd"));

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
                )))
        ));
    }


}