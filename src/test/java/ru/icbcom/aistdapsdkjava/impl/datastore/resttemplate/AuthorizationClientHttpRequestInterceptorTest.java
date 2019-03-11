package ru.icbcom.aistdapsdkjava.impl.datastore.resttemplate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import ru.icbcom.aistdapsdkjava.impl.datastore.auth.service.AuthenticationService;
import ru.icbcom.aistdapsdkjava.impl.datastore.auth.tokens.DefaultTokens;

import java.io.IOException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorizationClientHttpRequestInterceptorTest {

    @Mock
    AuthenticationService authenticationService;

    @Mock
    HttpRequest request;

    @Mock
    ClientHttpRequestExecution execution;

    @Mock
    HttpHeaders httpHeaders;

    private byte[] body = {0x01, 0x02, 0x03, 0x04, 0x05};
    private AuthorizationClientHttpRequestInterceptor interceptor;

    @BeforeEach
    void setup() {
        interceptor = new AuthorizationClientHttpRequestInterceptor(authenticationService);
    }

    @Test
    void interceptWhenNotAuthenticatedShouldWorkProperly() throws IOException {
        when(authenticationService.isAuthenticated()).thenReturn(false);

        interceptor.intercept(request, body, execution);

        InOrder inOrder = inOrder(authenticationService, request, execution, httpHeaders);
        inOrder.verify(authenticationService).isAuthenticated();
        inOrder.verify(execution).execute(request, body);
        verifyNoMoreInteractions(authenticationService, request, execution, httpHeaders);
    }

    @Test
    void interceptShouldWorkProperlyWhenAuthenticated() throws IOException {
        when(authenticationService.isAuthenticated()).thenReturn(true);
        when(authenticationService.getTokens()).thenReturn(
                DefaultTokens.builder()
                        .accessToken("someAccessToken")
                        .expiresIn(100)
                        .refreshToken("someRefreshToken")
                        .build());
        when(request.getHeaders()).thenReturn(httpHeaders);

        interceptor.intercept(request, body, execution);

        InOrder inOrder = inOrder(authenticationService, request, execution, httpHeaders);
        inOrder.verify(authenticationService).isAuthenticated();
        inOrder.verify(request).getHeaders();
        inOrder.verify(authenticationService).getTokens();
        inOrder.verify(httpHeaders).add("Authorization", "Bearer someAccessToken");
        inOrder.verify(execution).execute(request, body);
        verifyNoMoreInteractions(authenticationService, request, execution, httpHeaders);
    }

}