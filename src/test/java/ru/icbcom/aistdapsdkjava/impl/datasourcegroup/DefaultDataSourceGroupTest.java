package ru.icbcom.aistdapsdkjava.impl.datasourcegroup;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.Link;
import ru.icbcom.aistdapsdkjava.api.datasource.DataSourceCriteria;
import ru.icbcom.aistdapsdkjava.api.datasource.DataSourceList;
import ru.icbcom.aistdapsdkjava.api.datasource.DataSources;
import ru.icbcom.aistdapsdkjava.api.datasourcegroup.DataSourceGroup;
import ru.icbcom.aistdapsdkjava.api.exception.LinkNotFoundException;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectType;
import ru.icbcom.aistdapsdkjava.impl.datasource.DefaultDataSourceList;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.objectType.DefaultObjectType;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultDataSourceGroupTest {

    @Mock
    DataStore dataStore;

    @Test
    void fieldsInitializationShouldWorkProperly() {
        DataSourceGroup dataSourceGroup = new DefaultDataSourceGroup(dataStore)
                .setDataSourceGroupId(1000L)
                .setObjectTypeId(100L)
                .setCaption("Название группы источников данных");

        assertThat(dataSourceGroup, allOf(
                hasProperty("dataSourceGroupId", is(1000L)),
                hasProperty("objectTypeId", is(100L)),
                hasProperty("caption", is("Название группы источников данных")),
                hasProperty("dataStore", is(sameInstance(dataStore)))
        ));
    }

    @Test
    void saveShouldWorkProperly() {
        DataSourceGroup dataSourceGroup = new DefaultDataSourceGroup(dataStore)
                .setDataSourceGroupId(1000L)
                .setObjectTypeId(100L)
                .setCaption("Название группы источников данных");
        dataSourceGroup.add(new Link("http://127.0.0.1:8080/objectTypes/1/dataSourceGroups/1000", "self"));

        dataSourceGroup.save();

        verify(dataStore).save(same(dataSourceGroup));
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void deleteShouldWorkProperly() {
        DataSourceGroup dataSourceGroup = new DefaultDataSourceGroup(dataStore)
                .setDataSourceGroupId(1000L)
                .setObjectTypeId(100L)
                .setCaption("Название группы источников данных");
        dataSourceGroup.add(new Link("http://127.0.0.1:8080/objectTypes/1/dataSourceGroups/1000", "self"));

        dataSourceGroup.delete();

        verify(dataStore).delete(same(dataSourceGroup));
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void getObjectTypeShouldWorkProperly() {
        DataSourceGroup dataSourceGroup = new DefaultDataSourceGroup(dataStore)
                .setDataSourceGroupId(1000L)
                .setObjectTypeId(1L)
                .setCaption("Название источника данных");
        dataSourceGroup.add(new Link("http://127.0.0.1/objectTypes/1", "dap:objectType"));
        DefaultObjectType objectTypeToReturn = new DefaultObjectType(dataStore);
        when(dataStore.getResource(new Link("http://127.0.0.1/objectTypes/1", "dap:objectType"), DefaultObjectType.class)).thenReturn(objectTypeToReturn);

        ObjectType objectType = dataSourceGroup.getObjectType();

        assertSame(objectTypeToReturn, objectType);
        verify(dataStore).getResource(new Link("http://127.0.0.1/objectTypes/1", "dap:objectType"), DefaultObjectType.class);
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void getObjectTypeExceptionShouldBeThrownWhenThereIsNoLink() {
        DataSourceGroup dataSourceGroup = new DefaultDataSourceGroup(dataStore)
                .setDataSourceGroupId(1000L)
                .setObjectTypeId(1L)
                .setCaption("Название источника данных");

        LinkNotFoundException exception = assertThrows(LinkNotFoundException.class, () -> dataSourceGroup.getObjectType());

        assertThat(exception, allOf(
                hasProperty("message", is("Link 'dap:objectType' was not found in the current DataSourceGroup object. Method 'getObjectType()' " +
                        "may only be called on DataSourceGroup objects that have already been persisted and have an existing 'dap:objectType' link.")),
                hasProperty("resourceHref", is(nullValue())),
                hasProperty("rel", is("dap:objectType"))
        ));
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void getDataSourcesWithCriteriaShouldWorkProperly() {
        DataSourceGroup dataSourceGroup = new DefaultDataSourceGroup(dataStore)
                .setDataSourceGroupId(1000L)
                .setObjectTypeId(1L)
                .setCaption("Название источника данных");
        dataSourceGroup.add(new Link("http://127.0.0.1:8080/objectTypes/1/dataSourceGroups/3/dataSources", "dap:dataSourcesInGroup"));
        DataSourceCriteria criteria = DataSources.criteria()
                .orderByMeasureItem().ascending()
                .pageSize(100);
        DefaultDataSourceList dataSourceList = new DefaultDataSourceList(dataStore);
        when(dataStore.getResource(eq(new Link("http://127.0.0.1:8080/objectTypes/1/dataSourceGroups/3/dataSources", "dap:dataSourcesInGroup")), eq(DefaultDataSourceList.class), same(criteria)))
                .thenReturn(dataSourceList);

        DataSourceList dataSources = dataSourceGroup.getDataSources(criteria);

        assertSame(dataSourceList, dataSources);
        verify(dataStore).getResource(eq(new Link("http://127.0.0.1:8080/objectTypes/1/dataSourceGroups/3/dataSources", "dap:dataSourcesInGroup")), eq(DefaultDataSourceList.class), same(criteria));
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void getDataSourcesWithCriteriaExceptionShouldBeThrownWhenThereIsNoLink() {
        DataSourceGroup dataSourceGroup = new DefaultDataSourceGroup(dataStore)
                .setDataSourceGroupId(1000L)
                .setObjectTypeId(1L)
                .setCaption("Название источника данных");
        DataSourceCriteria criteria = DataSources.criteria()
                .orderByMeasureItem().ascending()
                .pageSize(100);

        LinkNotFoundException exception = assertThrows(LinkNotFoundException.class, () -> dataSourceGroup.getDataSources(criteria));

        assertThat(exception, allOf(
                hasProperty("message", is("Link 'dap:dataSourcesInGroup' was not found in the current DataSourceGroup object. Some methods " +
                        "may only be called on DataSourceGroup objects that have already been persisted and have an existing 'dap:dataSourcesInGroup' link.")),
                hasProperty("resourceHref", is(nullValue())),
                hasProperty("rel", is("dap:dataSourcesInGroup"))
        ));
        verifyNoMoreInteractions(dataStore);
    }


}