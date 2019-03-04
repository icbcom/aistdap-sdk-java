package ru.icbcom.aistdapsdkjava.impl.datastore;

import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Link;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import ru.icbcom.aistdapsdkjava.api.resource.Resource;
import ru.icbcom.aistdapsdkjava.api.resource.VoidResource;
import ru.icbcom.aistdapsdkjava.impl.auth.*;
import ru.icbcom.aistdapsdkjava.impl.mapper.ObjectMappers;
import ru.icbcom.aistdapsdkjava.impl.resource.DefaultVoidResource;

@Slf4j
public class DefaultDataStore implements DataStore {

    private final String baseUrl;
    private final Authentication authentication;
    private final RestTemplate restTemplate;

    private AuthenticationResponse authenticationResponse;

    public DefaultDataStore(String baseUrl, Authentication authentication) {
        this.baseUrl = baseUrl;
        this.authentication = authentication;
        this.restTemplate = createRestTemplate();
    }

    private RestTemplate createRestTemplate() {
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.setPrettyPrint(true);
        messageConverter.setObjectMapper(ObjectMappers.create(this));

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().removeIf(m -> m.getClass().getName().equals(MappingJackson2HttpMessageConverter.class.getName()));
        restTemplate.getMessageConverters().add(messageConverter);

        restTemplate.getInterceptors().add((request, body, execution) -> {
            if (authenticationResponse != null) {
                request.getHeaders().add("Authorization", "Bearer " + authenticationResponse.getAccessToken());
            }
            return execution.execute(request, body);
        });

        return restTemplate;
    }

    @Override
    public <T extends Resource> T getResource(String href, Class<T> clazz) {
        processAuthentication();
        return restTemplate.getForObject(href, clazz);
    }

    private void processAuthentication() {
        if (authenticationResponse == null) {
            log.info("Not authenticated. Trying to authenticate...");

            // Запрос корневой страницы.
            VoidResource rootResource = restTemplate.getForObject(baseUrl, DefaultVoidResource.class);
            Link loginLink = rootResource.getLink("dap:login").orElseThrow(() -> new RuntimeException("Login loginLink not found"));
            log.info(loginLink.toString());

            // Выполнение аутентификации.
            AuthenticationRequest authenticationRequest = new DefaultAuthenticationRequest(authentication.getLogin(), authentication.getPassword());

            authenticationResponse = restTemplate.postForObject(loginLink.getHref(), authenticationRequest, DefaultAuthenticationResponse.class);
            log.info(authenticationResponse.toString());
        } else {
            log.info("Already authenticated...");
        }
    }


}
