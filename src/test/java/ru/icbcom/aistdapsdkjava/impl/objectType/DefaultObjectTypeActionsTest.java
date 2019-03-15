package ru.icbcom.aistdapsdkjava.impl.objectType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.Link;
import ru.icbcom.aistdapsdkjava.api.exception.AistDapBackendException;
import ru.icbcom.aistdapsdkjava.api.exception.LinkNotFoundException;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectType;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.error.DefaultError;
import ru.icbcom.aistdapsdkjava.impl.resource.DefaultVoidResource;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// TODO: Перенестии все остальные тесты DefaultObjectTypeActions сюда из DefaultClientTest.

@ExtendWith(MockitoExtension.class)
class DefaultObjectTypeActionsTest {

    @Mock
    DataStore dataStore;

    private Link baseLink;
    private DefaultObjectTypeActions objectTypeActions;

    @BeforeEach
    void setup() {
        baseLink = new Link("http://127.0.0.1/");
        objectTypeActions = new DefaultObjectTypeActions(baseLink, dataStore);
    }

    @Test
    void getByIdShouldWorkProperly() {
        DefaultVoidResource rootVoidResource = new DefaultVoidResource(dataStore);
        rootVoidResource.add(new Link("http://127.0.0.1/objectTypes", "dap:objectTypes"));
        doReturn(rootVoidResource).when(dataStore).getResource(baseLink, DefaultVoidResource.class);

        DefaultObjectType objectTypeToReturn = new DefaultObjectType(dataStore);
        doReturn(objectTypeToReturn).when(dataStore).getResource(new Link("http://127.0.0.1/objectTypes/1", "dap:objectTypes"), DefaultObjectType.class);

        Optional<ObjectType> objectTypeOptional = objectTypeActions.getById(1L);
        assertTrue(objectTypeOptional.isPresent());
        assertSame(objectTypeToReturn, objectTypeOptional.get());

        verify(dataStore).getResource(baseLink, DefaultVoidResource.class);
        verify(dataStore).getResource(new Link("http://127.0.0.1/objectTypes/1", "dap:objectTypes"), DefaultObjectType.class);
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void getByIdExceptionShouldBeThrownWhenThereIsNoLink() {
        DefaultVoidResource rootVoidResource = new DefaultVoidResource(dataStore);
        doReturn(rootVoidResource).when(dataStore).getResource(baseLink, DefaultVoidResource.class);

        LinkNotFoundException exception = assertThrows(LinkNotFoundException.class, () -> objectTypeActions.getById(1L));
        assertThat(exception, allOf(
                hasProperty("message", is("Link with relation 'dap:objectTypes' was not found in resource with href 'http://127.0.0.1/'")),
                hasProperty("resourceHref", is("http://127.0.0.1/")),
                hasProperty("rel", is("dap:objectTypes"))
        ));
        verify(dataStore).getResource(baseLink, DefaultVoidResource.class);
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void getByIdShouldThrowExceptionWhenStatusCodeIsNot404() {
        DefaultVoidResource rootVoidResource = new DefaultVoidResource(dataStore);
        rootVoidResource.add(new Link("http://127.0.0.1/objectTypes", "dap:objectTypes"));
        doReturn(rootVoidResource).when(dataStore).getResource(baseLink, DefaultVoidResource.class);

        DefaultError defaultError = new DefaultError();
        defaultError.setStatus(406);
        AistDapBackendException exceptionToThrow = new AistDapBackendException(defaultError);
        doThrow(exceptionToThrow).when(dataStore).getResource(new Link("http://127.0.0.1/objectTypes/1", "dap:objectTypes"), DefaultObjectType.class);

        AistDapBackendException exception = assertThrows(AistDapBackendException.class, () -> objectTypeActions.getById(1L));
        assertSame(exceptionToThrow, exception);

        verify(dataStore).getResource(baseLink, DefaultVoidResource.class);
        verify(dataStore).getResource(new Link("http://127.0.0.1/objectTypes/1", "dap:objectTypes"), DefaultObjectType.class);
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void createObjectTypeShouldWorkProperly() {
        DefaultVoidResource rootVoidResource = new DefaultVoidResource(dataStore);
        rootVoidResource.add(new Link("http://127.0.0.1/objectTypes", "dap:objectTypes"));
        doReturn(rootVoidResource).when(dataStore).getResource(baseLink, DefaultVoidResource.class);

        ObjectType objectTypeToCreate = new DefaultObjectType(dataStore);

        DefaultObjectType objectTypeCreationResult = new DefaultObjectType(dataStore);
        when(dataStore.create(new Link("http://127.0.0.1/objectTypes", "dap:objectTypes"), objectTypeToCreate)).thenReturn(objectTypeCreationResult);

        ObjectType createdObjectType = objectTypeActions.createObjectType(objectTypeToCreate);
        assertSame(objectTypeCreationResult, createdObjectType);

        verify(dataStore).getResource(baseLink, DefaultVoidResource.class);
        verify(dataStore).create(new Link("http://127.0.0.1/objectTypes", "dap:objectTypes"), objectTypeToCreate);
        verifyNoMoreInteractions(dataStore);
    }

    /*

    @Test
    void createDataSourceGroupShouldWorkProperly() {
        ObjectType objectType = new DefaultObjectType(dataStore);
        objectType.setId(100L);
        objectType.add(new Link("http://127.0.0.1:8080/objectTypes/3/dataSourceGroups{?page,size,sort}", "dap:dataSourceGroups"));

        DataSourceGroup dataSourceGroupToCreate = new DefaultDataSourceGroup(dataStore);

        DataSourceGroup dataSourceGroupCreationResult = new DefaultDataSourceGroup(dataStore);
        when(dataStore.create(new Link("http://127.0.0.1:8080/objectTypes/3/dataSourceGroups{?page,size,sort}", "dap:dataSourceGroups"), dataSourceGroupToCreate)).thenReturn(dataSourceGroupCreationResult);

        DataSourceGroup createdDataSourceGroup = objectType.createDataSourceGroup(dataSourceGroupToCreate);

        assertSame(dataSourceGroupCreationResult, createdDataSourceGroup);

        ArgumentCaptor<DataSourceGroup> dataSourceArgumentCaptor = ArgumentCaptor.forClass(DataSourceGroup.class);
        verify(dataStore).create(eq(new Link("http://127.0.0.1:8080/objectTypes/3/dataSourceGroups{?page,size,sort}", "dap:dataSourceGroups")), dataSourceArgumentCaptor.capture());
        assertEquals(100L, dataSourceArgumentCaptor.getValue().getObjectTypeId());
        verifyNoMoreInteractions(dataStore);
    }
     */

}