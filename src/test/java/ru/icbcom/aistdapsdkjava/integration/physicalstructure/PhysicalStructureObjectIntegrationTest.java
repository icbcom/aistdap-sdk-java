package ru.icbcom.aistdapsdkjava.integration.physicalstructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.verify.VerificationTimes;
import ru.icbcom.aistdapsdkjava.api.device.Device;
import ru.icbcom.aistdapsdkjava.api.device.DeviceList;
import ru.icbcom.aistdapsdkjava.api.device.Devices;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectType;
import ru.icbcom.aistdapsdkjava.api.physicalstructure.PhysicalStructureObject;
import ru.icbcom.aistdapsdkjava.api.physicalstructure.PhysicalStructureObjectList;
import ru.icbcom.aistdapsdkjava.api.physicalstructure.PhysicalStructureObjects;
import ru.icbcom.aistdapsdkjava.integration.AbstractIntegrationTest;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockserver.matchers.MatchType.STRICT;
import static org.mockserver.matchers.Times.once;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.JsonBody.json;
import static org.mockserver.model.Parameter.param;
import static ru.icbcom.aistdapsdkjava.helper.ResourceHelper.loadResource;
import static ru.icbcom.aistdapsdkjava.helper.ResourceHelper.loadTemplatedResource;

class PhysicalStructureObjectIntegrationTest extends AbstractIntegrationTest {

    @BeforeEach
    void setup() {
        mockServer.when(request()
                .withMethod("GET")
                .withHeader("Authorization", "Bearer some-access-token")
                .withHeader("Accept", "application/json, application/problem+json")
                .withPath("/physicalStructure/10007"))
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/physicalstructure/physicalStructureObjectWithIdEqual10007.json", Map.of("serverPort", mockServer.getLocalPort()))));
        mockServer.when(request()
                .withMethod("GET")
                .withHeader("Authorization", "Bearer some-access-token")
                .withHeader("Accept", "application/json, application/problem+json")
                .withPath("/physicalStructure/10035"))
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/physicalstructure/physicalStructureObjectWithIdEqual10035.json", Map.of("serverPort", mockServer.getLocalPort()))));
    }

    @Test
    void saveShouldWorkProperly() {
        mockServer.when(request()
                .withMethod("PUT")
                .withHeader("Authorization", "Bearer some-access-token")
                .withHeader("Accept", "application/json, application/problem+json")
                .withPath("/physicalStructure/10007")
                .withBody(json(loadTemplatedResource("integration/physicalstructure/physicalStructureObjectWithIdEqual10007UpdateRequest.json", Map.of("serverPort", mockServer.getLocalPort())), STRICT)))
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/physicalstructure/physicalStructureObjectWithIdEqual10007UpdateResponse.json", Map.of("serverPort", mockServer.getLocalPort()))));

        PhysicalStructureObject physicalStructureObject = client.physicalStructure().getById(10007L).orElseThrow();
        physicalStructureObject.setName("Система АСКУЭ - UPDATED").setAttributeValue("Host", "localhost-updated");
        physicalStructureObject.save();

        mockServer.verify(request()
                        .withMethod("PUT")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/physicalStructure/10007")
                        .withBody(json(loadTemplatedResource("integration/physicalstructure/physicalStructureObjectWithIdEqual10007UpdateRequest.json", Map.of("serverPort", mockServer.getLocalPort())), STRICT)),
                VerificationTimes.exactly(1));
    }

    @Test
    void deleteShouldWorkProperly() {
        mockServer.when(request()
                .withMethod("DELETE")
                .withHeader("Authorization", "Bearer some-access-token")
                .withHeader("Accept", "application/json, application/problem+json")
                .withPath("/physicalStructure/10007"))
                .respond(response().withStatusCode(204));

        PhysicalStructureObject physicalStructureObject = client.physicalStructure().getById(10007L).orElseThrow();
        physicalStructureObject.delete();

        mockServer.verify(request()
                        .withMethod("DELETE")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/physicalStructure/10007"),
                VerificationTimes.exactly(1));
    }

