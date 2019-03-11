package ru.icbcom.aistdapsdkjava.impl.datastore;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.Link;
import org.springframework.web.client.RestTemplate;
import ru.icbcom.aistdapsdkjava.api.exception.AistDapSdkException;
import ru.icbcom.aistdapsdkjava.api.query.Criteria;
import ru.icbcom.aistdapsdkjava.api.resource.VoidResource;
import ru.icbcom.aistdapsdkjava.impl.datastore.auth.AuthenticationServiceFactory;
import ru.icbcom.aistdapsdkjava.impl.datastore.auth.service.AuthenticationService;
import ru.icbcom.aistdapsdkjava.impl.datastore.objectmapper.ObjectMapperFactory;
import ru.icbcom.aistdapsdkjava.impl.datastore.resttemplate.RestTemplateFactory;
import ru.icbcom.aistdapsdkjava.impl.query.DefaultCriteria;
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