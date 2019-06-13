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

package ru.icbcom.aistdapsdkjava.integration.datasource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.verify.VerificationTimes;
import ru.icbcom.aistdapsdkjava.api.datasource.DataSource;
import ru.icbcom.aistdapsdkjava.api.datasourcegroup.DataSourceGroup;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectType;
import ru.icbcom.aistdapsdkjava.integration.AbstractIntegrationTest;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockserver.matchers.MatchType.STRICT;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.JsonBody.json;
import static ru.icbcom.aistdapsdkjava.helper.ResourceHelper.loadTemplatedResource;

class DataSourceIntegrationTest extends AbstractIntegrationTest {

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

        // Запрос источника данных с идентификатором 2 для типа объекта с идентификатором 1.
        mockServer.when(request()
                .withMethod("GET")
                .withHeader("Authorization", "Bearer some-access-token")
                .withHeader("Accept", "application/json, application/problem+json")
                .withPath("/objectTypes/1/dataSources/2"))
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/objectType/dataSourceWithIdEqualTwoResponse.json", Map.of("serverPort", mockServer.getLocalPort()))));
    }

    @Test
    void getObjectTypeShouldWorkProperly() {
        DataSource dataSource = client.objectTypes().getById(1L).orElseThrow()
                .getDataSourceById(2L).orElseThrow();
        ObjectType objectType = dataSource.getObjectType();

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
    void getDataSourceGroupShouldWorkProperly() {
        // Запрос группы источников данных с идентификатором 1 для типа объекта с идентификатором 1.
        mockServer.when(request()
                .withMethod("GET")
                .withHeader("Authorization", "Bearer some-access-token")
                .withHeader("Accept", "application/json, application/problem+json")
                .withPath("/objectTypes/1/dataSourceGroups/1"))
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/dataSource/dataSourceGroupWithIdEqualOneResponse.json", Map.of("serverPort", mockServer.getLocalPort()))));

        DataSource dataSource = client.objectTypes().getById(1L).orElseThrow()
                .getDataSourceById(2L).orElseThrow();

        DataSourceGroup dataSourceGroup = dataSource.getDataSourceGroup();
        assertThat(dataSourceGroup, allOf(
                hasProperty("dataSourceGroupId", is(1L)),
                hasProperty("objectTypeId", is(1L)),
                hasProperty("caption", is("Параметры потребления тепловой энергии"))
        ));
        mockServer.verify(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes/1/dataSourceGroups/1"),
                VerificationTimes.exactly(1));
    }

    @Test
    void saveShouldWorkProperly() {
        // Запрос обновления источника данных.
        mockServer.when(request()
                .withMethod("PUT")
                .withHeader("Authorization", "Bearer some-access-token")
                .withHeader("Accept", "application/json, application/problem+json")
                .withPath("/objectTypes/1/dataSources/2")
                .withBody(json(loadTemplatedResource("integration/datasource/dataSourceUpdateRequest.json", Map.of("serverPort", mockServer.getLocalPort())), STRICT)))
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/datasource/dataSourceUpdateResponse.json", Map.of("serverPort", mockServer.getLocalPort()))));

        DataSource dataSource = client.objectTypes().getById(1L).orElseThrow()
                .getDataSourceById(2L).orElseThrow();
        dataSource.setCaption("Новое название иисточника данных")
                .setMeasureItem("Новая единица измерениия")
                .setDataSourceGroupId(5000L);
        dataSource.save();

        mockServer.verify(request()
                        .withMethod("PUT")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes/1/dataSources/2")
                        .withBody(json(loadTemplatedResource("integration/datasource/dataSourceUpdateRequest.json", Map.of("serverPort", mockServer.getLocalPort())), STRICT)),
                VerificationTimes.exactly(1));
    }

    @Test
    void deleteShouldWorkProperly() {
        // Запрос удаления источника данных.
        mockServer.when(request()
                .withMethod("DELETE")
                .withHeader("Authorization", "Bearer some-access-token")
                .withHeader("Accept", "application/json, application/problem+json")
                .withPath("/objectTypes/1/dataSources/2"))
                .respond(response().withStatusCode(204));

        DataSource dataSource = client.objectTypes().getById(1L).orElseThrow()
                .getDataSourceById(2L).orElseThrow();
        dataSource.delete();

        mockServer.verify(request()
                        .withMethod("DELETE")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes/1/dataSources/2"),
                VerificationTimes.exactly(1));
    }

}