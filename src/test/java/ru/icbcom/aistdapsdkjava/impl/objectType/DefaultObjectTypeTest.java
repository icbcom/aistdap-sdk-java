package ru.icbcom.aistdapsdkjava.impl.objectType;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.Link;
import ru.icbcom.aistdapsdkjava.api.datasource.DataSource;
import ru.icbcom.aistdapsdkjava.api.datasource.DataSourceCriteria;
import ru.icbcom.aistdapsdkjava.api.datasource.DataSourceList;
import ru.icbcom.aistdapsdkjava.api.datasource.DataSources;
import ru.icbcom.aistdapsdkjava.api.exception.AistDapBackendException;
import ru.icbcom.aistdapsdkjava.api.exception.LinkNotFoundException;
import ru.icbcom.aistdapsdkjava.api.objecttype.Attribute;
import ru.icbcom.aistdapsdkjava.api.objecttype.AttributeType;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectType;
import ru.icbcom.aistdapsdkjava.api.objecttype.Section;
import ru.icbcom.aistdapsdkjava.impl.datasource.DefaultDataSource;
import ru.icbcom.aistdapsdkjava.impl.datasource.DefaultDataSourceList;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.error.DefaultError;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultObjectTypeTest {

    @Mock
    DataStore dataStore;

    @Test
    void fieldsInitializationShouldWorkProperly() {
        ObjectType objectType = new DefaultObjectType(dataStore)
                .setId(1L)
                .setName("ObjectTypeName")
                .setCaption("Заголовок типа объекта")
                .setDevice(false)
                .addSection(
                        new DefaultSection(null)
                                .setName("SectionName")
                                .setCaption("Заголовок секции")
                                .addAttribute(
                                        new DefaultAttribute(null)
                                                .setName("InterbyteTimeout")
                                                .setCaption("Таймаут межсимвольного интервала (мс)")
                                                .setType(AttributeType.INTEGER)
                                                .setMin("0")
                                                .setMax("500")
                                )
                                .addAttribute(
                                        new DefaultAttribute(null)
                                                .setName("Password")
                                                .setCaption("Пароль")
                                                .setType(AttributeType.STRING)
                                                .setMask("\\d{6}")
                                                .setDefaultValue("defaultPassword")
                                                .setComment("Комментарий к атрибуту")
                                )
                                .setComment("Комментариий к секции")
                )
                .setEnabled(true);

        assertThat(objectType, allOf(
                hasProperty("id", is(1L)),
                hasProperty("name", is("ObjectTypeName")),
                hasProperty("caption", is("Заголовок типа объекта")),
                hasProperty("device", is(false)),
                hasProperty("sections", hasItem(
                        allOf(
                                hasProperty("name", is("SectionName")),
                                hasProperty("caption", is("Заголовок секции")),
                                hasProperty("attributes", contains(
                                        allOf(
                                                hasProperty("name", is("InterbyteTimeout")),
                                                hasProperty("caption", is("Таймаут межсимвольного интервала (мс)")),
                                                hasProperty("type", is(AttributeType.INTEGER)),
                                                hasProperty("min", is("0")),
                                                hasProperty("max", is("500")),
                                                hasProperty("mask", is(nullValue())),
                                                hasProperty("enumSetValues", is(empty())),
                                                hasProperty("defaultValue", is(nullValue())),
                                                hasProperty("comment", is(nullValue()))
                                        ),
                                        allOf(
                                                hasProperty("name", is("Password")),
                                                hasProperty("caption", is("Пароль")),
                                                hasProperty("type", is(AttributeType.STRING)),
                                                hasProperty("min", is(nullValue())),
                                                hasProperty("max", is(nullValue())),
                                                hasProperty("mask", is("\\d{6}")),
                                                hasProperty("enumSetValues", is(empty())),
                                                hasProperty("defaultValue", is("defaultPassword")),
                                                hasProperty("comment", is("Комментарий к атрибуту"))
                                        )
                                )),
                                hasProperty("comment", is("Комментариий к секции"))
                        )
                )),
                hasProperty("enabled", is(true))
        ));
        Optional<Attribute> attributeOptional = objectType.getAttributeByName("Password");
        assertTrue(attributeOptional.isPresent());
        assertThat(attributeOptional.get(), allOf(
                hasProperty("name", is("Password")),
                hasProperty("caption", is("Пароль")),
                hasProperty("type", is(AttributeType.STRING)),
                hasProperty("min", is(nullValue())),
                hasProperty("max", is(nullValue())),
                hasProperty("mask", is("\\d{6}")),
                hasProperty("enumSetValues", is(empty())),
                hasProperty("defaultValue", is("defaultPassword")),
                hasProperty("comment", is("Комментарий к атрибуту"))
        ));
        assertFalse(objectType.getAttributeByName("SomeAttribute").isPresent());

        Optional<Section> sectionOptional = objectType.getSectionByName("SectionName");
        assertTrue(sectionOptional.isPresent());
        assertThat(sectionOptional.get(), allOf(
                hasProperty("name", is("SectionName")),
                hasProperty("caption", is("Заголовок секции")),
                hasProperty("attributes", contains(
                        allOf(
                                hasProperty("name", is("InterbyteTimeout")),
                                hasProperty("caption", is("Таймаут межсимвольного интервала (мс)")),
                                hasProperty("type", is(AttributeType.INTEGER)),
                                hasProperty("min", is("0")),
                                hasProperty("max", is("500")),
                                hasProperty("mask", is(nullValue())),
                                hasProperty("enumSetValues", is(empty())),
                                hasProperty("defaultValue", is(nullValue())),
                                hasProperty("comment", is(nullValue()))
                        ),
                        allOf(
                                hasProperty("name", is("Password")),
                                hasProperty("caption", is("Пароль")),
                                hasProperty("type", is(AttributeType.STRING)),
                                hasProperty("min", is(nullValue())),
                                hasProperty("max", is(nullValue())),
                                hasProperty("mask", is("\\d{6}")),
                                hasProperty("enumSetValues", is(empty())),
                                hasProperty("defaultValue", is("defaultPassword")),
                                hasProperty("comment", is("Комментарий к атрибуту"))
                        )
                )),
                hasProperty("comment", is("Комментариий к секции"))
        ));
        assertFalse(objectType.getSectionByName("SomeSection").isPresent());
    }

    @Test
    void deleteShouldWorkProperly() {
        ObjectType objectType = new DefaultObjectType(dataStore);
        objectType.delete();

        verify(dataStore).delete(same(objectType));
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void getDataSourcesWithCriteriaShouldWorkProperly() {
        ObjectType objectType = new DefaultObjectType(dataStore);
        objectType.add(new Link("http://127.0.0.1/objectTypes/1/dataSources", "dap:dataSources"));

        DataSourceCriteria criteria = DataSources.criteria()
                .orderByMeasureItem().ascending()
                .pageSize(100);

        DefaultDataSourceList dataSourceList = new DefaultDataSourceList(dataStore);
        when(dataStore.getResource(eq(new Link("http://127.0.0.1/objectTypes/1/dataSources", "dap:dataSources")), eq(DefaultDataSourceList.class), same(criteria)))
                .thenReturn(dataSourceList);

        DataSourceList dataSources = objectType.getDataSources(criteria);
        assertSame(dataSourceList, dataSources);

        verify(dataStore).getResource(eq(new Link("http://127.0.0.1/objectTypes/1/dataSources", "dap:dataSources")), eq(DefaultDataSourceList.class), same(criteria));
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void getDataSourcesWithCriteriaExceptionShouldBeThrownWhenThereIsNoLink() {
        ObjectType objectType = new DefaultObjectType(dataStore);

        DataSourceCriteria criteria = DataSources.criteria()
                .orderByMeasureItem().ascending()
                .pageSize(100);

        LinkNotFoundException exception = assertThrows(LinkNotFoundException.class, () -> objectType.getDataSources(criteria));
        assertThat(exception, allOf(
                hasProperty("message", is("Link 'dap:dataSources' was not found in the current ObjectType object. Some methods may only be called on ObjectType objects that have already been persisted and have an existing 'dap:dataSources' link.")),
                hasProperty("resourceHref", is(nullValue())),
                hasProperty("rel", is("dap:dataSources"))
        ));
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void getDataSourcesShouldWorkProperly() {
        ObjectType objectType = new DefaultObjectType(dataStore);
        objectType.add(new Link("http://127.0.0.1/objectTypes/1/dataSources", "dap:dataSources"));

        DefaultDataSourceList dataSourceList = new DefaultDataSourceList(dataStore);
        when(dataStore.getResource(eq(new Link("http://127.0.0.1/objectTypes/1/dataSources", "dap:dataSources")), eq(DefaultDataSourceList.class), any()))
                .thenReturn(dataSourceList);

        DataSourceList dataSources = objectType.getDataSources();
        assertSame(dataSourceList, dataSources);

        verify(dataStore).getResource(eq(new Link("http://127.0.0.1/objectTypes/1/dataSources", "dap:dataSources")), eq(DefaultDataSourceList.class), any());
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void createDataSourceShouldWorkProperly() {
        ObjectType objectType = new DefaultObjectType(dataStore);
        objectType.setId(100L);
        objectType.add(new Link("http://127.0.0.1/objectTypes/1/dataSources", "dap:dataSources"));

        DataSource dataSourceToCreate = new DefaultDataSource(dataStore);

        DataSource dataSourceCreationResult = new DefaultDataSource(dataStore);
        when(dataStore.create(new Link("http://127.0.0.1/objectTypes/1/dataSources", "dap:dataSources"), dataSourceToCreate)).thenReturn(dataSourceCreationResult);

        DataSource createdDataSource = objectType.createDataSource(dataSourceToCreate);

        assertSame(dataSourceCreationResult, createdDataSource);

        ArgumentCaptor<DataSource> dataSourceArgumentCaptor = ArgumentCaptor.forClass(DataSource.class);
        verify(dataStore).create(eq(new Link("http://127.0.0.1/objectTypes/1/dataSources", "dap:dataSources")), dataSourceArgumentCaptor.capture());
        assertEquals(100L, dataSourceArgumentCaptor.getValue().getObjectTypeId());
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void createDataSourceExceptionShouldBeThrownWhenThereIsNoLink() {
        ObjectType objectType = new DefaultObjectType(dataStore);

        DataSource dataSourceToCreate = new DefaultDataSource(dataStore);

        LinkNotFoundException exception = assertThrows(LinkNotFoundException.class, () -> objectType.createDataSource(dataSourceToCreate));
        assertThat(exception, allOf(
                hasProperty("message", is("Link 'dap:dataSources' was not found in the current ObjectType object. Some methods may only be called on ObjectType objects that have already been persisted and have an existing 'dap:dataSources' link.")),
                hasProperty("resourceHref", is(nullValue())),
                hasProperty("rel", is("dap:dataSources"))
        ));
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void getDataSourceByIdShouldWorkProperly() {
        ObjectType objectType = new DefaultObjectType(dataStore);
        objectType.add(new Link("http://127.0.0.1/objectTypes/1/dataSources", "dap:dataSources"));

        DefaultDataSource dataSourceToReturn = new DefaultDataSource(dataStore);
        when(dataStore.getResource(new Link("http://127.0.0.1/objectTypes/1/dataSources/10", "dap:dataSources"), DefaultDataSource.class)).thenReturn(dataSourceToReturn);

        Optional<DataSource> dataSourceOptional = objectType.getDataSourceById(10L);
        assertTrue(dataSourceOptional.isPresent());
        assertSame(dataSourceToReturn, dataSourceOptional.get());

        verify(dataStore).getResource(new Link("http://127.0.0.1/objectTypes/1/dataSources/10", "dap:dataSources"), DefaultDataSource.class);
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void getDataSourceByIdExceptionShouldBeThrownWhenThereIsNoLink() {
        ObjectType objectType = new DefaultObjectType(dataStore);

        LinkNotFoundException exception = assertThrows(LinkNotFoundException.class, () -> objectType.getDataSourceById(10L));
        assertThat(exception, allOf(
                hasProperty("message", is("Link 'dap:dataSources' was not found in the current ObjectType object. Some methods may only be called on ObjectType objects that have already been persisted and have an existing 'dap:dataSources' link.")),
                hasProperty("resourceHref", is(nullValue())),
                hasProperty("rel", is("dap:dataSources"))
        ));
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void getDataSourceByShouldReturnEmptyOptionalWhenThereIs404Error() {
        ObjectType objectType = new DefaultObjectType(dataStore);
        objectType.add(new Link("http://127.0.0.1/objectTypes/1/dataSources", "dap:dataSources"));

        DefaultError defaultError = new DefaultError();
        defaultError.setStatus(404);
        AistDapBackendException exceptionToThrow = new AistDapBackendException(defaultError);
        doThrow(exceptionToThrow).when(dataStore).getResource(new Link("http://127.0.0.1/objectTypes/1/dataSources/10", "dap:dataSources"), DefaultDataSource.class);

        Optional<DataSource> dataSourceOptional = objectType.getDataSourceById(10L);
        assertFalse(dataSourceOptional.isPresent());

        verify(dataStore).getResource(new Link("http://127.0.0.1/objectTypes/1/dataSources/10", "dap:dataSources"), DefaultDataSource.class);
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void getDataSourceByShouldThrowExceptionWhenStatusCodeIsNot404() {
        ObjectType objectType = new DefaultObjectType(dataStore);
        objectType.add(new Link("http://127.0.0.1/objectTypes/1/dataSources", "dap:dataSources"));

        DefaultError defaultError = new DefaultError();
        defaultError.setStatus(406);
        AistDapBackendException exceptionToThrow = new AistDapBackendException(defaultError);
        doThrow(exceptionToThrow).when(dataStore).getResource(new Link("http://127.0.0.1/objectTypes/1/dataSources/10", "dap:dataSources"), DefaultDataSource.class);

        AistDapBackendException exception = assertThrows(AistDapBackendException.class, () -> objectType.getDataSourceById(10L));
        assertSame(exceptionToThrow, exception);

        verify(dataStore).getResource(new Link("http://127.0.0.1/objectTypes/1/dataSources/10", "dap:dataSources"), DefaultDataSource.class);
        verifyNoMoreInteractions(dataStore);
    }

}