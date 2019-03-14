package ru.icbcom.aistdapsdkjava.impl.datasource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.Link;
import ru.icbcom.aistdapsdkjava.api.datasource.DataSource;
import ru.icbcom.aistdapsdkjava.api.exception.LinkNotFoundException;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectType;
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

}