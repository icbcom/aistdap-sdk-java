package ru.icbcom.aistdapsdkjava.integration.objecttype;

import org.junit.jupiter.api.Test;
import org.mockserver.matchers.MatchType;
import org.mockserver.verify.VerificationTimes;
import ru.icbcom.aistdapsdkjava.api.objecttype.*;
import ru.icbcom.aistdapsdkjava.integration.AbstractIntegrationTest;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockserver.matchers.Times.once;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.JsonBody.json;
import static org.mockserver.model.Parameter.param;
import static ru.icbcom.aistdapsdkjava.helper.ResourceHelper.loadResource;
import static ru.icbcom.aistdapsdkjava.helper.ResourceHelper.loadTemplatedResource;

class ObjectTypeActionsIntegrationTest extends AbstractIntegrationTest {

    @Test
    void getAllShouldWorkProperly() {
        // Запрос получение типов объектов постранично.
        mockServer.when(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes"),
                once())
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/objectType/objectTypesFirstPageResponse.json", Map.of("serverPort", mockServer.getLocalPort()))));
        mockServer.when(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes")
                        .withQueryStringParameters(param("page", "1"), param("size", "2")),
                once())
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/objectType/objectTypesSecondPageResponse.json", Map.of("serverPort", mockServer.getLocalPort()))));

        ObjectTypeList objectTypeList = client.objectTypes().getAll();

        assertThat(objectTypeList, allOf(
                hasProperty("size", is(2L)),
                hasProperty("totalElements", is(4L)),
                hasProperty("totalPages", is(2L)),
                hasProperty("number", is(0L))
        ));
        Iterator<ObjectType> objectTypeListIterator = objectTypeList.iterator();

        assertTrue(objectTypeListIterator.hasNext());
        assertThat(objectTypeListIterator.next(), allOf(
                hasProperty("id", is(1L)),
                hasProperty("name", is("Mercury230/233")),
                hasProperty("caption", is("Счетчик э/э Меркурий 230/233")),
                hasProperty("device", is(true)),
                hasProperty("enabled", is(true))
        ));
        assertTrue(objectTypeListIterator.hasNext());
        assertThat(objectTypeListIterator.next(), allOf(
                hasProperty("id", is(3L)),
                hasProperty("name", is("Puma")),
                hasProperty("caption", is("УСПД \"Пума\"")),
                hasProperty("device", is(false)),
                hasProperty("enabled", is(true))
        ));
        assertTrue(objectTypeListIterator.hasNext());
        assertThat(objectTypeListIterator.next(), allOf(
                hasProperty("id", is(4L)),
                hasProperty("name", is("Puma-Can")),
                hasProperty("caption", is("CAN")),
                hasProperty("device", is(false)),
                hasProperty("enabled", is(true))
        ));
        assertTrue(objectTypeListIterator.hasNext());
        assertThat(objectTypeListIterator.next(), allOf(
                hasProperty("id", is(5L)),
                hasProperty("name", is("Puma-485")),
                hasProperty("caption", is("RS485")),
                hasProperty("device", is(false)),
                hasProperty("enabled", is(true))
        ));
        assertFalse(objectTypeListIterator.hasNext());

        mockServer.verify(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes"),
                VerificationTimes.exactly(2));
        mockServer.verify(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes")
                        .withQueryStringParameters(param("page", "1"), param("size", "2")),
                VerificationTimes.exactly(1));
    }

