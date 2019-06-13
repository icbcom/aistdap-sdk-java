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

package ru.icbcom.aistdapsdkjava.integration.physicalstructure;

import org.junit.jupiter.api.Test;
import org.mockserver.verify.VerificationTimes;
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

class PhysicalStructureObjectActionsIntegrationTest extends AbstractIntegrationTest {

    @Test
    void getAllInRootShouldWorkProperly() {
        mockServer.when(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/physicalStructure"),
                once())
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/physicalstructure/physicalStructureObjectList.json", Map.of("serverPort", mockServer.getLocalPort()))));

        PhysicalStructureObjectList physicalStructureObjectList = client.physicalStructure().getAllInRoot();

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
                        .withPath("/physicalStructure"),
                VerificationTimes.exactly(1));
    }

    @Test
    void getAllInRootWithCriteriaShouldWorkProperly() {
        mockServer.when(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/physicalStructure")
                        .withQueryStringParameters(param("size", "20"), param("page", "0"), param("sort", "name,desc")),
                once())
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/physicalstructure/physicalStructureObjectList.json", Map.of("serverPort", mockServer.getLocalPort()))));

        PhysicalStructureObjectList physicalStructureObjectList = client.physicalStructure()
                .getAllInRoot(
                        PhysicalStructureObjects.criteria()
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
                        .withPath("/physicalStructure")
                        .withQueryStringParameters(param("size", "20"), param("page", "0"), param("sort", "name,desc")),
                VerificationTimes.exactly(1));
    }

    @Test
    void getByIdShouldWorkProperly() {
        mockServer.when(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/physicalStructure/10007"),
                once())
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/physicalstructure/physicalStructureObjectWithIdEqual10007.json", Map.of("serverPort", mockServer.getLocalPort()))));

        Optional<PhysicalStructureObject> physicalStructureObjectOptional = client.physicalStructure().getById(10007L);

        assertTrue(physicalStructureObjectOptional.isPresent());
        assertThat(physicalStructureObjectOptional.get(), allOf(
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

    @Test
    void createInRootShouldWorkProperly() {
        mockServer.when(request()
                        .withMethod("POST")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/physicalStructure")
                        .withBody(json(loadResource("integration/physicalstructure/physicalStructureObjectCreateRequest.json"), STRICT)),
                once())
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/physicalstructure/physicalStructureObjectCreateResponse.json", Map.of("serverPort", mockServer.getLocalPort()))));

        PhysicalStructureObject physicalStructureObject = client.instantiate(PhysicalStructureObject.class)
                .setObjectTypeId(1L)
                .setName("Название объекта")
                .setAttributeValue("Server", "puma.icbcom.ru")
                .setAttributeValue("Port", "2755")
                .setAttributeValue("AdditionalAttribute", "some attribute value");

        PhysicalStructureObject createdPhysicalStructureObject = client.physicalStructure().createInRoot(physicalStructureObject);
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
                        .withPath("/physicalStructure")
                        .withBody(json(loadResource("integration/physicalstructure/physicalStructureObjectCreateRequest.json"), STRICT)),
                VerificationTimes.exactly(1));
    }

}