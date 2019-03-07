package ru.icbcom.aistdapsdkjava.impl.datastore.resttemplate;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import ru.icbcom.aistdapsdkjava.impl.datastore.auth.controller.AuthenticationService;

import java.io.IOException;

// TODO: Протестировать данный класс.

@RequiredArgsConstructor
public class AuthorizationClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {
    private final AuthenticationService authenticationService;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        if (authenticationService.isAuthenticated()) {
            request.getHeaders().add("Authorization", "Bearer " + authenticationService.getTokens().getAccessToken());
        }
        return execution.execute(request, body);
    }
}