    @Test
    void getAllWithCriteriaShouldWorkProperly() {
        // Запрос получение типов объектов c критериями.
        mockServer.when(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes")
                        .withQueryStringParameters(param("size", "2"), param("page", "0"), param("sort", "caption,desc")),
                once())
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/objectType/objectTypesWithCriteriaResponse.json", Map.of("serverPort", mockServer.getLocalPort()))));

        ObjectTypeList objectTypeList = client.objectTypes()
                .getAll(ObjectTypes.criteria()
                        .orderByCaption().descending()
                        .pageSize(2)
                        .pageNumber(0));

        assertThat(objectTypeList, allOf(
                hasProperty("size", is(2L)),
                hasProperty("totalElements", is(2L)),
                hasProperty("totalPages", is(1L)),
                hasProperty("number", is(0L))
        ));
        Iterator<ObjectType> objectTypeListIterator = objectTypeList.iterator();
        assertTrue(objectTypeListIterator.hasNext());
        assertThat(objectTypeListIterator.next(), allOf(
                hasProperty("id", is(42L)),
                hasProperty("name", is("AskueDevice")),
                hasProperty("caption", is("Устройство в АСКУЭ")),
                hasProperty("device", is(false)),
                hasProperty("enabled", is(true))
        ));
        assertTrue(objectTypeListIterator.hasNext());
        assertThat(objectTypeListIterator.next(), allOf(
                hasProperty("id", is(40L)),
                hasProperty("name", is("OceanConnectDevice")),
                hasProperty("caption", is("Устройство в OceanConnect")),
                hasProperty("device", is(false)),
                hasProperty("enabled", is(true))
        ));
        assertFalse(objectTypeListIterator.hasNext());
        mockServer.verify(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes")
                        .withQueryStringParameters(param("size", "2"), param("page", "0"), param("sort", "caption,desc")),
                VerificationTimes.exactly(1));
    }

    @Test
    void createObjectTypeShouldWorkProperly() {
        // Запрос создания нового типа объекта.
        mockServer.when(request()
                        .withMethod("POST")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes")
                        .withBody(json(loadResource("integration/objectType/objectTypeCreationRequest.json"), MatchType.STRICT)),
                once())
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/objectType/objectTypeCreationResponse.json", Map.of("serverPort", mockServer.getLocalPort()))));

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
        assertEquals(objectType, createdObjectType);
        mockServer.verify(request()
                        .withMethod("POST")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes")
                        .withBody(json(loadResource("integration/objectType/objectTypeCreationRequest.json"), MatchType.STRICT)),
                VerificationTimes.exactly(1));
    }

    @Test
    void getAllEnabledDevicesShouldWorkProperly() {
        // Запрос первой страницы размером 1 для типов объектов. Данный запрос используется для получения ссылки на ресурс 'search'.
        mockServer.when(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes")
                        .withQueryStringParameters(param("size", "1")),
                once())
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/objectType/objectTypesWithCriteriaResponse.json", Map.of("serverPort", mockServer.getLocalPort()))));
        // Запрос 'search' ресурса для типов объектов.
        mockServer.when(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes/search"),
                once())
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/objectType/objectTypesSearchResponse.json", Map.of("serverPort", mockServer.getLocalPort()))));
        // Запрос findAllEnabledDevices.
        mockServer.when(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes/search/findAllEnabledDevices"),
                once())
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/objectType/objectTypesSearchFindAllEnabledDevicesResponse.json", Map.of("serverPort", mockServer.getLocalPort()))));

        ObjectTypeList objectTypeList = client.objectTypes().getAllEnabledDevices();

        assertThat(objectTypeList, allOf(
                hasProperty("size", is(2L)),
                hasProperty("totalElements", is(2L)),
                hasProperty("totalPages", is(1L)),
                hasProperty("number", is(0L))
        ));
        Iterator<ObjectType> objectTypeListIterator = objectTypeList.iterator();

        assertTrue(objectTypeListIterator.hasNext());
        assertThat(objectTypeListIterator.next(), allOf(
                hasProperty("id", is(1L)),
                hasProperty("name", is("Mercury230/233")),
                hasProperty("caption", is("Счетчик э/э Меркурий 230/233")),
                hasProperty("device", is(true)),
                hasProperty("enabled", is(true))
        ));
        assertTrue(objectTypeListIterator.hasNext());
        assertThat(objectTypeListIterator.next(), allOf(
                hasProperty("id", is(13L)),
                hasProperty("name", is("MeteoLufft-WS600-UMB")),
                hasProperty("caption", is("Метеостанция Lufft WS600-UMB")),
                hasProperty("device", is(true)),
                hasProperty("enabled", is(true))
        ));
        assertFalse(objectTypeListIterator.hasNext());
        mockServer.verify(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes/search/findAllEnabledDevices"),
                VerificationTimes.exactly(1));
    }

