package ru.icbcom.aistdapsdkjava;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ru.icbcom.aistdapsdkjava.api.client.Client;
import ru.icbcom.aistdapsdkjava.api.client.Clients;
import ru.icbcom.aistdapsdkjava.api.objecttype.*;

import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Slf4j
@Disabled
public class ObjectTypeDemo {

    private Client client;

    @BeforeEach
    void setup() {
        client = Clients.builder()
                .setBaseUrl("http://127.0.0.1:8080/")
                .setLogin("Admin")
                .setPassword("newPassword")
                .build();
    }

    @Test
    void listObjectTypesDemo() throws InterruptedException {
        ObjectTypeCriteria criteria = ObjectTypes.criteria()
                .orderById().ascending()
                .pageSize(200);
        ObjectTypeList objectTypeList = client.objectTypes().getAll(criteria);
        for (ObjectType objectType : objectTypeList) {
            log.info(objectType.toString());
        }
    }

    @Test
    void updateObjectType() {
        ObjectTypeList objectTypeList = client.objectTypes().getAll();

        Iterable<ObjectType> iterable = objectTypeList::iterator;
        Stream<ObjectType> objectTypeStream = StreamSupport.stream(iterable.spliterator(), false);
        ObjectType mercuryObjectType = objectTypeStream.filter(objectType -> objectType.getId() == 1).findAny().orElseThrow();

        // Добавление нового атрибута.
        Section firstSection = mercuryObjectType.getSections().iterator().next();
        log.info(firstSection.toString());

        Optional<Attribute> counterAttributeOptional = firstSection.getAttributeByName("Counter");
        if (counterAttributeOptional.isPresent()) {
            int incrementedValue = Integer.parseInt(counterAttributeOptional.get().getDefaultValue()) + 1;
            log.info("Counter incrementing... " + incrementedValue);
            counterAttributeOptional.get().setDefaultValue(incrementedValue + "");
        } else {
            log.info("Counter attribute creation...");
            Attribute attribute = client.instantiate(Attribute.class)
                    .setName("Counter")
                    .setType(AttributeType.INTEGER)
                    .setCaption("Тестовый атрибут - счетчик")
                    .setDefaultValue("1");
            firstSection.addAttribute(attribute);
        }

        // Сохранение изменений.
        mercuryObjectType.save();
    }

    @Test
    void creationObjectTypeDemo() {
        ObjectType objectType =
                client.instantiate(ObjectType.class)
                        .setId(100L)
                        .setName("ObjectTypeName")
                        .setCaption("Название тестового типа объекта")
                        .setDevice(false)
                        .setEnabled(true)
                        .addSection(
                                client.instantiate(Section.class)
                                        .setName("SomeSection")
                                        .setCaption("Тестовая секция")
                                        .setComment("Комментарий к тестовой секции")
                                        .addAttribute(
                                                client.instantiate(Attribute.class)
                                                        .setName("SomeAttribute")
                                                        .setCaption("Тестовый атрибут")
                                                        .setType(AttributeType.ENUMERATION)
                                                        .addEnumSetValue(
                                                                client.instantiate(EnumSetValue.class)
                                                                        .setNumber(1)
                                                                        .setCaption("Элемент перечисления 1")
                                                        )
                                                        .addEnumSetValue(
                                                                client.instantiate(EnumSetValue.class)
                                                                        .setNumber(2)
                                                                        .setCaption("Элемент перечисления 2")
                                                        )
                                                        .setDefaultValue("2")
                                                        .setComment("Комментарий к данному атриибуту")
                                        )
                        );

        ObjectType createdObjectType = client.objectTypes().createObjectType(objectType);
        log.info(createdObjectType.toString());
    }

    @Test
    void deletionObjectTypeDemo() {
        ObjectType objectType =
                client.instantiate(ObjectType.class)
                        .setName("ObjectTypeName")
                        .setCaption("Название тестового типа объекта")
                        .setDevice(false)
                        .setEnabled(true);

        ObjectType createdObjectType = client.objectTypes().createObjectType(objectType);

        createdObjectType.delete();
    }

    @Test
    void getAllEnabledDevices() {
        ObjectTypeList objectTypeList = client.objectTypes().getAllEnabledDevices(
                ObjectTypes.criteria()
                        .orderByName().ascending()
                        .pageSize(100)
        );
        for (ObjectType objectType : objectTypeList) {
            log.info(objectType.toString());
        }
    }

    @Test
    void getAllDevices() {
        ObjectTypeList objectTypeList = client.objectTypes().getAllDevices(
                ObjectTypes.criteria()
                        .orderByName().ascending()
                        .pageSize(100)
        );
        for (ObjectType objectType : objectTypeList) {
            log.info(objectType.toString());
        }
    }

    @Test
    void getAllEnabled() {
        ObjectTypeList objectTypeList = client.objectTypes().getAllEnabled(
                ObjectTypes.criteria()
                        .orderByName().ascending()
                        .pageSize(100)
        );
        for (ObjectType objectType : objectTypeList) {
            log.info(objectType.toString());
        }
    }


}
