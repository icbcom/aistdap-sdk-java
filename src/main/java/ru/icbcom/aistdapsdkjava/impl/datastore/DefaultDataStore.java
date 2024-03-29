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

import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import ru.icbcom.aistdapsdkjava.api.exception.AistDapSdkException;
import ru.icbcom.aistdapsdkjava.api.query.Criteria;
import ru.icbcom.aistdapsdkjava.api.resource.CollectionResource;
import ru.icbcom.aistdapsdkjava.api.resource.Deletable;
import ru.icbcom.aistdapsdkjava.api.resource.Resource;
import ru.icbcom.aistdapsdkjava.api.resource.Savable;
import ru.icbcom.aistdapsdkjava.impl.datastore.auth.AuthenticationServiceFactory;
import ru.icbcom.aistdapsdkjava.impl.datastore.auth.key.DefaultAuthenticationKey;
import ru.icbcom.aistdapsdkjava.impl.datastore.auth.service.AuthenticationService;
import ru.icbcom.aistdapsdkjava.impl.datastore.linkexpander.DefaultCriteriaLinkExpander;
import ru.icbcom.aistdapsdkjava.impl.datastore.objectmapper.ObjectMapperFactory;
import ru.icbcom.aistdapsdkjava.impl.datastore.resttemplate.AuthorizationClientHttpRequestInterceptor;
import ru.icbcom.aistdapsdkjava.impl.datastore.resttemplate.RestTemplateFactory;
import ru.icbcom.aistdapsdkjava.impl.query.DefaultCriteria;
import ru.icbcom.aistdapsdkjava.impl.query.EmptyCriteria;
import ru.icbcom.aistdapsdkjava.impl.resource.AbstractResource;

// TODO: Протестиировать реализацию новых методов данного класса.

public class DefaultDataStore implements DataStore {

    private static final String CANNOT_DELETE_NO_SELF_LINK_MSG = "This resource does not have a self link, therefore it cannot be deleted.";
    private static final String DEFAULT_CRITERIA_MSG = "The " + DefaultDataStore.class.getName() + " implementation only functions with " + DefaultCriteria.class.getName() + " instances.";
    private static final String CANNOT_SAVE_NO_SELF_LINK_MSG = "'save' may only be called on objects that have already been persisted and have an existing self link.";

    private final DefaultCriteriaLinkExpander linkExpander;
    private final AuthenticationService authenticationService;
    private final RestTemplate restTemplate;

    public DefaultDataStore(String baseUrl, String login, String password, ObjectMapperFactory objectMapperFactory,
                            AuthenticationServiceFactory authenticationServiceFactory, RestTemplateFactory restTemplateFactory) {
        this.linkExpander = new DefaultCriteriaLinkExpander();
        this.restTemplate = restTemplateFactory.create(objectMapperFactory.create(this));
        this.authenticationService = authenticationServiceFactory.create(baseUrl, new DefaultAuthenticationKey(login, password), this.restTemplate);

        registerRestTemplateAuthorizationInterceptor(this.restTemplate, this.authenticationService);
    }

    private void registerRestTemplateAuthorizationInterceptor(RestTemplate restTemplate, AuthenticationService authenticationService) {
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

    @Override
    public <T extends Resource> T create(Link parentLink, T resource) {
        Assert.notNull(parentLink, "parentLink argument cannot be null");
        Assert.notNull(resource, "resource argument cannot be null");
        Assert.isInstanceOf(AbstractResource.class, resource);
        Assert.isTrue(!CollectionResource.class.isAssignableFrom(resource.getClass()), "collections cannot be persisted");

        authenticationService.ensureAuthentication();
        Resource createdResource = restTemplate.postForObject(parentLink.expand().getHref(), resource, resource.getClass());

        return (T) createdResource;
    }

    @Override
    public <T extends Resource & Savable> T save(T resource) {
        Assert.notNull(resource, "resource argument cannot be null");
        Assert.isInstanceOf(AbstractResource.class, resource, "resource argument must be an AbstractResource");
        Assert.isTrue(!CollectionResource.class.isAssignableFrom(resource.getClass()), "collections cannot be persisted");

        Link selfLink = resource.getLink("self").orElseThrow(() -> new IllegalArgumentException(CANNOT_SAVE_NO_SELF_LINK_MSG));
        String expandedHref = selfLink.expand().getHref();

        HttpEntity<T> httpEntity = new HttpEntity<>(resource);
        authenticationService.ensureAuthentication();
        ResponseEntity<? extends Resource> responseEntity = restTemplate.exchange(expandedHref, HttpMethod.PUT, httpEntity, resource.getClass());

        return (T) responseEntity.getBody();
    }

    @Override
    public <T extends Resource & Deletable> void delete(T resource) {
        Assert.notNull(resource, "resource argument cannot be null");
        Assert.isInstanceOf(AbstractResource.class, resource, "resource argument must be an AbstractResource");
        Assert.isTrue(!CollectionResource.class.isAssignableFrom(resource.getClass()), "collections cannot be deleted");

        Link selfLink = resource.getLink("self").orElseThrow(() -> new IllegalArgumentException(CANNOT_DELETE_NO_SELF_LINK_MSG));
        String expandedHref = selfLink.expand().getHref();
        authenticationService.ensureAuthentication();
        restTemplate.delete(expandedHref);
    }

    @Override
    public void callMethod(Link link) {
        Assert.notNull(link, "link argument cannot be null");
        authenticationService.ensureAuthentication();
        restTemplate.postForObject(link.expand().getHref(), null, Void.class);
    }

    @Override
    public <T extends Resource> void callMethod(Link link, T methodArgumentResource) {
        Assert.notNull(link, "link argument cannot be null");
        Assert.notNull(methodArgumentResource, "argumentResource argument cannot be null");
        Assert.isInstanceOf(AbstractResource.class, methodArgumentResource, "argumentResource argument must be an AbstractResource");
        authenticationService.ensureAuthentication();
        restTemplate.postForObject(link.expand().getHref(), methodArgumentResource, Void.class);
    }

}
