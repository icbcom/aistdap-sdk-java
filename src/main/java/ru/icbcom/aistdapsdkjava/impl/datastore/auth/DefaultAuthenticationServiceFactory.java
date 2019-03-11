package ru.icbcom.aistdapsdkjava.impl.datastore.auth;

import org.springframework.web.client.RestTemplate;
import ru.icbcom.aistdapsdkjava.impl.datastore.auth.key.AuthenticationKey;
import ru.icbcom.aistdapsdkjava.impl.datastore.auth.service.AuthenticationService;
import ru.icbcom.aistdapsdkjava.impl.datastore.auth.service.DefaultAuthenticationService;

public class DefaultAuthenticationServiceFactory implements AuthenticationServiceFactory {
    @Override
    public AuthenticationService create(String baseUrl, AuthenticationKey authentication, RestTemplate restTemplate) {
        return new DefaultAuthenticationService(baseUrl, authentication, restTemplate);
    }
}
