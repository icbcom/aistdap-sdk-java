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

package ru.icbcom.aistdapsdkjava.impl.datastore;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.icbcom.aistdapsdkjava.api.exception.AistDapSdkException;
import ru.icbcom.aistdapsdkjava.api.query.Criteria;
import ru.icbcom.aistdapsdkjava.api.resource.Deletable;
import ru.icbcom.aistdapsdkjava.api.resource.Savable;
import ru.icbcom.aistdapsdkjava.api.resource.VoidResource;
import ru.icbcom.aistdapsdkjava.impl.datastore.auth.AuthenticationServiceFactory;
import ru.icbcom.aistdapsdkjava.impl.datastore.auth.service.AuthenticationService;
import ru.icbcom.aistdapsdkjava.impl.datastore.objectmapper.ObjectMapperFactory;
import ru.icbcom.aistdapsdkjava.impl.datastore.resttemplate.RestTemplateFactory;
import ru.icbcom.aistdapsdkjava.impl.query.DefaultCriteria;
import ru.icbcom.aistdapsdkjava.impl.resource.AbstractResource;
import ru.icbcom.aistdapsdkjava.impl.resource.DefaultVoidResource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultDataStoreTest {

    @Mock
    ObjectMapperFactory objectMapperFactory;

    @Mock
    AuthenticationServiceFactory authenticationServiceFactory;

    @Mock
    RestTemplateFactory restTemplateFactory;

    @Mock
    ObjectMapper objectMapper;

    @Mock
    AuthenticationService authenticationService;

    @Mock
    RestTemplate restTemplate;

    private DataStore dataStore;

    @BeforeEach
    void setup() {
        when(objectMapperFactory.create(any())).thenReturn(objectMapper);
        when(authenticationServiceFactory.create(any(), any(), any())).thenReturn(authenticationService);
        when(restTemplateFactory.create(any())).thenReturn(restTemplate);
        when(restTemplate.getInterceptors()).thenReturn(new ArrayList<>());

        dataStore = new DefaultDataStore("http://127.0.0.1:8080", "someLogin",
                "somePassword", objectMapperFactory, authenticationServiceFactory, restTemplateFactory);
    }

    @Test
    void getResourceWithCriteriaShouldWorkProperly() {
        Link link = new Link("http://127.0.0.1/resource{?page,size,sort}", "someRel");
        TestCriteria testCriteria = new DefaultTestCriteria().pageNumber(2).pageSize(100).orderBySomeField().descending();

        DefaultVoidResource resourceToReturn = new DefaultVoidResource(dataStore);
        when(restTemplate.getForObject("http://127.0.0.1/resource?page=2&size=100&sort=someField,desc", VoidResource.class)).thenReturn(resourceToReturn);
        VoidResource resource = dataStore.getResource(link, VoidResource.class, testCriteria);

        assertSame(resourceToReturn, resource);

        InOrder inOrder = inOrder(authenticationService, restTemplate);
        inOrder.verify(authenticationService).ensureAuthentication();
        inOrder.verify(restTemplate).getForObject("http://127.0.0.1/resource?page=2&size=100&sort=someField,desc", VoidResource.class);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void getResourceWithCriteriaShouldThrowExceptionIfRestTemplateReturnedNull() {
        Link link = new Link("http://127.0.0.1/resource{?page,size,sort}", "someRel");
        TestCriteria testCriteria = new DefaultTestCriteria().pageNumber(2).pageSize(100).orderBySomeField().descending();

        DefaultVoidResource resourceToReturn = new DefaultVoidResource(dataStore);
        when(restTemplate.getForObject("http://127.0.0.1/resource?page=2&size=100&sort=someField,desc", VoidResource.class)).thenReturn(null);

        AistDapSdkException exception = assertThrows(AistDapSdkException.class, () -> dataStore.getResource(link, VoidResource.class, testCriteria));
        assertEquals("Server returned null for requested resource 'http://127.0.0.1/resource?page=2&size=100&sort=someField,desc'", exception.getMessage());

        InOrder inOrder = inOrder(authenticationService, restTemplate);
        inOrder.verify(authenticationService).ensureAuthentication();
        inOrder.verify(restTemplate).getForObject("http://127.0.0.1/resource?page=2&size=100&sort=someField,desc", VoidResource.class);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void getResourceWithoutCriteriaShouldWorkProperly() {
        Link link = new Link("http://127.0.0.1/resource{?page,size,sort}", "someRel");

        DefaultVoidResource resourceToReturn = new DefaultVoidResource(dataStore);
        when(restTemplate.getForObject("http://127.0.0.1/resource", VoidResource.class)).thenReturn(resourceToReturn);

        VoidResource resource = dataStore.getResource(link, VoidResource.class);

        assertSame(resourceToReturn, resource);

        InOrder inOrder = inOrder(authenticationService, restTemplate);
        inOrder.verify(authenticationService).ensureAuthentication();
        inOrder.verify(restTemplate).getForObject("http://127.0.0.1/resource", VoidResource.class);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void createShouldWorkProperly() {
        Link link = new Link("http://127.0.0.1/resource{?page,size,sort}", "someRel");

        DefaultVoidResource objectToCreate = new DefaultVoidResource(dataStore);

        DefaultVoidResource resourceToReturn = new DefaultVoidResource(dataStore);
        when(restTemplate.postForObject("http://127.0.0.1/resource", objectToCreate, DefaultVoidResource.class)).thenReturn(resourceToReturn);

        DefaultVoidResource createdResource = dataStore.create(link, objectToCreate);
        assertSame(resourceToReturn, createdResource);

        InOrder inOrder = inOrder(authenticationService, restTemplate);
        inOrder.verify(authenticationService).ensureAuthentication();
        inOrder.verify(restTemplate).postForObject("http://127.0.0.1/resource", objectToCreate, DefaultVoidResource.class);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void saveShouldWorkProperly() {
        SavableDeletableVoidResource resourceToSave = new SavableDeletableVoidResource(dataStore);
        resourceToSave.add(new Link("http://127.0.0.1/resource/1", "self"));

        SavableDeletableVoidResource resourceToReturn = new SavableDeletableVoidResource(dataStore);
        when(restTemplate.exchange("http://127.0.0.1/resource/1", HttpMethod.PUT, new HttpEntity<>(resourceToSave), SavableDeletableVoidResource.class))
                .thenReturn(new ResponseEntity<>(resourceToReturn, HttpStatus.OK));

        SavableDeletableVoidResource saveResult = dataStore.save(resourceToSave);
        assertSame(resourceToReturn, saveResult);

        InOrder inOrder = inOrder(authenticationService, restTemplate);
        inOrder.verify(authenticationService).ensureAuthentication();
        inOrder.verify(restTemplate).exchange("http://127.0.0.1/resource/1", HttpMethod.PUT, new HttpEntity<>(resourceToSave), SavableDeletableVoidResource.class);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void deleteShouldWorkProperly() {
        SavableDeletableVoidResource resourceToDelete = new SavableDeletableVoidResource(dataStore);
        resourceToDelete.add(new Link("http://127.0.0.1/resource/1", "self"));

        dataStore.delete(resourceToDelete);

        InOrder inOrder = inOrder(authenticationService, restTemplate);
        inOrder.verify(authenticationService).ensureAuthentication();
        inOrder.verify(restTemplate).delete("http://127.0.0.1/resource/1");
        inOrder.verifyNoMoreInteractions();
    }

    /*
     HttpEntity<T> httpEntity = new HttpEntity<>(resource);
        ResponseEntity<? extends Resource> responseEntity = restTemplate.exchange(expandedHref, HttpMethod.PUT, httpEntity, resource.getClass());
     */

    /*
        <T extends Resource> T create(Link parentLink, T resource);

    <T extends Resource & Savable> T save(T resource);

    <T extends Resource> void delete(T resource);
     */

    @Test
    void callMethodShouldWorkProperly() {
        Link link = new Link("http://127.0.0.1/resource{?page,size,sort}", "someRel");

        dataStore.callMethod(link);

        InOrder inOrder = inOrder(authenticationService, restTemplate);
        inOrder.verify(authenticationService).ensureAuthentication();
        inOrder.verify(restTemplate).postForObject("http://127.0.0.1/resource", null, Void.class);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void callMethodWithArgumentShouldWorkProperly() {
        Link link = new Link("http://127.0.0.1/resource{?page,size,sort}", "someRel");

        SavableDeletableVoidResource methodArgument = new SavableDeletableVoidResource(dataStore);
        dataStore.callMethod(link, methodArgument);

        InOrder inOrder = inOrder(authenticationService, restTemplate);
        inOrder.verify(authenticationService).ensureAuthentication();
        inOrder.verify(restTemplate).postForObject("http://127.0.0.1/resource", methodArgument, Void.class);
        inOrder.verifyNoMoreInteractions();
    }

    @EqualsAndHashCode(callSuper = false)
    private static class SavableDeletableVoidResource extends AbstractResource implements VoidResource, Savable, Deletable {
        public SavableDeletableVoidResource(@JacksonInject DataStore dataStore) {
            super(dataStore);
        }

        @Override
        public void save() {
            throw new IllegalStateException("Not implemented");
        }

        @Override
        public void delete() {
            throw new IllegalStateException("Not implemented");

        }
    }

    private interface TestCriteria extends Criteria<TestCriteria> {
        TestCriteria orderBySomeField();
    }

    private static class DefaultTestCriteria extends DefaultCriteria<TestCriteria> implements TestCriteria {
        @Override
        public TestCriteria orderBySomeField() {
            return orderBy("someField");
        }
    }

}