package ru.icbcom.aistdapsdkjava.impl.datastore.auth;

import org.springframework.web.client.RestTemplate;
import ru.icbcom.aistdapsdkjava.impl.datastore.auth.key.AuthenticationKey;
import ru.icbcom.aistdapsdkjava.impl.datastore.auth.service.AuthenticationService;

public interface AuthenticationServiceFactory {
    AuthenticationService create(String baseUrl, AuthenticationKey authentication, RestTemplate restTemplate);
}
