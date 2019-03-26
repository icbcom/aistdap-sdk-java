package ru.icbcom.aistdapsdkjava.integration.device;

import org.junit.jupiter.api.Test;
import org.mockserver.verify.VerificationTimes;
import ru.icbcom.aistdapsdkjava.api.device.Device;
import ru.icbcom.aistdapsdkjava.api.device.DeviceList;
import ru.icbcom.aistdapsdkjava.api.device.Devices;
import ru.icbcom.aistdapsdkjava.integration.AbstractIntegrationTest;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockserver.matchers.MatchType.STRICT;
import static org.mockserver.matchers.Times.once;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.JsonBody.json;
import static org.mockserver.model.Parameter.param;
import static ru.icbcom.aistdapsdkjava.helper.ResourceHelper.loadResource;
import static ru.icbcom.aistdapsdkjava.helper.ResourceHelper.loadTemplatedResource;

class DeviceActionsIntegrationTest extends AbstractIntegrationTest {

    @Test
    void getAllShouldWorkProperly() {
        mockServer.when(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/devices"),
                once())
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/device/deviceListResponse.json", Map.of("serverPort", mockServer.getLocalPort()))));

        DeviceList deviceList = client.devices().getAll();

        assertThat(deviceList, allOf(
                hasProperty("size", is(20L)),
                hasProperty("totalElements", is(2L)),
                hasProperty("totalPages", is(1L)),
                hasProperty("number", is(0L))
        ));
        Iterator<Device> iterator = deviceList.iterator();
        assertTrue(iterator.hasNext());
        assertThat(iterator.next(), allOf(
                hasProperty("id", is(10033L)),
                hasProperty("objectTypeId", is(22L)),
                hasProperty("name", is("Счетчик э/э Аист A100")),
                hasProperty("attributeValues", allOf(
                        hasEntry("Serial", "000000000000"),
                        hasEntry("AccessLevel", "3"),
                        hasEntry("Password", "000000"),
                        hasEntry("RatesNumber", "4"),
                        hasEntry("Latitude", "0"),
                        hasEntry("Longitude", "0")
                )),
                hasProperty("dataStore", is(notNullValue()))
        ));
        assertTrue(iterator.hasNext());
        assertThat(iterator.next(), allOf(
                hasProperty("id", is(10036L)),
                hasProperty("objectTypeId", is(37L)),
                hasProperty("name", is("Метеостанция IMETEOLABS PWS-200")),
                hasProperty("attributeValues", allOf(
                        hasEntry("Address", "5"),
                        hasEntry("Latitude", "122"),
                        hasEntry("Longitude", "157")
                )),
                hasProperty("dataStore", is(notNullValue()))
        ));
        assertFalse(iterator.hasNext());
        mockServer.verify(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/devices"),
                VerificationTimes.exactly(1));
    }

    @Test
    void getAllWithCriteriaShouldWorkProperly() {
        mockServer.when(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/devices")
                        .withQueryStringParameters(param("size", "20"), param("page", "0"), param("sort", "name,desc")),
                once())
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/device/deviceListResponse.json", Map.of("serverPort", mockServer.getLocalPort()))));

        DeviceList deviceList = client.devices().getAll(Devices.criteria()
                .orderByName().descending()
                .pageSize(20)
                .pageNumber(0));

        assertThat(deviceList, allOf(
                hasProperty("size", is(20L)),
                hasProperty("totalElements", is(2L)),
                hasProperty("totalPages", is(1L)),
                hasProperty("number", is(0L))
        ));
        Iterator<Device> iterator = deviceList.iterator();
        assertTrue(iterator.hasNext());
        assertThat(iterator.next(), allOf(
                hasProperty("id", is(10033L)),
                hasProperty("objectTypeId", is(22L)),
                hasProperty("name", is("Счетчик э/э Аист A100")),
                hasProperty("attributeValues", allOf(
                        hasEntry("Serial", "000000000000"),
                        hasEntry("AccessLevel", "3"),
                        hasEntry("Password", "000000"),
                        hasEntry("RatesNumber", "4"),
                        hasEntry("Latitude", "0"),
                        hasEntry("Longitude", "0")
                )),
                hasProperty("dataStore", is(notNullValue()))
        ));
        assertTrue(iterator.hasNext());
        assertThat(iterator.next(), allOf(
                hasProperty("id", is(10036L)),
                hasProperty("objectTypeId", is(37L)),
                hasProperty("name", is("Метеостанция IMETEOLABS PWS-200")),
                hasProperty("attributeValues", allOf(
                        hasEntry("Address", "5"),
                        hasEntry("Latitude", "122"),
                        hasEntry("Longitude", "157")
                )),
                hasProperty("dataStore", is(notNullValue()))
        ));
        assertFalse(iterator.hasNext());
        mockServer.verify(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/devices")
                        .withQueryStringParameters(param("size", "20"), param("page", "0"), param("sort", "name,desc")),
                VerificationTimes.exactly(1));
    }

    @Test
    void getByIdShouldWorkProperly() {
        mockServer.when(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/devices/1"),
                once())
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/device/deviceWithIdEqualOneResponse.json", Map.of("serverPort", mockServer.getLocalPort()))));

        Optional<Device> deviceOptional = client.devices().getById(1L);

        assertTrue(deviceOptional.isPresent());
        assertThat(deviceOptional.get(), allOf(
                hasProperty("id", is(1L)),
                hasProperty("objectTypeId", is(22L)),
                hasProperty("name", is("Счетчик э/э Аист A100")),
                hasProperty("attributeValues", allOf(
                        hasEntry("Serial", "000000000000"),
                        hasEntry("AccessLevel", "3"),
                        hasEntry("Password", "000000"),
                        hasEntry("RatesNumber", "4"),
                        hasEntry("Latitude", "0"),
                        hasEntry("Longitude", "0")
                )),
                hasProperty("dataStore", is(notNullValue()))
        ));
        mockServer.verify(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/devices/1"),
                VerificationTimes.exactly(1));
    }

    @Test
    void createShouldWorkProperly() {
        mockServer.when(request()
                        .withMethod("POST")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/devices")
                        .withBody(json(loadResource("integration/device/deviceCreateRequest.json"), STRICT)),
                once())
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/device/deviceCreateResponse.json", Map.of("serverPort", mockServer.getLocalPort()))));

        Device device = client.instantiate(Device.class)
                .setObjectTypeId(1L)
                .setName("Название устройства")
                .setAttributeValue("Server", "puma.icbcom.ru")
                .setAttributeValue("Port", "2755")
                .setAttributeValue("AdditionalAttribute", "some attribute value");

        Device createdDevice = client.devices().create(device);
        assertThat(createdDevice, allOf(
                hasProperty("id", is(100L)),
                hasProperty("objectTypeId", is(1L)),
                hasProperty("name", is("Название устройства")),
                hasProperty("attributeValues", allOf(
                        hasEntry("Server", "puma.icbcom.ru"),
                        hasEntry("Port", "2755"),
                        hasEntry("AdditionalAttribute", "some attribute value")
                )),
                hasProperty("dataStore", is(notNullValue()))
        ));
        mockServer.verify(request()
                        .withMethod("POST")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/devices")
                        .withBody(json(loadResource("integration/device/deviceCreateRequest.json"), STRICT)),
                VerificationTimes.exactly(1));
    }

}