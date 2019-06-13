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

package ru.icbcom.aistdapsdkjava.impl.physicalstructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.Link;
import ru.icbcom.aistdapsdkjava.api.exception.AistDapBackendException;
import ru.icbcom.aistdapsdkjava.api.exception.LinkNotFoundException;
import ru.icbcom.aistdapsdkjava.api.physicalstructure.PhysicalStructureObject;
import ru.icbcom.aistdapsdkjava.api.physicalstructure.PhysicalStructureObjectCriteria;
import ru.icbcom.aistdapsdkjava.api.physicalstructure.PhysicalStructureObjectList;
import ru.icbcom.aistdapsdkjava.api.physicalstructure.PhysicalStructureObjects;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.error.DefaultError;
import ru.icbcom.aistdapsdkjava.impl.resource.DefaultVoidResource;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultPhysicalStructureObjectActionsTest {

    @Mock
    DataStore dataStore;

    private Link baseLink;
    private DefaultPhysicalStructureObjectActions physicalStructureObjectActions;

    @BeforeEach
    void setup() {
        baseLink = new Link("http://127.0.0.1/");
        physicalStructureObjectActions = new DefaultPhysicalStructureObjectActions(baseLink, dataStore);
    }

    @Test
    void getAllInRootShouldWorkProperly() {
        DefaultVoidResource rootVoidResource = new DefaultVoidResource(dataStore);
        rootVoidResource.add(new Link("http://127.0.0.1:8080/physicalStructure", "dap:physicalStructure"));
        doReturn(rootVoidResource).when(dataStore).getResource(baseLink, DefaultVoidResource.class);

        DefaultPhysicalStructureObjectList physicalStructureObjectListToReturn = new DefaultPhysicalStructureObjectList(dataStore);
        doReturn(physicalStructureObjectListToReturn).when(dataStore).getResource(any(), eq(DefaultPhysicalStructureObjectList.class), any());

        PhysicalStructureObjectList getAllResult = physicalStructureObjectActions.getAllInRoot();

        assertSame(physicalStructureObjectListToReturn, getAllResult);
        verify(dataStore).getResource(baseLink, DefaultVoidResource.class);
        verify(dataStore).getResource(eq(new Link("http://127.0.0.1:8080/physicalStructure", "dap:physicalStructure")), eq(DefaultPhysicalStructureObjectList.class), eq(PhysicalStructureObjects.criteria()));
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void getAllInRootWithCriteriaShouldWorkProperly() {
        DefaultVoidResource rootVoidResource = new DefaultVoidResource(dataStore);
        rootVoidResource.add(new Link("http://127.0.0.1:8080/physicalStructure", "dap:physicalStructure"));
        doReturn(rootVoidResource).when(dataStore).getResource(baseLink, DefaultVoidResource.class);

        DefaultPhysicalStructureObjectList physicalStructureObjectListToReturn = new DefaultPhysicalStructureObjectList(dataStore);
        doReturn(physicalStructureObjectListToReturn).when(dataStore).getResource(any(), eq(DefaultPhysicalStructureObjectList.class), any());

        PhysicalStructureObjectCriteria criteria = PhysicalStructureObjects.criteria()
                .pageSize(100)
                .orderByName().descending();
        PhysicalStructureObjectList getAllResult = physicalStructureObjectActions.getAllInRoot(criteria);

        assertSame(physicalStructureObjectListToReturn, getAllResult);
        verify(dataStore).getResource(baseLink, DefaultVoidResource.class);
        verify(dataStore).getResource(eq(new Link("http://127.0.0.1:8080/physicalStructure", "dap:physicalStructure")), eq(DefaultPhysicalStructureObjectList.class), eq(criteria));
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void getByIdShouldWorkProperly() {
        DefaultVoidResource rootVoidResource = new DefaultVoidResource(dataStore);
        rootVoidResource.add(new Link("http://127.0.0.1:8080/physicalStructure", "dap:physicalStructure"));
        doReturn(rootVoidResource).when(dataStore).getResource(baseLink, DefaultVoidResource.class);

        DefaultPhysicalStructureObject physicalStructureObjectToReturn = new DefaultPhysicalStructureObject(dataStore);
        doReturn(physicalStructureObjectToReturn).when(dataStore).getResource(new Link("http://127.0.0.1:8080/physicalStructure/1", "dap:physicalStructure"), DefaultPhysicalStructureObject.class);

        Optional<PhysicalStructureObject> physicalStructureObjectOptional = physicalStructureObjectActions.getById(1L);
        assertTrue(physicalStructureObjectOptional.isPresent());
        assertSame(physicalStructureObjectToReturn, physicalStructureObjectOptional.get());

        verify(dataStore).getResource(baseLink, DefaultVoidResource.class);
        verify(dataStore).getResource(new Link("http://127.0.0.1:8080/physicalStructure/1", "dap:physicalStructure"), DefaultPhysicalStructureObject.class);
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void getByIdExceptionShouldBeThrownWhenThereIsNoLink() {
        DefaultVoidResource rootVoidResource = new DefaultVoidResource(dataStore);
        doReturn(rootVoidResource).when(dataStore).getResource(baseLink, DefaultVoidResource.class);

        LinkNotFoundException exception = assertThrows(LinkNotFoundException.class, () -> physicalStructureObjectActions.getById(1L));
        assertThat(exception, allOf(
                hasProperty("message", is("Link with relation 'dap:physicalStructure' was not found in resource with href 'http://127.0.0.1/'")),
                hasProperty("resourceHref", is("http://127.0.0.1/")),
                hasProperty("rel", is("dap:physicalStructure"))
        ));
        verify(dataStore).getResource(baseLink, DefaultVoidResource.class);
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void getByIdShouldThrowExceptionWhenStatusCodeIsNot404() {
        DefaultVoidResource rootVoidResource = new DefaultVoidResource(dataStore);
        rootVoidResource.add(new Link("http://127.0.0.1:8080/physicalStructure", "dap:physicalStructure"));
        doReturn(rootVoidResource).when(dataStore).getResource(baseLink, DefaultVoidResource.class);

        DefaultError defaultError = new DefaultError();
        defaultError.setStatus(406);
        AistDapBackendException exceptionToThrow = new AistDapBackendException(defaultError);
        doThrow(exceptionToThrow).when(dataStore).getResource(new Link("http://127.0.0.1:8080/physicalStructure/1", "dap:physicalStructure"), DefaultPhysicalStructureObject.class);

        AistDapBackendException exception = assertThrows(AistDapBackendException.class, () -> physicalStructureObjectActions.getById(1L));
        assertSame(exceptionToThrow, exception);

        verify(dataStore).getResource(baseLink, DefaultVoidResource.class);
        verify(dataStore).getResource(new Link("http://127.0.0.1:8080/physicalStructure/1", "dap:physicalStructure"), DefaultPhysicalStructureObject.class);
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void createPhysicalStructureObjectInRoot() {
        DefaultVoidResource rootVoidResource = new DefaultVoidResource(dataStore);
        rootVoidResource.add(new Link("http://127.0.0.1:8080/physicalStructure", "dap:physicalStructure"));
        doReturn(rootVoidResource).when(dataStore).getResource(baseLink, DefaultVoidResource.class);

        PhysicalStructureObject physicalStructureObjectToCreate = new DefaultPhysicalStructureObject(dataStore);

        DefaultPhysicalStructureObject physicalStructureObjectCreationResult = new DefaultPhysicalStructureObject(dataStore);
        when(dataStore.create(new Link("http://127.0.0.1:8080/physicalStructure", "dap:physicalStructure"), physicalStructureObjectToCreate)).thenReturn(physicalStructureObjectCreationResult);

        PhysicalStructureObject createdPhysicalStructureObject = physicalStructureObjectActions.createInRoot(physicalStructureObjectToCreate);
        assertSame(physicalStructureObjectCreationResult, createdPhysicalStructureObject);

        verify(dataStore).getResource(baseLink, DefaultVoidResource.class);
        verify(dataStore).create(new Link("http://127.0.0.1:8080/physicalStructure", "dap:physicalStructure"), physicalStructureObjectToCreate);
        verifyNoMoreInteractions(dataStore);
    }

}