package ru.icbcom.aistdapsdkjava.impl.objectType;

import org.junit.jupiter.api.Test;
import ru.icbcom.aistdapsdkjava.integrationtest.objecttype.Attribute;
import ru.icbcom.aistdapsdkjava.integrationtest.objecttype.AttributeType;
import ru.icbcom.aistdapsdkjava.integrationtest.objecttype.Section;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DefaultSectionTest {

    @Test
    void fieldsInitializationShouldWorkProperly() {
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
                .setComment("Комментариий к секции");

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
        ));

        Optional<Attribute> attributeOptional = section.getAttributeByName("Password");
        assertTrue(attributeOptional.isPresent());
        assertThat(attributeOptional.get(), allOf(
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
        assertFalse(section.getAttributeByName("SomeAttribute").isPresent());
    }

}