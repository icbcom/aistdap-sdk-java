package ru.icbcom.aistdapsdkjava.impl.objectType;

import org.junit.jupiter.api.Test;
import ru.icbcom.aistdapsdkjava.api.objecttype.Attribute;
import ru.icbcom.aistdapsdkjava.api.objecttype.AttributeType;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectType;
import ru.icbcom.aistdapsdkjava.api.objecttype.Section;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DefaultObjectTypeTest {

    @Test
    void fieldsInitializationShouldWorkProperly() {
        ObjectType objectType = new DefaultObjectType()
                .setId(1L)
                .setName("ObjectTypeName")
                .setCaption("Заголовок типа объекта")
                .setDevice(false)
                .addSection(
                        new DefaultSection()
                                .setName("SectionName")
                                .setCaption("Заголовок секции")
                                .addAttribute(
                                        new DefaultAttribute()
                                                .setName("InterbyteTimeout")
                                                .setCaption("Таймаут межсимвольного интервала (мс)")
                                                .setType(AttributeType.INTEGER)
                                                .setMin("0")
                                                .setMax("500")
                                )
                                .addAttribute(
                                        new DefaultAttribute()
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
                hasProperty("enabled", is(true))
        ));
        Optional<Attribute> attributeOptional = objectType.getAttributeByName("Password");
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
        assertFalse(objectType.getAttributeByName("SomeAttribute").isPresent());

        Optional<Section> sectionOptional = objectType.getSectionByName("SectionName");
        assertTrue(sectionOptional.isPresent());
        assertThat(sectionOptional.get(), allOf(
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
        assertFalse(objectType.getSectionByName("SomeSection").isPresent());
    }

}