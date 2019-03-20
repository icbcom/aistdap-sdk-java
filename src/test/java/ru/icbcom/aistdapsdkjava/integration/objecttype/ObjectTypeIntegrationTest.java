package ru.icbcom.aistdapsdkjava.integration.objecttype;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.matchers.MatchType;
import org.mockserver.verify.VerificationTimes;
import ru.icbcom.aistdapsdkjava.api.datasource.DataSource;
import ru.icbcom.aistdapsdkjava.api.datasource.DataSourceList;
import ru.icbcom.aistdapsdkjava.api.datasource.DataSources;
import ru.icbcom.aistdapsdkjava.api.datasourcegroup.DataSourceGroup;
import ru.icbcom.aistdapsdkjava.api.datasourcegroup.DataSourceGroupList;
import ru.icbcom.aistdapsdkjava.api.datasourcegroup.DataSourceGroups;
import ru.icbcom.aistdapsdkjava.api.objecttype.Attribute;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectType;
import ru.icbcom.aistdapsdkjava.api.objecttype.Section;
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

class ObjectTypeIntegrationTest extends AbstractIntegrationTest {

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
    }

    @Test
    void saveShouldWorkProperly() {
        // Запрос обновления типа объекта с идентификатором 1.
        mockServer.when(request()
                .withMethod("PUT")
                .withHeader("Authorization", "Bearer some-access-token")
                .withHeader("Accept", "application/json, application/problem+json")
                .withPath("/objectTypes/1")
                .withBody(json(loadTemplatedResource("integration/objectType/objectTypeWithIdEqToOneUpdateRequest.json", Map.of("serverPort", mockServer.getLocalPort())), STRICT)))
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/objectType/objectTypeWithIdEqToOneUpdateResponse.json", Map.of("serverPort", mockServer.getLocalPort()))));

        ObjectType objectType = client.objectTypes().getById(1L).orElseThrow();
        objectType.setCaption("Новый заголовок типа объекта")
                .setName("NewObjectTypeName")
                .setEnabled(false);
        Section settingsSection = objectType.getSectionByName("Settings").orElseThrow();
        settingsSection.setCaption("Новое название секции")
                .setName("New settingsSection name")
                .setComment("New settingsSection comment");
        Attribute addressAttribute = settingsSection.getAttributeByName("Address").orElseThrow();
        addressAttribute.setName("NewAttributeName")
                .setCaption("Новое название атрибута")
                .setComment("Комментарий к атрибуту");
        objectType.save();

        mockServer.verify(request()
                        .withMethod("PUT")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes/1")
                        .withBody(json(loadTemplatedResource("integration/objectType/objectTypeWithIdEqToOneUpdateRequest.json", Map.of("serverPort", mockServer.getLocalPort())), STRICT)),
                VerificationTimes.exactly(1));
    }

    @Test
    void deleteShouldWorkProperly() {
        // Запрос удаления типа объекта с идентификатором 1.
        mockServer.when(request()
                .withMethod("DELETE")
                .withHeader("Authorization", "Bearer some-access-token")
                .withHeader("Accept", "application/json, application/problem+json")
                .withPath("/objectTypes/1"))
                .respond(response().withStatusCode(204));

        ObjectType objectType = client.objectTypes().getById(1L).orElseThrow();
        objectType.delete();

        mockServer.verify(request()
                        .withMethod("DELETE")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes/1"),
                VerificationTimes.exactly(1));
    }

    @Test
    void getDataSourcesShouldWorkProperly() {
        // Запрос источников данных для типа объекта с идентификатором 1.
        mockServer.when(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes/1/dataSources"),
                once())
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/objectType/dataSourcesListResponse.json", Map.of("serverPort", mockServer.getLocalPort()))));

        ObjectType objectType = client.objectTypes().getById(1L).orElseThrow();
        DataSourceList dataSourceList = objectType.getDataSources();

        assertThat(dataSourceList, allOf(
                hasProperty("size", is(2L)),
                hasProperty("totalElements", is(2L)),
                hasProperty("totalPages", is(1L)),
                hasProperty("number", is(0L))
        ));
        Iterator<DataSource> dataSourceListIterator = dataSourceList.iterator();
        assertTrue(dataSourceListIterator.hasNext());
        assertThat(dataSourceListIterator.next(), allOf(
                hasProperty("dataSourceId", is(15L)),
                hasProperty("objectTypeId", is(1L)),
                hasProperty("caption", is("Электроэнергия, R+ Тариф 4")),
                hasProperty("measureItem", is("квар*ч")),
                hasProperty("dataSourceGroupId", is(3L))
        ));
        assertTrue(dataSourceListIterator.hasNext());
        assertThat(dataSourceListIterator.next(), allOf(
                hasProperty("dataSourceId", is(14L)),
                hasProperty("objectTypeId", is(1L)),
                hasProperty("caption", is("Электроэнергия, R+ Тариф 3")),
                hasProperty("measureItem", is("квар*ч")),
                hasProperty("dataSourceGroupId", is(3L))
        ));
        assertFalse(dataSourceListIterator.hasNext());
        mockServer.verify(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes/1/dataSources"),
                VerificationTimes.exactly(1));
    }

    @Test
    void getDataSourcesWithCriteriaShouldWorkProperly() {
        mockServer.when(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes/1/dataSources")
                        .withQueryStringParameters(param("size", "2"), param("page", "0"), param("sort", "caption,desc")),
                once())
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/objectType/dataSourcesListResponse.json", Map.of("serverPort", mockServer.getLocalPort()))));

        ObjectType objectType = client.objectTypes().getById(1L).orElseThrow();
        DataSourceList dataSourceList = objectType
                .getDataSources(DataSources.criteria()
                        .orderByCaption().descending()
                        .pageNumber(0)
                        .pageSize(2));

        assertThat(dataSourceList, allOf(
                hasProperty("size", is(2L)),
                hasProperty("totalElements", is(2L)),
                hasProperty("totalPages", is(1L)),
                hasProperty("number", is(0L))
        ));
        Iterator<DataSource> dataSourceListIterator = dataSourceList.iterator();
        assertTrue(dataSourceListIterator.hasNext());
        assertThat(dataSourceListIterator.next(), allOf(
                hasProperty("dataSourceId", is(15L)),
                hasProperty("objectTypeId", is(1L)),
                hasProperty("caption", is("Электроэнергия, R+ Тариф 4")),
                hasProperty("measureItem", is("квар*ч")),
                hasProperty("dataSourceGroupId", is(3L))
        ));
        assertTrue(dataSourceListIterator.hasNext());
        assertThat(dataSourceListIterator.next(), allOf(
                hasProperty("dataSourceId", is(14L)),
                hasProperty("objectTypeId", is(1L)),
                hasProperty("caption", is("Электроэнергия, R+ Тариф 3")),
                hasProperty("measureItem", is("квар*ч")),
                hasProperty("dataSourceGroupId", is(3L))
        ));
        assertFalse(dataSourceListIterator.hasNext());
        mockServer.verify(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes/1/dataSources")
                        .withQueryStringParameters(param("size", "2"), param("page", "0"), param("sort", "caption,desc")),
                VerificationTimes.exactly(1));
    }

    @Test
    void createDataSourceShouldWorkProperly() {
        // Запрос создания нового источника данных.
        mockServer.when(request()
                        .withMethod("POST")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes/1/dataSources")
                        .withBody(json(loadResource("integration/objectType/dataSourceCreationRequest.json"), MatchType.STRICT)),
                once())
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/objectType/dataSourceCreationResponse.json", Map.of("serverPort", mockServer.getLocalPort()))));

        ObjectType objectType = client.objectTypes().getById(1L).orElseThrow();
        DataSource dataSourceToCreate = client.instantiate(DataSource.class)
                .setDataSourceId(12345L)
                .setCaption("Название нового источника данных")
                .setMeasureItem("единица измерения")
                .setDataSourceGroupId(3L);
        DataSource createdDataSource = objectType.createDataSource(dataSourceToCreate);

        assertEquals(dataSourceToCreate, createdDataSource);
        mockServer.verify(request()
                        .withMethod("POST")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes/1/dataSources")
                        .withBody(json(loadResource("integration/objectType/dataSourceCreationRequest.json"), MatchType.STRICT)),
                VerificationTimes.exactly(1));
    }

    @Test
    void getDataSourceByIdShouldWorkProperly() {
        // Запрос источника данных с идентификатором 2 для типа объекта с идентификатором 1.
        mockServer.when(request()
                .withMethod("GET")
                .withHeader("Authorization", "Bearer some-access-token")
                .withHeader("Accept", "application/json, application/problem+json")
                .withPath("/objectTypes/1/dataSources/2"))
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/objectType/dataSourceWithIdEqualTwoResponse.json", Map.of("serverPort", mockServer.getLocalPort()))));

        ObjectType objectType = client.objectTypes().getById(1L).orElseThrow();
        Optional<DataSource> dataSourceOptional = objectType.getDataSourceById(2L);

        assertTrue(dataSourceOptional.isPresent());
        DataSource dataSource = dataSourceOptional.get();
        assertThat(dataSource, allOf(
                hasProperty("dataSourceId", is(2L)),
                hasProperty("objectTypeId", is(1L)),
                hasProperty("caption", is("Электроэнергия, A+ Тариф 1")),
                hasProperty("measureItem", is("кВт*ч")),
                hasProperty("dataSourceGroupId", is(1L))
        ));
        mockServer.verify(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes/1/dataSources/2"),
                VerificationTimes.exactly(1));
    }

    @Test
    void getDataSourceGroupsShouldWorkProperly() {
        // Запрос групп источников данных для типа объекта с идентификатором 1.
        mockServer.when(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes/1/dataSourceGroups"),
                once())
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/objectType/dataSourceGroupListResponse.json", Map.of("serverPort", mockServer.getLocalPort()))));

        ObjectType objectType = client.objectTypes().getById(1L).orElseThrow();
        DataSourceGroupList dataSourceGroupList = objectType.getDataSourceGroups();

        assertThat(dataSourceGroupList, allOf(
                hasProperty("size", is(2L)),
                hasProperty("totalElements", is(2L)),
                hasProperty("totalPages", is(1L)),
                hasProperty("number", is(0L))
        ));
        Iterator<DataSourceGroup> dataSourceGroupListIterator = dataSourceGroupList.iterator();
        assertTrue(dataSourceGroupListIterator.hasNext());
        assertThat(dataSourceGroupListIterator.next(), allOf(
                hasProperty("dataSourceGroupId", is(5L)),
                hasProperty("objectTypeId", is(1L)),
                hasProperty("caption", is("Электрическая мощность"))
        ));
        assertTrue(dataSourceGroupListIterator.hasNext());
        assertThat(dataSourceGroupListIterator.next(), allOf(
                hasProperty("dataSourceGroupId", is(3L)),
                hasProperty("objectTypeId", is(1L)),
                hasProperty("caption", is("Параметры электроэнергии R+"))
        ));
        assertFalse(dataSourceGroupListIterator.hasNext());
        mockServer.verify(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes/1/dataSourceGroups"),
                VerificationTimes.exactly(1));
    }

    @Test
    void getDataSourceGroupsWithCriteriaShouldWorkProperly() {
        // Запрос групп источников данных для типа объекта с идентификатором 1.
        mockServer.when(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes/1/dataSourceGroups")
                        .withQueryStringParameters(param("size", "2"), param("page", "0"), param("sort", "caption,desc")),
                once())
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/objectType/dataSourceGroupListResponse.json", Map.of("serverPort", mockServer.getLocalPort()))));

        ObjectType objectType = client.objectTypes().getById(1L).orElseThrow();
        DataSourceGroupList dataSourceGroupList = objectType
                .getDataSourceGroups(DataSourceGroups.criteria()
                        .orderByCaption().descending()
                        .pageNumber(0)
                        .pageSize(2));

        assertThat(dataSourceGroupList, allOf(
                hasProperty("size", is(2L)),
                hasProperty("totalElements", is(2L)),
                hasProperty("totalPages", is(1L)),
                hasProperty("number", is(0L))
        ));
        Iterator<DataSourceGroup> dataSourceGroupListIterator = dataSourceGroupList.iterator();
        assertTrue(dataSourceGroupListIterator.hasNext());
        assertThat(dataSourceGroupListIterator.next(), allOf(
                hasProperty("dataSourceGroupId", is(5L)),
                hasProperty("objectTypeId", is(1L)),
                hasProperty("caption", is("Электрическая мощность"))
        ));
        assertTrue(dataSourceGroupListIterator.hasNext());
        assertThat(dataSourceGroupListIterator.next(), allOf(
                hasProperty("dataSourceGroupId", is(3L)),
                hasProperty("objectTypeId", is(1L)),
                hasProperty("caption", is("Параметры электроэнергии R+"))
        ));
        assertFalse(dataSourceGroupListIterator.hasNext());
        mockServer.verify(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes/1/dataSourceGroups")
                        .withQueryStringParameters(param("size", "2"), param("page", "0"), param("sort", "caption,desc")),
                VerificationTimes.exactly(1));
    }

    @Test
    void getDataSourceGroupByIdShouldWorkProperly() {
        // Запрос группы источников данных с идентификатором 2 для типа объекта с идентификатором 1.
        mockServer.when(request()
                .withMethod("GET")
                .withHeader("Authorization", "Bearer some-access-token")
                .withHeader("Accept", "application/json, application/problem+json")
                .withPath("/objectTypes/1/dataSourceGroups/2"))
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/objectType/dataSourceGroupWithIdEqualTwoResponse.json", Map.of("serverPort", mockServer.getLocalPort()))));

        ObjectType objectType = client.objectTypes().getById(1L).orElseThrow();
        Optional<DataSourceGroup> dataSourceGroupOptional = objectType.getDataSourceGroupById(2L);

        assertTrue(dataSourceGroupOptional.isPresent());
        DataSourceGroup dataSourceGroup = dataSourceGroupOptional.get();
        assertThat(dataSourceGroup, allOf(
                hasProperty("dataSourceGroupId", is(2L)),
                hasProperty("objectTypeId", is(1L)),
                hasProperty("caption", is("Параметры электроэнергии A-"))
        ));
        mockServer.verify(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes/1/dataSourceGroups/2"),
                VerificationTimes.exactly(1));
    }

    @Test
    void createDataSourceGroupShouldWorkProperly() {
        // Запрос создания новой группы источников данных.
        mockServer.when(request()
                        .withMethod("POST")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes/1/dataSourceGroups")
                        .withBody(json(loadResource("integration/objectType/dataSourceGroupCreationRequest.json"), MatchType.STRICT)),
                once())
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/objectType/dataSourceGroupCreationResponse.json", Map.of("serverPort", mockServer.getLocalPort()))));

        ObjectType objectType = client.objectTypes().getById(1L).orElseThrow();
        DataSourceGroup dataSourceGroupToCreate = client.instantiate(DataSourceGroup.class)
                .setDataSourceGroupId(12345L)
                .setCaption("Название группы источников данных");
        DataSourceGroup createdDataSourceGroup = objectType.createDataSourceGroup(dataSourceGroupToCreate);

        assertEquals(dataSourceGroupToCreate, createdDataSourceGroup);
        mockServer.verify(request()
                        .withMethod("POST")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes/1/dataSourceGroups")
                        .withBody(json(loadResource("integration/objectType/dataSourceGroupCreationRequest.json"), MatchType.STRICT)),
                VerificationTimes.exactly(1));
    }

}