    @Test
    void getObjectTypeShouldWorkProperly() {
        mockServer.when(request()
                .withMethod("GET")
                .withHeader("Authorization", "Bearer some-access-token")
                .withHeader("Accept", "application/json, application/problem+json")
                .withPath("/objectTypes/41"))
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/physicalStructure/objectTypeWithIdEqual41.json", Map.of("serverPort", mockServer.getLocalPort()))));

        PhysicalStructureObject physicalStructureObject = client.physicalStructure().getById(10007L).orElseThrow();
        ObjectType objectType = physicalStructureObject.getObjectType();

        assertThat(objectType, allOf(
                hasProperty("id", is(41L)),
                hasProperty("name", is("AskueSystem")),
                hasProperty("caption", is("Система АСКУЭ")),
                hasProperty("device", is(false)),
                hasProperty("enabled", is(true))
        ));
        mockServer.verify(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes/41"),
                VerificationTimes.exactly(1));
    }

    @Test
    void getDescendantsShouldWorkProperly() {
        mockServer.when(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/physicalStructure/10007/descendants"),
                once())
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/physicalstructure/physicalStructureObjectList.json", Map.of("serverPort", mockServer.getLocalPort()))));

        PhysicalStructureObject physicalStructureObject = client.physicalStructure().getById(10007L).orElseThrow();
        PhysicalStructureObjectList physicalStructureObjectList = physicalStructureObject.getDescendants();

        assertThat(physicalStructureObjectList, allOf(
                hasProperty("size", is(20L)),
                hasProperty("totalElements", is(3L)),
                hasProperty("totalPages", is(1L)),
                hasProperty("number", is(0L))
        ));
        Iterator<PhysicalStructureObject> iterator = physicalStructureObjectList.iterator();
        assertTrue(iterator.hasNext());
        assertThat(iterator.next(), allOf(
                hasProperty("id", is(10007L)),
                hasProperty("objectTypeId", is(41L)),
                hasProperty("name", is("Система АСКУЭ")),
                hasProperty("attributeValues", allOf(
                        hasEntry("Host", "localhost"),
                        hasEntry("Port", "1433"),
                        hasEntry("Database", "aeinfov2_mts"),
                        hasEntry("Login", "sa"),
                        hasEntry("Password", "a2lsa2Fib3Q="),
                        hasEntry("MeasurementsPollRate", "10"),
                        hasEntry("EnergyProfilesPollRate", "3600")
                )),
                hasProperty("descendantsCount", is(1L)),
                hasProperty("devicesCount", is(0L)),
                hasProperty("dataStore", is(notNullValue()))
        ));
        assertTrue(iterator.hasNext());
        assertThat(iterator.next(), allOf(
                hasProperty("id", is(10032L)),
                hasProperty("objectTypeId", is(34L)),
                hasProperty("name", is("UDP транспорт")),
                hasProperty("attributeValues", allOf(
                        hasEntry("Period", "1000"),
                        hasEntry("DefaultReadTimeout", "1000"),
                        hasEntry("RepeatEnabled", "False"),
                        hasEntry("DelayBetweenPolls", "1000"),
                        hasEntry("IpAddress", "1000"),
                        hasEntry("Port", "31337")
                )),
                hasProperty("descendantsCount", is(0L)),
                hasProperty("devicesCount", is(1L)),
                hasProperty("dataStore", is(notNullValue()))
        ));
        assertTrue(iterator.hasNext());
        assertThat(iterator.next(), allOf(
                hasProperty("id", is(10027L)),
                hasProperty("objectTypeId", is(41L)),
                hasProperty("name", is("New ASKUE system")),
                hasProperty("attributeValues", allOf(
                        hasEntry("Host", "localhost"),
                        hasEntry("Port", "1433"),
                        hasEntry("Database", "aeinfov2_mts"),
                        hasEntry("Login", "sa"),
                        hasEntry("Password", "pswd"),
                        hasEntry("MeasurementsPollRate", "10"),
                        hasEntry("EnergyProfilesPollRate", "3600")
                )),
                hasProperty("descendantsCount", is(0L)),
                hasProperty("devicesCount", is(0L)),
                hasProperty("dataStore", is(notNullValue()))
        ));
        assertFalse(iterator.hasNext());
        mockServer.verify(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/physicalStructure/10007/descendants"),
                VerificationTimes.exactly(1));
    }

