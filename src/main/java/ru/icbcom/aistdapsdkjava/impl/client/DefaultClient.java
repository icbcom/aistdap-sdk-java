package ru.icbcom.aistdapsdkjava.impl.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import ru.icbcom.aistdapsdkjava.api.client.Client;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectTypeCriteria;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectTypeList;
import ru.icbcom.aistdapsdkjava.api.resource.Resource;
import ru.icbcom.aistdapsdkjava.api.resource.ResourceFactory;
import ru.icbcom.aistdapsdkjava.api.resource.VoidResource;
import ru.icbcom.aistdapsdkjava.impl.auth.AuthenticationRequest;
import ru.icbcom.aistdapsdkjava.impl.auth.AuthenticationResponse;
import ru.icbcom.aistdapsdkjava.impl.auth.DefaultAuthenticationRequest;
import ru.icbcom.aistdapsdkjava.impl.auth.DefaultAuthenticationResponse;
import ru.icbcom.aistdapsdkjava.impl.objectmapper.ObjectMappers;
import ru.icbcom.aistdapsdkjava.impl.resource.DefaultResourceFactory;
import ru.icbcom.aistdapsdkjava.impl.resource.DefaultVoidResource;

@Slf4j
public class DefaultClient implements Client {

    private final String baseUrl;
    private final String login;
    private final String password;
    private final RestTemplate restTemplate;

    private final ResourceFactory resourceFactory;

    private AuthenticationResponse authenticationResponse;

    public DefaultClient(String baseUrl, String login, String password) {
        this.baseUrl = baseUrl;
        this.login = login;
        this.password = password;
        this.restTemplate = newRestTemplateInstance();
        this.resourceFactory = new DefaultResourceFactory();
    }

    private RestTemplate newRestTemplateInstance() {
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.setPrettyPrint(true);
        messageConverter.setObjectMapper(ObjectMappers.create());

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().removeIf(m -> m.getClass().getName().equals(MappingJackson2HttpMessageConverter.class.getName()));
        restTemplate.getMessageConverters().add(messageConverter);

        return restTemplate;
    }

    @Override
    public <T extends Resource> T instantiate(Class<T> clazz) {
        return resourceFactory.instantiate(clazz);
    }

    @Override
    public ObjectTypeList getObjectTypes() {
        return null;
    }

    @Override
    @SneakyThrows
    public ObjectTypeList getObjectTypes(ObjectTypeCriteria criteria) {
        // Реализовать получение типов объектов в соответствии с заданной спецификацией.

        // Если вход пользователся в систему не был осуществлен, то производим вход пользователя в систему.
        if (authenticationResponse == null) {
            log.info("Not authenticated. Trying to authenticate...");

            // Запрос корневой страницы.
            VoidResource rootResource = restTemplate.getForObject(baseUrl, DefaultVoidResource.class);
            Link loginLink = rootResource.getLink("dap:login").orElseThrow(() -> new RuntimeException("Login loginLink not found"));
            log.info(loginLink.toString());

            // Выполнение аутентификации.
            AuthenticationRequest authenticationRequest = new DefaultAuthenticationRequest(login, password);

            authenticationResponse = restTemplate.postForObject(loginLink.getHref(), authenticationRequest, DefaultAuthenticationResponse.class);
            log.info(authenticationResponse.toString());
        } else {
            log.info("Already authenticated...");
        }

        // Выполнения получения списка типов объектов.
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + authenticationResponse.getAccessToken());
            return execution.execute(request, body);
        });

        // Получение корневой страницы.
        VoidResource rootResource = restTemplate.getForObject(baseUrl, DefaultVoidResource.class);
        Link objectTypesLink = rootResource.getLink("dap:objectTypes").orElseThrow();

        System.out.println(objectTypesLink);

        // Получение списка тиипов объектов.
        String forObject = restTemplate.getForObject(objectTypesLink.expand().getHref(), String.class);
        System.out.println(forObject);


        return null;
    }

}
