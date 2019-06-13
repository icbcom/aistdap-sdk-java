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

package ru.icbcom.aistdapsdkjava.impl.datasource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.Link;
import ru.icbcom.aistdapsdkjava.api.datasource.DataSource;
import ru.icbcom.aistdapsdkjava.api.datasourcegroup.DataSourceGroup;
import ru.icbcom.aistdapsdkjava.api.exception.LinkNotFoundException;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectType;
import ru.icbcom.aistdapsdkjava.impl.datasourcegroup.DefaultDataSourceGroup;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.objectType.DefaultObjectType;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultDataSourceTest {

    @Mock
    DataStore dataStore;

    @Test
    void fieldsInitializationShouldWorkProperly() {
        DataSource dataSource = new DefaultDataSource(dataStore)
                .setDataSourceId(100L)
                .setObjectTypeId(1L)
                .setCaption("Название источника данных")
                .setMeasureItem("Единица измерения")
                .setDataSourceGroupId(1000L);

        assertThat(dataSource, allOf(
                hasProperty("dataSourceId", is(100L)),
                hasProperty("objectTypeId", is(1L)),
                hasProperty("caption", is("Название источника данных")),
                hasProperty("measureItem", is("Единица измерения")),
                hasProperty("dataSourceGroupId", is(1000L)),
                hasProperty("dataStore", is(sameInstance(dataStore)))
        ));
    }

    @Test
    void getObjectTypeShouldWorkProperly() {
        DataSource dataSource = new DefaultDataSource(dataStore)
                .setDataSourceId(100L)
                .setObjectTypeId(1L)
                .setCaption("Название источника данных")
                .setMeasureItem("Единица измерения")
                .setDataSourceGroupId(1000L);
        dataSource.add(new Link("http://127.0.0.1/objectTypes/1", "dap:objectType"));

        DefaultObjectType objectTypeToReturn = new DefaultObjectType(dataStore);
        when(dataStore.getResource(new Link("http://127.0.0.1/objectTypes/1", "dap:objectType"), DefaultObjectType.class)).thenReturn(objectTypeToReturn);

        ObjectType objectType = dataSource.getObjectType();
        assertSame(objectTypeToReturn, objectType);

        verify(dataStore).getResource(new Link("http://127.0.0.1/objectTypes/1", "dap:objectType"), DefaultObjectType.class);
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void getObjectTypeExceptionShouldBeThrownWhenThereIsNoLink() {
        DataSource dataSource = new DefaultDataSource(dataStore)
                .setDataSourceId(100L)
                .setObjectTypeId(1L)
                .setCaption("Название источника данных")
                .setMeasureItem("Единица измерения")
                .setDataSourceGroupId(1000L);

        LinkNotFoundException exception = assertThrows(LinkNotFoundException.class, () -> dataSource.getObjectType());
        assertThat(exception, allOf(
                hasProperty("message", is("Link 'dap:objectType' was not found in the current DataSource object. Method 'getObjectType()' " +
                        "may only be called on DataSource objects that have already been persisted and have an existing 'dap:objectType' link.")),
                hasProperty("resourceHref", is(nullValue())),
                hasProperty("rel", is("dap:objectType"))
        ));
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void getDataSourceGroupShouldWorkProperly() {
        DataSource dataSource = new DefaultDataSource(dataStore)
                .setDataSourceId(100L)
                .setObjectTypeId(1L)
                .setCaption("Название источника данных")
                .setMeasureItem("Единица измерения")
                .setDataSourceGroupId(1000L);
        dataSource.add(new Link("http://127.0.0.1:8080/objectTypes/1/dataSourceGroups/100", "dap:dataSourceGroup"));

        DefaultDataSourceGroup defaultDataSourceGroupToReturn = new DefaultDataSourceGroup(dataStore);
        when(dataStore.getResource(new Link("http://127.0.0.1:8080/objectTypes/1/dataSourceGroups/100", "dap:dataSourceGroup"), DefaultDataSourceGroup.class)).thenReturn(defaultDataSourceGroupToReturn);

        DataSourceGroup dataSourceGroup = dataSource.getDataSourceGroup();
        assertSame(defaultDataSourceGroupToReturn, dataSourceGroup);

        verify(dataStore).getResource(new Link("http://127.0.0.1:8080/objectTypes/1/dataSourceGroups/100", "dap:dataSourceGroup"), DefaultDataSourceGroup.class);
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void getDataSourceGroupExceptionShouldBeThrownWhenThereIsNoLink() {
        DataSource dataSource = new DefaultDataSource(dataStore)
                .setDataSourceId(100L)
                .setObjectTypeId(1L)
                .setCaption("Название источника данных")
                .setMeasureItem("Единица измерения")
                .setDataSourceGroupId(1000L);

        LinkNotFoundException exception = assertThrows(LinkNotFoundException.class, dataSource::getDataSourceGroup);
        assertThat(exception, allOf(
                hasProperty("message", is("Link 'dap:dataSourceGroup' was not found in the current DataSource object. Method 'getDataSourceGroup()' " +
                        "may only be called on DataSource objects that have already been persisted and have an existing 'dap:dataSourceGroup' link.")),
                hasProperty("resourceHref", is(nullValue())),
                hasProperty("rel", is("dap:dataSourceGroup"))
        ));
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void saveShouldWorkProperly() {
        DataSource dataSource = new DefaultDataSource(dataStore)
                .setDataSourceId(100L)
                .setObjectTypeId(1L)
                .setCaption("Название источника данных")
                .setMeasureItem("Единица измерения")
                .setDataSourceGroupId(1000L);
        dataSource.add(new Link("http://127.0.0.1:8080/objectTypes/1/dataSource/100", "self"));

        dataSource.save();

        verify(dataStore).save(same(dataSource));
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void deleteShouldWorkProperly() {
        DataSource dataSource = new DefaultDataSource(dataStore)
                .setDataSourceId(100L)
                .setObjectTypeId(1L)
                .setCaption("Название источника данных")
                .setMeasureItem("Единица измерения")
                .setDataSourceGroupId(1000L);
        dataSource.add(new Link("http://127.0.0.1:8080/objectTypes/1/dataSource/100", "self"));

        dataSource.delete();

        verify(dataStore).delete(same(dataSource));
        verifyNoMoreInteractions(dataStore);
    }

}