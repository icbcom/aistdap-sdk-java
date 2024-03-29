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

package ru.icbcom.aistdapsdkjava.integration.datasourcegroup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.verify.VerificationTimes;
import ru.icbcom.aistdapsdkjava.api.datasource.DataSource;
import ru.icbcom.aistdapsdkjava.api.datasource.DataSourceList;
import ru.icbcom.aistdapsdkjava.api.datasource.DataSources;
import ru.icbcom.aistdapsdkjava.api.datasourcegroup.DataSourceGroup;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectType;
import ru.icbcom.aistdapsdkjava.integration.AbstractIntegrationTest;

import java.util.Iterator;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockserver.matchers.MatchType.STRICT;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.JsonBody.json;
import static org.mockserver.model.Parameter.param;
import static ru.icbcom.aistdapsdkjava.helper.ResourceHelper.loadTemplatedResource;

class DataSourceGroupIntegrationTest extends AbstractIntegrationTest {

    @BeforeEach
    void setup() {
        // Запрос типа объекта с идентификатором 1.
        mockServer.when(request()
                .withMethod("GET")
                .withHeader("Authorization", "Bearer some-access-token")
                .withHeader("Accept", "application/json, application/problem+json")
                .withPath("/objectTypes/1"))
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/objectType/objectTypeWithIdEqToOneResponse.json", Map.of("serverPort", mockServer.getLocalPort()))));

        // Запрос группы источников данных с идентификатором 2 для типа объекта с идентификатором 1.
        mockServer.when(request()
                .withMethod("GET")
                .withHeader("Authorization", "Bearer some-access-token")
                .withHeader("Accept", "application/json, application/problem+json")
                .withPath("/objectTypes/1/dataSourceGroups/2"))
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/objectType/dataSourceGroupWithIdEqualTwoResponse.json", Map.of("serverPort", mockServer.getLocalPort()))));
    }

