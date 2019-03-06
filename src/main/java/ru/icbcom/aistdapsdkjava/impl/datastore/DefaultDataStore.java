package ru.icbcom.aistdapsdkjava.impl.datastore;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.hateoas.Link;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.Assert;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import ru.icbcom.aistdapsdkjava.api.query.Criteria;
import ru.icbcom.aistdapsdkjava.api.resource.Resource;
import ru.icbcom.aistdapsdkjava.impl.auth.AuthenticationKey;
import ru.icbcom.aistdapsdkjava.impl.auth.controller.AuthenticationController;
import ru.icbcom.aistdapsdkjava.impl.auth.controller.DefaultAuthenticationController;
import ru.icbcom.aistdapsdkjava.impl.mapper.ObjectMappers;
import ru.icbcom.aistdapsdkjava.impl.query.DefaultCriteria;
import ru.icbcom.aistdapsdkjava.impl.query.EmptyCriteria;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

// TODO: Протестиировать данный класс.

@Slf4j
public class DefaultDataStore implements DataStore {

    private static final String DEFAULT_CRITERIA_MSG = "The " + DefaultDataStore.class.getName()
            + " implementation only functions with " + DefaultCriteria.class.getName() + " instances.";

    private final String baseUrl;
    private final AuthenticationKey authentication;
    private final RestTemplate restTemplate;
    private final AuthenticationController authenticationController;

    public DefaultDataStore(String baseUrl, AuthenticationKey authenticationKey) {
        this.baseUrl = baseUrl;
        this.authentication = authenticationKey;
        this.restTemplate = RestTemplateFactory.create(this);
        this.authenticationController = new DefaultAuthenticationController(baseUrl, authenticationKey, restTemplate);
//        registerRestTemplateAuthorizationInterceptor();
    }

    private void registerRestTemplateAuthorizationInterceptor() {
        restTemplate.getInterceptors().add((request, body, execution) -> {
            if (authenticationController.isAuthenticated()) {
                request.getHeaders().add("Authorization", "Bearer " + authenticationController.getTokens().getAccessToken());
            }
            return execution.execute(request, body);
        });
    }

    @Override
    public <T extends Resource> T getResource(Link link, Class<T> clazz) {
        return getResource(link, clazz, EmptyCriteria.INSTANCE);
    }

    @Override
    public <T extends Resource, C extends Criteria> T getResource(Link link, Class<T> clazz, C criteria) {
        Assert.isInstanceOf(DefaultCriteria.class, criteria, DEFAULT_CRITERIA_MSG);

        authenticationController.ensureAuthentication();

        Map<String, String> arguments = prepareCriteriaArguments((DefaultCriteria) criteria);
        String expandedHref = link.expand(arguments).getHref();
        return restTemplate.getForObject(expandedHref, clazz);
    }

    private Map<String, String> prepareCriteriaArguments(DefaultCriteria defaultCriteria) {
        Map<String, String> arguments = new HashMap<>();
        if (defaultCriteria.hasPageNumber()) {
            arguments.put("page", defaultCriteria.getPageNumber().toString());
        }
        if (defaultCriteria.hasPageSize()) {
            arguments.put("size", defaultCriteria.getPageSize().toString());
        }
        if (defaultCriteria.hasOrder()) {
            String propertyName = defaultCriteria.getOrder().getPropertyName();
            String order = defaultCriteria.getOrder().isAscending() ? "asc" : "desc";
            arguments.put("sort", propertyName + "," + order);
        }
        return arguments;
    }

    private static class RestTemplateFactory {
        public static RestTemplate create(DataStore dataStore) {
            ObjectMapper objectMapper = ObjectMappers.create(dataStore);

            MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
            messageConverter.setPrettyPrint(true);
            messageConverter.setObjectMapper(objectMapper);

            ClientHttpRequestFactory factory = new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());

//            RestTemplate restTemplate = new RestTemplate(factory);
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().removeIf(m -> m.getClass().getName().equals(MappingJackson2HttpMessageConverter.class.getName()));
            restTemplate.getMessageConverters().add(messageConverter);

            restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler(objectMapper));

//            restTemplate.getInterceptors().add((request, body, execution) -> {
//                ClientHttpResponse response = execution.execute(request, body);
//                String s = IOUtils.toString(response.getBody(), StandardCharsets.UTF_8);
//                System.out.println("Response!");
//                System.out.println(s);
//
//                return response;
//            });

            return restTemplate;
        }
    }

    @RequiredArgsConstructor
    private static class RestTemplateResponseErrorHandler extends DefaultResponseErrorHandler {

        private final ObjectMapper objectMapper;

        @Override
        public void handleError(ClientHttpResponse response) throws IOException {
            InputStream bodyInputStream = response.getBody();
            String s = IOUtils.toString(bodyInputStream, StandardCharsets.UTF_8);
            System.out.println("Response!");
            System.out.println(s);


//            System.out.println(s);
            throw new RuntimeException();
//            ru.icbcom.aistdapsdkjava.api.error.Error error = objectMapper.readValue(response.getBody(), DefaultError.class);
//            throw new AistDapException(error);
        }
    }

}