    @Test
    void getAllEnabledDevicesWithCriteriaShouldWorkProperly() {
        // Запрос первой страницы размером 1 для типов объектов. Данный запрос используется для получения ссылки на ресурс 'search'.
        mockServer.when(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes")
                        .withQueryStringParameters(param("size", "1")),
                once())
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/objectType/objectTypesWithCriteriaResponse.json", Map.of("serverPort", mockServer.getLocalPort()))));
        // Запрос 'search' ресурса для типов объектов.
        mockServer.when(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes/search"),
                once())
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/objectType/objectTypesSearchResponse.json", Map.of("serverPort", mockServer.getLocalPort()))));
        // Запрос findAllEnabledDevices.
        mockServer.when(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes/search/findAllEnabledDevices")
                        .withQueryStringParameters(param("size", "2"), param("page", "0"), param("sort", "caption,desc")),
                once())
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/objectType/objectTypesSearchFindAllEnabledDevicesResponse.json", Map.of("serverPort", mockServer.getLocalPort()))));

        ObjectTypeList objectTypeList = client.objectTypes()
                .getAllEnabledDevices(ObjectTypes.criteria()
                        .orderByCaption().descending()
                        .pageSize(2)
                        .pageNumber(0));

        assertThat(objectTypeList, allOf(
                hasProperty("size", is(2L)),
                hasProperty("totalElements", is(2L)),
                hasProperty("totalPages", is(1L)),
                hasProperty("number", is(0L))
        ));
        Iterator<ObjectType> objectTypeListIterator = objectTypeList.iterator();
        assertTrue(objectTypeListIterator.hasNext());
        assertThat(objectTypeListIterator.next(), allOf(
                hasProperty("id", is(1L)),
                hasProperty("name", is("Mercury230/233")),
                hasProperty("caption", is("Счетчик э/э Меркурий 230/233")),
                hasProperty("device", is(true)),
                hasProperty("enabled", is(true))
        ));
        assertTrue(objectTypeListIterator.hasNext());
        assertThat(objectTypeListIterator.next(), allOf(
                hasProperty("id", is(13L)),
                hasProperty("name", is("MeteoLufft-WS600-UMB")),
                hasProperty("caption", is("Метеостанция Lufft WS600-UMB")),
                hasProperty("device", is(true)),
                hasProperty("enabled", is(true))
        ));
        assertFalse(objectTypeListIterator.hasNext());
        mockServer.verify(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes/search/findAllEnabledDevices")
                        .withQueryStringParameters(param("size", "2"), param("page", "0"), param("sort", "caption,desc")),
                VerificationTimes.exactly(1));
    }

    @Test
    void getAllDevicesShouldWorkProperly() {
        // Запрос первой страницы размером 1 для типов объектов. Данный запрос используется для получения ссылки на ресурс 'search'.
        mockServer.when(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes")
                        .withQueryStringParameters(param("size", "1")),
                once())
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/objectType/objectTypesWithCriteriaResponse.json", Map.of("serverPort", mockServer.getLocalPort()))));
        // Запрос 'search' ресурса для типов объектов.
        mockServer.when(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes/search"),
                once())
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/objectType/objectTypesSearchResponse.json", Map.of("serverPort", mockServer.getLocalPort()))));
        // Запрос findAllEnabledDevices.
        mockServer.when(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes/search/findAllDevices"),
                once())
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/objectType/objectTypesSearchFindAllDevices.json", Map.of("serverPort", mockServer.getLocalPort()))));

        ObjectTypeList objectTypeList = client.objectTypes().getAllDevices();

        assertThat(objectTypeList, allOf(
                hasProperty("size", is(2L)),
                hasProperty("totalElements", is(2L)),
                hasProperty("totalPages", is(1L)),
                hasProperty("number", is(0L))
        ));
        Iterator<ObjectType> objectTypeListIterator = objectTypeList.iterator();
        assertTrue(objectTypeListIterator.hasNext());
        assertThat(objectTypeListIterator.next(), allOf(
                hasProperty("id", is(27L)),
                hasProperty("name", is("Sa94HeatMeter")),
                hasProperty("caption", is("Теплосчетчик SA-94")),
                hasProperty("device", is(true)),
                hasProperty("enabled", is(false))
        ));
        assertTrue(objectTypeListIterator.hasNext());
        assertThat(objectTypeListIterator.next(), allOf(
                hasProperty("id", is(1L)),
                hasProperty("name", is("Mercury230/233")),
                hasProperty("caption", is("Счетчик э/э Меркурий 230/233")),
                hasProperty("device", is(true)),
                hasProperty("enabled", is(true))
        ));
        assertFalse(objectTypeListIterator.hasNext());
        mockServer.verify(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes/search/findAllDevices"),
                VerificationTimes.exactly(1));
    }