    @Test
    void getObjectTypeShouldWorkProperly() {
        DataSourceGroup dataSourceGroup = client.objectTypes().getById(1L).orElseThrow()
                .getDataSourceGroupById(2L).orElseThrow();
        ObjectType objectType = dataSourceGroup.getObjectType();

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
                VerificationTimes.exactly(2));
    }

    @Test
    void getDataSourcesShouldWorkProperly() {
        // Запрос источников данных принадлежащих конкретной группе (группа с идентификатором 2 для типа объекта 1).
        mockServer.when(request()
                .withMethod("GET")
                .withHeader("Authorization", "Bearer some-access-token")
                .withHeader("Accept", "application/json, application/problem+json")
                .withPath("/objectTypes/1/dataSourceGroups/2/dataSources"))
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/datasourcegroup/dataSourcesInGroupResponse.json", Map.of("serverPort", mockServer.getLocalPort()))));

        DataSourceGroup dataSourceGroup = client.objectTypes().getById(1L).orElseThrow()
                .getDataSourceGroupById(2L).orElseThrow();
        DataSourceList dataSourceList = dataSourceGroup.getDataSources();

        assertThat(dataSourceList, allOf(
                hasProperty("size", is(2L)),
                hasProperty("totalElements", is(2L)),
                hasProperty("totalPages", is(1L)),
                hasProperty("number", is(0L))
        ));
        Iterator<DataSource> dataSourceListIterator = dataSourceList.iterator();
        assertTrue(dataSourceListIterator.hasNext());
        assertThat(dataSourceListIterator.next(), allOf(
                hasProperty("dataSourceId", is(10L)),
                hasProperty("objectTypeId", is(1L)),
                hasProperty("caption", is("Электроэнергия, A- Тариф 4")),
                hasProperty("measureItem", is("кВт*ч")),
                hasProperty("dataSourceGroupId", is(2L))
        ));
        assertTrue(dataSourceListIterator.hasNext());
        assertThat(dataSourceListIterator.next(), allOf(
                hasProperty("dataSourceId", is(9L)),
                hasProperty("objectTypeId", is(1L)),
                hasProperty("caption", is("Электроэнергия, A- Тариф 3")),
                hasProperty("measureItem", is("кВт*ч")),
                hasProperty("dataSourceGroupId", is(2L))
        ));
        assertFalse(dataSourceListIterator.hasNext());
        mockServer.verify(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes/1/dataSourceGroups/2/dataSources"),
                VerificationTimes.exactly(1));
    }

    @Test
    void getDataSourcesWithCriteriaShouldWorkProperly() {
        // Запрос источников данных принадлежащих конкретной группе (группа с идентификатором 2 для типа объекта 1).
        mockServer.when(request()
                .withMethod("GET")
                .withHeader("Authorization", "Bearer some-access-token")
                .withHeader("Accept", "application/json, application/problem+json")
                .withPath("/objectTypes/1/dataSourceGroups/2/dataSources")
                .withQueryStringParameters(param("size", "2"), param("page", "0"), param("sort", "caption,desc")))
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/datasourcegroup/dataSourcesInGroupResponse.json", Map.of("serverPort", mockServer.getLocalPort()))));

        DataSourceGroup dataSourceGroup = client.objectTypes().getById(1L).orElseThrow()
                .getDataSourceGroupById(2L).orElseThrow();
        DataSourceList dataSourceList = dataSourceGroup.getDataSources(DataSources.criteria()
                .orderByCaption().descending()
                .pageSize(2)
                .pageNumber(0));

        assertThat(dataSourceList, allOf(
                hasProperty("size", is(2L)),
                hasProperty("totalElements", is(2L)),
                hasProperty("totalPages", is(1L)),
                hasProperty("number", is(0L))
        ));
        Iterator<DataSource> dataSourceListIterator = dataSourceList.iterator();
        assertTrue(dataSourceListIterator.hasNext());
        assertThat(dataSourceListIterator.next(), allOf(
                hasProperty("dataSourceId", is(10L)),
                hasProperty("objectTypeId", is(1L)),
                hasProperty("caption", is("Электроэнергия, A- Тариф 4")),
                hasProperty("measureItem", is("кВт*ч")),
                hasProperty("dataSourceGroupId", is(2L))
        ));
        assertTrue(dataSourceListIterator.hasNext());
        assertThat(dataSourceListIterator.next(), allOf(
                hasProperty("dataSourceId", is(9L)),
                hasProperty("objectTypeId", is(1L)),
                hasProperty("caption", is("Электроэнергия, A- Тариф 3")),
                hasProperty("measureItem", is("кВт*ч")),
                hasProperty("dataSourceGroupId", is(2L))
        ));
        assertFalse(dataSourceListIterator.hasNext());
        mockServer.verify(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes/1/dataSourceGroups/2/dataSources")
                        .withQueryStringParameters(param("size", "2"), param("page", "0"), param("sort", "caption,desc")),
                VerificationTimes.exactly(1));
    }

    @Test
    void saveShouldWorkProperly() {
        // Запрос обновления группы источников данных.
        mockServer.when(request()
                .withMethod("PUT")
                .withHeader("Authorization", "Bearer some-access-token")
                .withHeader("Accept", "application/json, application/problem+json")
                .withPath("/objectTypes/1/dataSourceGroups/2")
                .withBody(json(loadTemplatedResource("integration/datasourcegroup/dataSourceGroupUpdateRequest.json", Map.of("serverPort", mockServer.getLocalPort())), STRICT)))
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/datasourcegroup/dataSourceGroupUpdateResponse.json", Map.of("serverPort", mockServer.getLocalPort()))));

        DataSourceGroup dataSourceGroup = client.objectTypes().getById(1L).orElseThrow()
                .getDataSourceGroupById(2L).orElseThrow();
        dataSourceGroup.setCaption("Новое название группы источнииков данных");
        dataSourceGroup.save();

        mockServer.verify(request()
                        .withMethod("PUT")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes/1/dataSourceGroups/2")
                        .withBody(json(loadTemplatedResource("integration/datasourcegroup/dataSourceGroupUpdateRequest.json", Map.of("serverPort", mockServer.getLocalPort())), STRICT)),
                VerificationTimes.exactly(1));
    }

    @Test
    void deleteShouldWorkProperly() {
        // Запрос удаления группы источников данных.
        mockServer.when(request()
                .withMethod("DELETE")
                .withHeader("Authorization", "Bearer some-access-token")
                .withHeader("Accept", "application/json, application/problem+json")
                .withPath("/objectTypes/1/dataSourceGroups/2"))
                .respond(response().withStatusCode(204));

        DataSourceGroup dataSourceGroup = client.objectTypes().getById(1L).orElseThrow()
                .getDataSourceGroupById(2L).orElseThrow();
        dataSourceGroup.delete();

        mockServer.verify(request()
                        .withMethod("DELETE")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes/1/dataSourceGroups/2"),
                VerificationTimes.exactly(1));
    }

}