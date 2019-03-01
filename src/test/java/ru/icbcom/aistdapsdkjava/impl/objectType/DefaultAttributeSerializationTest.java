package ru.icbcom.aistdapsdkjava.impl.objectType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import ru.icbcom.aistdapsdkjava.api.objecttype.Attribute;
import ru.icbcom.aistdapsdkjava.api.objecttype.AttributeType;
import ru.icbcom.aistdapsdkjava.impl.objectmapper.ObjectMappers;

class DefaultAttributeSerializationTest {

    private ObjectMapper objectMapper = ObjectMappers.create();

    @Test
    void integerAttributeSerializationTest() throws JsonProcessingException, JSONException {
        Attribute attribute = new DefaultAttribute()
                .setName("InterbyteTimeout")
                .setCaption("Таймаут межсимвольного интервала (мс)")
                .setType(AttributeType.INTEGER)
                .setMin("0")
                .setMax("500");

        String expected =
                "{" +
                        "\"name\": \"InterbyteTimeout\"," +
                        "\"caption\": \"Таймаут межсимвольного интервала (мс)\"," +
                        "\"type\": \"Integer\"," +
                        "\"min\": \"0\"," +
                        "\"max\": \"500\"" +
                        "}";
        JSONAssert.assertEquals(expected, objectMapper.writeValueAsString(attribute), true);
    }

    @Test
    void stringAttributeSerializationTest() throws JsonProcessingException, JSONException {
        Attribute attribute = new DefaultAttribute()
                .setName("Password")
                .setCaption("Пароль")
                .setType(AttributeType.STRING)
                .setMask("\\d{6}")
                .setDefaultValue("defaultPassword")
                .setComment("Комментарий к атрибуту");


        String expected =
                "{" +
                        "\"name\": \"Password\"," +
                        "\"caption\": \"Пароль\"," +
                        "\"type\": \"String\"," +
                        "\"mask\": \"\\\\d{6}\"," +
                        "\"defaultValue\": \"defaultPassword\"," +
                        "\"comment\": \"Комментарий к атрибуту\"" +
                        "}";
        JSONAssert.assertEquals(expected, objectMapper.writeValueAsString(attribute), true);
    }

    @Test
    void booleanAttributeSerializationTest() throws JsonProcessingException, JSONException {
        Attribute attribute = new DefaultAttribute()
                .setName("UseServerUtc")
                .setCaption("Использовать часовой пояс сервера")
                .setType(AttributeType.BOOLEAN);

        String expected =
                "{" +
                        "\"name\": \"UseServerUtc\"," +
                        "\"caption\": \"Использовать часовой пояс сервера\"," +
                        "\"type\": \"Boolean\"" +
                        "}";
        JSONAssert.assertEquals(expected, objectMapper.writeValueAsString(attribute), true);
    }

    @Test
    void floatAttributeSerializationTest() throws JsonProcessingException, JSONException {
        Attribute attribute = new DefaultAttribute()
                .setName("Latitude")
                .setCaption("Широта")
                .setType(AttributeType.FLOAT);

        String expected =
                "{" +
                        "\"name\": \"Latitude\"," +
                        "\"caption\": \"Широта\"," +
                        "\"type\": \"Float\"" +
                        "}";
        JSONAssert.assertEquals(expected, objectMapper.writeValueAsString(attribute), true);
    }

    @Test
    void timeAttributeSerializationTest() throws JsonProcessingException, JSONException {
        Attribute attribute = new DefaultAttribute()
                .setName("AlarmTime")
                .setCaption("Время аларма")
                .setType(AttributeType.TIME);

        String expected =
                "{" +
                        "\"name\": \"AlarmTime\"," +
                        "\"caption\": \"Время аларма\"," +
                        "\"type\": \"Time\"" +
                        "}";
        JSONAssert.assertEquals(expected, objectMapper.writeValueAsString(attribute), true);
    }

    @Test
    void dateAttributeSerializationTest() throws JsonProcessingException, JSONException {
        Attribute attribute = new DefaultAttribute()
                .setName("AlarmDate")
                .setCaption("Дата аларма")
                .setType(AttributeType.DATE);

        String expected =
                "{" +
                        "\"name\": \"AlarmDate\"," +
                        "\"caption\": \"Дата аларма\"," +
                        "\"type\": \"Date\"" +
                        "}";
        JSONAssert.assertEquals(expected, objectMapper.writeValueAsString(attribute), true);
    }

    @Test
    void dateTimeAttributeSerializationTest() throws JsonProcessingException, JSONException {
        Attribute attribute = new DefaultAttribute()
                .setName("AlarmDateTime")
                .setCaption("Дата и время аларма")
                .setType(AttributeType.DATETIME);

        String expected =
                "{" +
                        "\"name\": \"AlarmDateTime\"," +
                        "\"caption\": \"Дата и время аларма\"," +
                        "\"type\": \"DateTime\"" +
                        "}";
        JSONAssert.assertEquals(expected, objectMapper.writeValueAsString(attribute), true);
    }

    @Test
    void enumerationAttributeSerializationTest() throws JsonProcessingException, JSONException {
        Attribute attribute = new DefaultAttribute()
                .setName("Baud")
                .setCaption("Скорость порта")
                .setType(AttributeType.ENUMERATION)
                .addEnumSetValue(new DefaultEnumSetValue().setNumber(1).setCaption("1200"))
                .addEnumSetValue(new DefaultEnumSetValue().setNumber(2).setCaption("2400"))
                .addEnumSetValue(new DefaultEnumSetValue().setNumber(3).setCaption("4800"))
                .setDefaultValue("1");

        String expected =
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
        JSONAssert.assertEquals(expected, objectMapper.writeValueAsString(attribute), true);
    }

    @Test
    void setAttributeSerializationTest() throws JsonProcessingException, JSONException {
        Attribute attribute = new DefaultAttribute()
                .setName("Parity")
                .setCaption("Четность")
                .setType(AttributeType.SET)
                .addEnumSetValue(new DefaultEnumSetValue().setNumber(1).setCaption("None"))
                .addEnumSetValue(new DefaultEnumSetValue().setNumber(2).setCaption("Even"))
                .addEnumSetValue(new DefaultEnumSetValue().setNumber(3).setCaption("Odd"));

        String expected =
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
        JSONAssert.assertEquals(expected, objectMapper.writeValueAsString(attribute), true);
    }

}