    @Test
    void getDescendantsWithCriteriaShouldWorkProperly() {
        mockServer.when(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/physicalStructure/10007/descendants")
                        .withQueryStringParameters(param("size", "20"), param("page", "0"), param("sort", "name,desc")),
                once())
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/physicalstructure/physicalStructureObjectList.json", Map.of("serverPort", mockServer.getLocalPort()))));

        PhysicalStructureObject physicalStructureObject = client.physicalStructure().getById(10007L).orElseThrow();
        PhysicalStructureObjectList physicalStructureObjectList = physicalStructureObject
                .getDescendants(PhysicalStructureObjects.criteria()
                        .pageSize(20)
                        .pageNumber(0)
                        .orderByName().descending());

        assertThat(physicalStructureObjectList, allOf(
                hasProperty("size", is(20L)),
                hasProperty("totalElements", is(3L)),
                hasProperty("totalPages", is(1L)),
                hasProperty("number", is(0L))
        ));
        Iterator<PhysicalStructureObject> iterator = physicalStructureObjectList.iterator();
        assertTrue(iterator.hasNext());
        assertThat(iterator.next(), allOf(
                hasProperty("id", is(10007L)),
                hasProperty("objectTypeId", is(41L)),
                hasProperty("name", is("Система АСКУЭ")),
                hasProperty("attributeValues", allOf(
                        hasEntry("Host", "localhost"),
                        hasEntry("Port", "1433"),
                        hasEntry("Database", "aeinfov2_mts"),
                        hasEntry("Login", "sa"),
                        hasEntry("Password", "a2lsa2Fib3Q="),
                        hasEntry("MeasurementsPollRate", "10"),
                        hasEntry("EnergyProfilesPollRate", "3600")
                )),
                hasProperty("descendantsCount", is(1L)),
                hasProperty("devicesCount", is(0L)),
                hasProperty("dataStore", is(notNullValue()))
        ));
        assertTrue(iterator.hasNext());
        assertThat(iterator.next(), allOf(
                hasProperty("id", is(10032L)),
                hasProperty("objectTypeId", is(34L)),
                hasProperty("name", is("UDP транспорт")),
                hasProperty("attributeValues", allOf(
                        hasEntry("Period", "1000"),
                        hasEntry("DefaultReadTimeout", "1000"),
                        hasEntry("RepeatEnabled", "False"),
                        hasEntry("DelayBetweenPolls", "1000"),
                        hasEntry("IpAddress", "1000"),
                        hasEntry("Port", "31337")
                )),
                hasProperty("descendantsCount", is(0L)),
                hasProperty("devicesCount", is(1L)),
                hasProperty("dataStore", is(notNullValue()))
        ));
        assertTrue(iterator.hasNext());
        assertThat(iterator.next(), allOf(
                hasProperty("id", is(10027L)),
                hasProperty("objectTypeId", is(41L)),
                hasProperty("name", is("New ASKUE system")),
                hasProperty("attributeValues", allOf(
                        hasEntry("Host", "localhost"),
                        hasEntry("Port", "1433"),
                        hasEntry("Database", "aeinfov2_mts"),
                        hasEntry("Login", "sa"),
                        hasEntry("Password", "pswd"),
                        hasEntry("MeasurementsPollRate", "10"),
                        hasEntry("EnergyProfilesPollRate", "3600")
                )),
                hasProperty("descendantsCount", is(0L)),
                hasProperty("devicesCount", is(0L)),
                hasProperty("dataStore", is(notNullValue()))
        ));
        assertFalse(iterator.hasNext());
        mockServer.verify(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/physicalStructure/10007/descendants")
                        .withQueryStringParameters(param("size", "20"), param("page", "0"), param("sort", "name,desc")),
                VerificationTimes.exactly(1));
    }