    @Test
    void getAllDevicesWithCriteriaShouldWorkProperly() {
        // Запрос первой страницы размером 1 для типов объектов. Данный запрос используется для получения ссылки на ресурс 'search'.
        mockServer.when(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes")
                        .withQueryStringParameters(param("size", "1")),
                once())
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/objectType/objectTypesWithCriteriaResponse.json", Map.of("serverPort", mockServer.getLocalPort()))));
        // Запрос 'search' ресурса для типов объектов.
        mockServer.when(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes/search"),
                once())
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/objectType/objectTypesSearchResponse.json", Map.of("serverPort", mockServer.getLocalPort()))));
        // Запрос findAllEnabledDevices.
        mockServer.when(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes/search/findAllDevices")
                        .withQueryStringParameters(param("size", "2"), param("page", "0"), param("sort", "caption,desc")),
                once())
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/objectType/objectTypesSearchFindAllDevices.json", Map.of("serverPort", mockServer.getLocalPort()))));

        ObjectTypeList objectTypeList = client.objectTypes()
                .getAllDevices(ObjectTypes.criteria()
                        .orderByCaption().descending()
                        .pageSize(2)
                        .pageNumber(0));

        assertThat(objectTypeList, allOf(
                hasProperty("size", is(2L)),
                hasProperty("totalElements", is(2L)),
                hasProperty("totalPages", is(1L)),
                hasProperty("number", is(0L))
        ));
        Iterator<ObjectType> objectTypeListIterator = objectTypeList.iterator();
        assertTrue(objectTypeListIterator.hasNext());
        assertThat(objectTypeListIterator.next(), allOf(
                hasProperty("id", is(27L)),
                hasProperty("name", is("Sa94HeatMeter")),
                hasProperty("caption", is("Теплосчетчик SA-94")),
                hasProperty("device", is(true)),
                hasProperty("enabled", is(false))
        ));
        assertTrue(objectTypeListIterator.hasNext());
        assertThat(objectTypeListIterator.next(), allOf(
                hasProperty("id", is(1L)),
                hasProperty("name", is("Mercury230/233")),
                hasProperty("caption", is("Счетчик э/э Меркурий 230/233")),
                hasProperty("device", is(true)),
                hasProperty("enabled", is(true))
        ));
        assertFalse(objectTypeListIterator.hasNext());
        mockServer.verify(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes/search/findAllDevices")
                        .withQueryStringParameters(param("size", "2"), param("page", "0"), param("sort", "caption,desc")),
                VerificationTimes.exactly(1));
    }

    @Test
    void getAllEnabledShouldWorkProperly() {
        // Запрос первой страницы размером 1 для типов объектов. Данный запрос используется для получения ссылки на ресурс 'search'.
        mockServer.when(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes")
                        .withQueryStringParameters(param("size", "1")),
                once())
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/objectType/objectTypesWithCriteriaResponse.json", Map.of("serverPort", mockServer.getLocalPort()))));
        // Запрос 'search' ресурса для типов объектов.
        mockServer.when(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes/search"),
                once())
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/objectType/objectTypesSearchResponse.json", Map.of("serverPort", mockServer.getLocalPort()))));
        // Запрос findAllEnabledDevices.
        mockServer.when(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes/search/findAllEnabled"),
                once())
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/objectType/objectTypesSearchFindAllEnabled.json", Map.of("serverPort", mockServer.getLocalPort()))));

        ObjectTypeList objectTypeList = client.objectTypes().getAllEnabled();

        assertThat(objectTypeList, allOf(
                hasProperty("size", is(2L)),
                hasProperty("totalElements", is(2L)),
                hasProperty("totalPages", is(1L)),
                hasProperty("number", is(0L))
        ));
        Iterator<ObjectType> objectTypeListIterator = objectTypeList.iterator();
        assertTrue(objectTypeListIterator.hasNext());
        assertThat(objectTypeListIterator.next(), allOf(
                hasProperty("id", is(42L)),
                hasProperty("name", is("AskueDevice")),
                hasProperty("caption", is("Устройство в АСКУЭ")),
                hasProperty("device", is(false)),
                hasProperty("enabled", is(true))
        ));
        assertTrue(objectTypeListIterator.hasNext());
        assertThat(objectTypeListIterator.next(), allOf(
                hasProperty("id", is(40L)),
                hasProperty("name", is("OceanConnectDevice")),
                hasProperty("caption", is("Устройство в OceanConnect")),
                hasProperty("device", is(false)),
                hasProperty("enabled", is(true))
        ));
        assertFalse(objectTypeListIterator.hasNext());
        mockServer.verify(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes/search/findAllEnabled"),
                VerificationTimes.exactly(1));
    }

