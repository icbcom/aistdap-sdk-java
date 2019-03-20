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
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectTypeCriteria;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectTypeList;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectTypes;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.error.DefaultError;
import ru.icbcom.aistdapsdkjava.impl.resource.DefaultVoidResource;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

    @Test
    void getAllWithCriteriaShouldWorkProperly() {
        DefaultVoidResource rootVoidResource = new DefaultVoidResource(dataStore);
        rootVoidResource.add(new Link("http://127.0.0.1/objectTypes", "dap:objectTypes"));
        doReturn(rootVoidResource).when(dataStore).getResource(baseLink, DefaultVoidResource.class);

        DefaultObjectTypeList objectTypeListToReturn = new DefaultObjectTypeList(dataStore);
        doReturn(objectTypeListToReturn).when(dataStore).getResource(any(), eq(DefaultObjectTypeList.class), any());

        ObjectTypeCriteria criteria = ObjectTypes.criteria()
                .pageSize(100)
                .orderByName().descending();
        ObjectTypeList getAllResult = objectTypeActions.getAll(criteria);

        assertSame(objectTypeListToReturn, getAllResult);
        verify(dataStore).getResource(baseLink, DefaultVoidResource.class);
        verify(dataStore).getResource(eq(new Link("http://127.0.0.1/objectTypes", "dap:objectTypes")), eq(DefaultObjectTypeList.class), same(criteria));
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void getAllShouldWorkProperly() {
        DefaultVoidResource rootVoidResource = new DefaultVoidResource(dataStore);
        rootVoidResource.add(new Link("http://127.0.0.1/objectTypes", "dap:objectTypes"));
        doReturn(rootVoidResource).when(dataStore).getResource(baseLink, DefaultVoidResource.class);

        DefaultObjectTypeList objectTypeListToReturn = new DefaultObjectTypeList(dataStore);
        doReturn(objectTypeListToReturn).when(dataStore).getResource(any(), eq(DefaultObjectTypeList.class), any());

        ObjectTypeList getAllResult = objectTypeActions.getAll();

        assertSame(objectTypeListToReturn, getAllResult);
        verify(dataStore).getResource(baseLink, DefaultVoidResource.class);
        verify(dataStore).getResource(eq(new Link("http://127.0.0.1/objectTypes", "dap:objectTypes")), eq(DefaultObjectTypeList.class), eq(ObjectTypes.criteria()));
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void getAllEnabledDevicesWithCriteriaShouldWorkProperly() {
        DefaultVoidResource rootVoidResource = new DefaultVoidResource(dataStore);
        rootVoidResource.add(new Link("http://127.0.0.1/objectTypes", "dap:objectTypes"));
        doReturn(rootVoidResource).when(dataStore).getResource(baseLink, DefaultVoidResource.class);

        DefaultVoidResource objectTypeCollectionResource = new DefaultVoidResource(dataStore);
        objectTypeCollectionResource.add(new Link("http://127.0.0.1/objectTypes/search", "search"));
        doReturn(objectTypeCollectionResource).when(dataStore).getResource(new Link("http://127.0.0.1/objectTypes", "dap:objectTypes"), DefaultVoidResource.class);

        DefaultVoidResource objectTypeSearchResource = new DefaultVoidResource(dataStore);
        objectTypeSearchResource.add(new Link("http://127.0.0.1/objectTypes/search/findAllEnabledDevices", "dap:findAllEnabledDevices"));
        doReturn(objectTypeSearchResource).when(dataStore).getResource(new Link("http://127.0.0.1/objectTypes/search", "search"), DefaultVoidResource.class);

        DefaultObjectTypeList objectTypeListToReturn = new DefaultObjectTypeList(dataStore);
        doReturn(objectTypeListToReturn).when(dataStore).getResource(eq(new Link("http://127.0.0.1/objectTypes/search/findAllEnabledDevices", "dap:findAllEnabledDevices")), eq(DefaultObjectTypeList.class), any());

        ObjectTypeList allEnabledDevicesResult = objectTypeActions.getAllEnabledDevices(ObjectTypes.criteria().pageSize(100).orderByName().descending());
        assertSame(objectTypeListToReturn, allEnabledDevicesResult);
        verify(dataStore).getResource(baseLink, DefaultVoidResource.class);
        verify(dataStore).getResource(new Link("http://127.0.0.1/objectTypes", "dap:objectTypes"), DefaultVoidResource.class);
        verify(dataStore).getResource(new Link("http://127.0.0.1/objectTypes/search", "search"), DefaultVoidResource.class);
        verify(dataStore).getResource(eq(new Link("http://127.0.0.1/objectTypes/search/findAllEnabledDevices", "dap:findAllEnabledDevices")), eq(DefaultObjectTypeList.class), any());
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void getAllDevicesWithCriteriaShouldWorkProperly() {
        DefaultVoidResource rootVoidResource = new DefaultVoidResource(dataStore);
        rootVoidResource.add(new Link("http://127.0.0.1/objectTypes", "dap:objectTypes"));
        doReturn(rootVoidResource).when(dataStore).getResource(baseLink, DefaultVoidResource.class);

        DefaultVoidResource objectTypeCollectionResource = new DefaultVoidResource(dataStore);
        objectTypeCollectionResource.add(new Link("http://127.0.0.1/objectTypes/search", "search"));
        doReturn(objectTypeCollectionResource).when(dataStore).getResource(new Link("http://127.0.0.1/objectTypes", "dap:objectTypes"), DefaultVoidResource.class);

        DefaultVoidResource objectTypeSearchResource = new DefaultVoidResource(dataStore);
        objectTypeSearchResource.add(new Link("http://127.0.0.1/objectTypes/search/findAllDevices", "dap:findAllDevices"));
        doReturn(objectTypeSearchResource).when(dataStore).getResource(new Link("http://127.0.0.1/objectTypes/search", "search"), DefaultVoidResource.class);

        DefaultObjectTypeList objectTypeListToReturn = new DefaultObjectTypeList(dataStore);
        doReturn(objectTypeListToReturn).when(dataStore).getResource(eq(new Link("http://127.0.0.1/objectTypes/search/findAllDevices", "dap:findAllDevices")), eq(DefaultObjectTypeList.class), any());

        ObjectTypeList allEnabledDevicesResult = objectTypeActions.getAllDevices(ObjectTypes.criteria().pageSize(100).orderByName().descending());
        assertSame(objectTypeListToReturn, allEnabledDevicesResult);
        verify(dataStore).getResource(baseLink, DefaultVoidResource.class);
        verify(dataStore).getResource(new Link("http://127.0.0.1/objectTypes", "dap:objectTypes"), DefaultVoidResource.class);
        verify(dataStore).getResource(new Link("http://127.0.0.1/objectTypes/search", "search"), DefaultVoidResource.class);
        verify(dataStore).getResource(eq(new Link("http://127.0.0.1/objectTypes/search/findAllDevices", "dap:findAllDevices")), eq(DefaultObjectTypeList.class), any());
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void getAllEnabledShouldWorkProperly() {
        DefaultVoidResource rootVoidResource = new DefaultVoidResource(dataStore);
        rootVoidResource.add(new Link("http://127.0.0.1/objectTypes", "dap:objectTypes"));
        doReturn(rootVoidResource).when(dataStore).getResource(baseLink, DefaultVoidResource.class);

        DefaultVoidResource objectTypeCollectionResource = new DefaultVoidResource(dataStore);
        objectTypeCollectionResource.add(new Link("http://127.0.0.1/objectTypes/search", "search"));
        doReturn(objectTypeCollectionResource).when(dataStore).getResource(new Link("http://127.0.0.1/objectTypes", "dap:objectTypes"), DefaultVoidResource.class);

        DefaultVoidResource objectTypeSearchResource = new DefaultVoidResource(dataStore);
        objectTypeSearchResource.add(new Link("http://127.0.0.1/objectTypes/search/findAllEnabled", "dap:findAllEnabled"));
        doReturn(objectTypeSearchResource).when(dataStore).getResource(new Link("http://127.0.0.1/objectTypes/search", "search"), DefaultVoidResource.class);

        DefaultObjectTypeList objectTypeListToReturn = new DefaultObjectTypeList(dataStore);
        doReturn(objectTypeListToReturn).when(dataStore).getResource(eq(new Link("http://127.0.0.1/objectTypes/search/findAllEnabled", "dap:findAllEnabled")), eq(DefaultObjectTypeList.class), any());

        ObjectTypeList allEnabledDevicesResult = objectTypeActions.getAllEnabled(ObjectTypes.criteria().pageSize(100).orderByName().descending());
        assertSame(objectTypeListToReturn, allEnabledDevicesResult);
        verify(dataStore).getResource(baseLink, DefaultVoidResource.class);
        verify(dataStore).getResource(new Link("http://127.0.0.1/objectTypes", "dap:objectTypes"), DefaultVoidResource.class);
        verify(dataStore).getResource(new Link("http://127.0.0.1/objectTypes/search", "search"), DefaultVoidResource.class);
        verify(dataStore).getResource(eq(new Link("http://127.0.0.1/objectTypes/search/findAllEnabled", "dap:findAllEnabled")), eq(DefaultObjectTypeList.class), any());
        verifyNoMoreInteractions(dataStore);
    }

}