    @Test
    void getAttachedDevicesShouldWorkProperly() {
        mockServer.when(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/physicalStructure/10007/attachedDevices"),
                once())
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/device/deviceListResponse.json", Map.of("serverPort", mockServer.getLocalPort()))));

        PhysicalStructureObject physicalStructureObject = client.physicalStructure().getById(10007L).orElseThrow();
        DeviceList attachedDevices = physicalStructureObject.getAttachedDevices();

        assertThat(attachedDevices, allOf(
                hasProperty("size", is(20L)),
                hasProperty("totalElements", is(2L)),
                hasProperty("totalPages", is(1L)),
                hasProperty("number", is(0L))
        ));
        Iterator<Device> iterator = attachedDevices.iterator();
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
                        .withPath("/physicalStructure/10007/attachedDevices"),
                VerificationTimes.exactly(1));
    }

    @Test
    void getAttachedDevicesWithCriteriaShouldWorkProperly() {
        mockServer.when(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/physicalStructure/10007/attachedDevices")
                        .withQueryStringParameters(param("size", "20"), param("page", "0"), param("sort", "name,desc")),
                once())
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/device/deviceListResponse.json", Map.of("serverPort", mockServer.getLocalPort()))));

        PhysicalStructureObject physicalStructureObject = client.physicalStructure().getById(10007L).orElseThrow();
        DeviceList attachedDevices = physicalStructureObject
                .getAttachedDevices(Devices.criteria()
                        .orderByName().descending()
                        .pageSize(20)
                        .pageNumber(0));

        assertThat(attachedDevices, allOf(
                hasProperty("size", is(20L)),
                hasProperty("totalElements", is(2L)),
                hasProperty("totalPages", is(1L)),
                hasProperty("number", is(0L))
        ));
        Iterator<Device> iterator = attachedDevices.iterator();
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
                        .withPath("/physicalStructure/10007/attachedDevices")
                        .withQueryStringParameters(param("size", "20"), param("page", "0"), param("sort", "name,desc")),
                VerificationTimes.exactly(1));
    }

    @Test
    void createDescendantShouldWorkProperly() {
        mockServer.when(request()
                        .withMethod("POST")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/physicalStructure/10007/descendants")
                        .withBody(json(loadResource("integration/physicalstructure/physicalStructureObjectCreateRequest.json"), STRICT)),
                once())
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/physicalstructure/physicalStructureObjectCreateResponse.json", Map.of("serverPort", mockServer.getLocalPort()))));

        PhysicalStructureObject parentPhysicalStructureObject = client.physicalStructure().getById(10007L).orElseThrow();
        PhysicalStructureObject physicalStructureObjectToCreate = client.instantiate(PhysicalStructureObject.class)
                .setObjectTypeId(1L)
                .setName("Название объекта")
                .setAttributeValue("Server", "puma.icbcom.ru")
                .setAttributeValue("Port", "2755")
                .setAttributeValue("AdditionalAttribute", "some attribute value");
        PhysicalStructureObject createdPhysicalStructureObject = parentPhysicalStructureObject.createDescendant(physicalStructureObjectToCreate);

        assertThat(createdPhysicalStructureObject, allOf(
                hasProperty("id", is(123L)),
                hasProperty("objectTypeId", is(1L)),
                hasProperty("name", is("Название объекта")),
                hasProperty("attributeValues", allOf(
                        hasEntry("Server", "puma.icbcom.ru"),
                        hasEntry("Port", "2755"),
                        hasEntry("AdditionalAttribute", "some attribute value")
                )),
                hasProperty("descendantsCount", is(0L)),
                hasProperty("devicesCount", is(0L)),
                hasProperty("dataStore", is(notNullValue()))
        ));
        mockServer.verify(request()
                        .withMethod("POST")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/physicalStructure/10007/descendants")
                        .withBody(json(loadResource("integration/physicalstructure/physicalStructureObjectCreateRequest.json"), STRICT)),
                VerificationTimes.exactly(1));
    }

    @Test
    void getParentShouldWorkProperly() {
        PhysicalStructureObject physicalStructureObject = client.physicalStructure().getById(10035L).orElseThrow();
        Optional<PhysicalStructureObject> parentOptional = physicalStructureObject.getParent();

        assertTrue(parentOptional.isPresent());
        assertThat(parentOptional.get(), allOf(
                hasProperty("id", is(10007L)),
                hasProperty("objectTypeId", is(41L)),
                hasProperty("name", is("Система АСКУЭ")),
                hasProperty("attributeValues", allOf(
                        hasEntry("Host", "localhost"),
                        hasEntry("Port", "1433"),
                        hasEntry("Database", "aeinfov2_mts"),
                        hasEntry("Login", "sa"),
                        hasEntry("Password", "a2lsa2Fib3Q="),
                        hasEntry("MeasurementsPollRate", "10"),
                        hasEntry("EnergyProfilesPollRate", "3600")
                )),
                hasProperty("descendantsCount", is(1L)),
                hasProperty("devicesCount", is(0L)),
                hasProperty("dataStore", is(notNullValue()))
        ));
        mockServer.verify(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/physicalStructure/10007"),
                VerificationTimes.exactly(1));

    }
}