    @Test
    void getAllEnabledWithCriteriaShouldWorkProperly() {
        // Запрос первой страницы размером 1 для типов объектов. Данный запрос используется для получения ссылки на ресурс 'search'.
        mockServer.when(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes")
                        .withQueryStringParameters(param("size", "1")),
                once())
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/objectType/objectTypesWithCriteriaResponse.json", Map.of("serverPort", mockServer.getLocalPort()))));
        // Запрос 'search' ресурса для типов объектов.
        mockServer.when(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes/search"),
                once())
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/objectType/objectTypesSearchResponse.json", Map.of("serverPort", mockServer.getLocalPort()))));
        // Запрос findAllEnabledDevices.
        mockServer.when(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes/search/findAllEnabled")
                        .withQueryStringParameters(param("size", "2"), param("page", "0"), param("sort", "caption,desc")),
                once())
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/objectType/objectTypesSearchFindAllEnabled.json", Map.of("serverPort", mockServer.getLocalPort()))));

        ObjectTypeList objectTypeList = client.objectTypes()
                .getAllEnabled(ObjectTypes.criteria()
                        .orderByCaption().descending()
                        .pageSize(2)
                        .pageNumber(0));

        assertThat(objectTypeList, allOf(
                hasProperty("size", is(2L)),
                hasProperty("totalElements", is(2L)),
                hasProperty("totalPages", is(1L)),
                hasProperty("number", is(0L))
        ));
        Iterator<ObjectType> objectTypeListIterator = objectTypeList.iterator();
        assertTrue(objectTypeListIterator.hasNext());
        assertThat(objectTypeListIterator.next(), allOf(
                hasProperty("id", is(42L)),
                hasProperty("name", is("AskueDevice")),
                hasProperty("caption", is("Устройство в АСКУЭ")),
                hasProperty("device", is(false)),
                hasProperty("enabled", is(true))
        ));
        assertTrue(objectTypeListIterator.hasNext());
        assertThat(objectTypeListIterator.next(), allOf(
                hasProperty("id", is(40L)),
                hasProperty("name", is("OceanConnectDevice")),
                hasProperty("caption", is("Устройство в OceanConnect")),
                hasProperty("device", is(false)),
                hasProperty("enabled", is(true))
        ));
        assertFalse(objectTypeListIterator.hasNext());
        mockServer.verify(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes/search/findAllEnabled")
                        .withQueryStringParameters(param("size", "2"), param("page", "0"), param("sort", "caption,desc")),
                VerificationTimes.exactly(1));
    }

    @Test
    void getByIdShouldWorkProperly() {
        // Запрос типа объекта с идентификатором 1.
        mockServer.when(request()
                .withMethod("GET")
                .withHeader("Authorization", "Bearer some-access-token")
                .withHeader("Accept", "application/json, application/problem+json")
                .withPath("/objectTypes/1"))
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/objectType/objectTypeWithIdEqToOneResponse.json", Map.of("serverPort", mockServer.getLocalPort()))));

        Optional<ObjectType> objectTypeOptional = client.objectTypes().getById(1L);
        assertTrue(objectTypeOptional.isPresent());
        ObjectType objectType = objectTypeOptional.get();
        assertThat(objectType, allOf(
                hasProperty("id", is(1L)),
                hasProperty("name", is("Mercury230/233")),
                hasProperty("caption", is("Счетчик э/э Меркурий 230/233")),
                hasProperty("device", is(true)),
                hasProperty("enabled", is(true))
        ));
        mockServer.verify(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes/1"),
                VerificationTimes.exactly(1));
    }

}