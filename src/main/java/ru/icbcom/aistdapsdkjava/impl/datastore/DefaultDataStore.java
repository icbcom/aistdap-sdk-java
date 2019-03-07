package ru.icbcom.aistdapsdkjava.impl.datastore;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Link;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import ru.icbcom.aistdapsdkjava.api.exception.AistDapSdkException;
import ru.icbcom.aistdapsdkjava.api.query.Criteria;
import ru.icbcom.aistdapsdkjava.api.resource.Resource;
import ru.icbcom.aistdapsdkjava.impl.datastore.auth.AuthenticationKey;
import ru.icbcom.aistdapsdkjava.impl.datastore.auth.DefaultAuthenticationKey;
import ru.icbcom.aistdapsdkjava.impl.datastore.auth.controller.AuthenticationService;
import ru.icbcom.aistdapsdkjava.impl.datastore.auth.controller.DefaultAuthenticationService;
import ru.icbcom.aistdapsdkjava.impl.datastore.linkexpander.DefaultCriteriaLinkExpander;
import ru.icbcom.aistdapsdkjava.impl.datastore.resttemplate.AuthorizationClientHttpRequestInterceptor;
import ru.icbcom.aistdapsdkjava.impl.datastore.resttemplate.RestTemplateFactory;
import ru.icbcom.aistdapsdkjava.impl.datastore.objectmapper.ObjectMapperFactory;
import ru.icbcom.aistdapsdkjava.impl.query.DefaultCriteria;
import ru.icbcom.aistdapsdkjava.impl.query.EmptyCriteria;

// TODO: Протестиировать данный класс.

@Slf4j
public class DefaultDataStore implements DataStore {

    private static final String DEFAULT_CRITERIA_MSG = "The " + DefaultDataStore.class.getName()
            + " implementation only functions with " + DefaultCriteria.class.getName() + " instances.";

    private final DefaultCriteriaLinkExpander linkExpander;
    private final AuthenticationService authenticationService;
    private final RestTemplate restTemplate;

    public DefaultDataStore(String baseUrl, String login, String password) {
        this.linkExpander = new DefaultCriteriaLinkExpander();
        this.authenticationService = createAuthenticationController(baseUrl, new DefaultAuthenticationKey(login, password));
        this.restTemplate = RestTemplateFactory.create(ObjectMapperFactory.create(this));
        registerRestTemplateAuthorizationInterceptor();
    }

    private DefaultAuthenticationService createAuthenticationController(String baseUrl, AuthenticationKey authenticationKey) {
        ObjectMapper objectMapper = ObjectMapperFactory.create(this);
        RestTemplate restTemplate = RestTemplateFactory.create(objectMapper);
        return new DefaultAuthenticationService(baseUrl, authenticationKey, restTemplate);
    }

    private void registerRestTemplateAuthorizationInterceptor() {
        restTemplate.getInterceptors().add(new AuthorizationClientHttpRequestInterceptor(authenticationService));
    }

    @Override
    public <T extends Resource> T getResource(Link link, Class<T> clazz) {
        return getResource(link, clazz, EmptyCriteria.INSTANCE);
    }

    @Override
    public <T extends Resource, C extends Criteria> T getResource(Link link, Class<T> clazz, C criteria) {
        Assert.notNull(link, "link argument cannot be null");
        Assert.notNull(link, "criteria argument cannot be null");
        Assert.isInstanceOf(DefaultCriteria.class, criteria, DEFAULT_CRITERIA_MSG);

        authenticationService.ensureAuthentication();
        String expandedHref = linkExpander.expand(link, (DefaultCriteria) criteria);
        T resource = restTemplate.getForObject(expandedHref, clazz);
        if (resource == null) {
            throw new AistDapSdkException(String.format("Server returned null for requested resource '%s'", expandedHref));
        }
        return resource;
    }

}
