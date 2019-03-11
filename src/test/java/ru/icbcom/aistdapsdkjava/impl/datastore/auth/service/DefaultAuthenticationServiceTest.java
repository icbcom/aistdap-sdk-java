package ru.icbcom.aistdapsdkjava.impl.datastore.auth.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.Link;
import org.springframework.web.client.RestTemplate;
import ru.icbcom.aistdapsdkjava.api.exception.AistDapSdkException;
import ru.icbcom.aistdapsdkjava.api.exception.BackendException;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.datastore.auth.key.AuthenticationKey;
import ru.icbcom.aistdapsdkjava.impl.datastore.auth.key.DefaultAuthenticationKey;
import ru.icbcom.aistdapsdkjava.impl.datastore.auth.request.AuthenticationRequest;
import ru.icbcom.aistdapsdkjava.impl.datastore.auth.request.RefreshTokenRequest;
import ru.icbcom.aistdapsdkjava.impl.datastore.auth.response.DefaultAuthenticationResponse;
import ru.icbcom.aistdapsdkjava.impl.error.DefaultError;
import ru.icbcom.aistdapsdkjava.api.exception.LinkNotFoundException;
import ru.icbcom.aistdapsdkjava.impl.resource.DefaultVoidResource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultAuthenticationServiceTest {

    @Mock
    RestTemplate restTemplate;

    @Mock
    DataStore dataStore;

    private AuthenticationService authenticationService;
    private DefaultVoidResource defaultVoidResource;
    private DefaultAuthenticationResponse authenticationResponse;

    @BeforeEach
    void setup() {
        AuthenticationKey authenticationKey = new DefaultAuthenticationKey("someLogin", "somePassword");
        authenticationService = new DefaultAuthenticationService("http://127.0.0.1", authenticationKey, restTemplate);

        defaultVoidResource = new DefaultVoidResource(dataStore);
        defaultVoidResource.add(new Link("http://127.0.0.1/auth/login", "dap:login"));
        defaultVoidResource.add(new Link("http://127.0.0.1/auth/token", "dap:refreshToken"));

        authenticationResponse = new DefaultAuthenticationResponse();
        authenticationResponse.setAccessToken("someAccessToken");
        authenticationResponse.setExpiresIn(100);
        authenticationResponse.setRefreshToken("someRefreshToken");
    }

    @Test
    void justCreatedAuthenticationServiceShouldNotBeAuthenticated() {
        assertFalse(authenticationService.isAuthenticated());
        assertNull(authenticationService.getTokens());
    }

    @Test
    void initialLoginSequenceShouldWorkProperly() {
        when(restTemplate.getForObject("http://127.0.0.1", DefaultVoidResource.class)).thenReturn(defaultVoidResource);
        when(restTemplate.postForObject(eq("http://127.0.0.1/auth/login"), any(AuthenticationRequest.class), eq(DefaultAuthenticationResponse.class))).thenReturn(authenticationResponse);

        authenticationService.ensureAuthentication();

        assertTrue(authenticationService.isAuthenticated());
        assertNotNull(authenticationService.getTokens());
        assertEquals("someAccessToken", authenticationService.getTokens().getAccessToken());
        assertEquals(100, authenticationService.getTokens().getExpiresIn());
        assertEquals("someRefreshToken", authenticationService.getTokens().getRefreshToken());

        InOrder inOrder = inOrder(restTemplate);
        inOrder.verify(restTemplate).getForObject("http://127.0.0.1", DefaultVoidResource.class);

        ArgumentCaptor<AuthenticationRequest> authenticationRequestArgumentCaptor = ArgumentCaptor.forClass(AuthenticationRequest.class);
        inOrder.verify(restTemplate).postForObject(eq("http://127.0.0.1/auth/login"), authenticationRequestArgumentCaptor.capture(), eq(DefaultAuthenticationResponse.class));

        AuthenticationRequest actualAuthenticationRequest = authenticationRequestArgumentCaptor.getValue();
        assertEquals("someLogin", actualAuthenticationRequest.getUsername());
        assertEquals("somePassword", actualAuthenticationRequest.getPassword());

        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void ifThereIsNoDapLoginLinkInRootResourceExceptionShouldBBeThrown() {
        DefaultVoidResource voidResource = new DefaultVoidResource(dataStore);
        voidResource.add(new Link("http://127.0.0.1/auth/token", "dap:refreshToken"));

        when(restTemplate.getForObject("http://127.0.0.1", DefaultVoidResource.class)).thenReturn(voidResource);

        LinkNotFoundException exception = assertThrows(LinkNotFoundException.class, () -> authenticationService.ensureAuthentication());
        assertEquals("Link with relation 'dap:login' was not found in resource with href 'http://127.0.0.1'", exception.getMessage());
        assertEquals("http://127.0.0.1", exception.getResourceHref());
        assertEquals("dap:login", exception.getRel());
    }

    @Test
    void ifThereIsNoDapRefreshTokenLinkInRootResourceExceptionShouldBBeThrown() {
        DefaultVoidResource voidResource = new DefaultVoidResource(dataStore);
        voidResource.add(new Link("http://127.0.0.1/auth/login", "dap:login"));

        when(restTemplate.getForObject("http://127.0.0.1", DefaultVoidResource.class)).thenReturn(voidResource);

        LinkNotFoundException exception = assertThrows(LinkNotFoundException.class, () -> authenticationService.ensureAuthentication());
        assertEquals("Link with relation 'dap:refreshToken' was not found in resource with href 'http://127.0.0.1'", exception.getMessage());
        assertEquals("http://127.0.0.1", exception.getResourceHref());
        assertEquals("dap:refreshToken", exception.getRel());
    }

    @Test
    void ifEmptyRootResourceReturnedExceptionShouldBeThrown() {
        when(restTemplate.getForObject("http://127.0.0.1", DefaultVoidResource.class)).thenReturn(null);

        AistDapSdkException exception = assertThrows(AistDapSdkException.class, () -> authenticationService.ensureAuthentication());
        assertEquals("Received root resource was null", exception.getMessage());
    }

    @Test
    void ifAuthenticationResponseWasNullExceptionShouldBeThrown() {
        when(restTemplate.getForObject("http://127.0.0.1", DefaultVoidResource.class)).thenReturn(defaultVoidResource);
        when(restTemplate.postForObject(eq("http://127.0.0.1/auth/login"), any(AuthenticationRequest.class), eq(DefaultAuthenticationResponse.class))).thenReturn(null);

        AistDapSdkException exception = assertThrows(AistDapSdkException.class, () -> authenticationService.ensureAuthentication());
        assertEquals("Received authentication response was null", exception.getMessage());
    }

    @Test
    void tokenShouldBeRefreshedIfItIsStillValidShouldWorkProperly() {
        when(restTemplate.getForObject("http://127.0.0.1", DefaultVoidResource.class)).thenReturn(defaultVoidResource);
        when(restTemplate.postForObject(eq("http://127.0.0.1/auth/login"), any(AuthenticationRequest.class), eq(DefaultAuthenticationResponse.class))).thenReturn(authenticationResponse);

        authenticationService.ensureAuthentication();
        authenticationService.ensureAuthentication();

        InOrder inOrder = inOrder(restTemplate);
        inOrder.verify(restTemplate).getForObject("http://127.0.0.1", DefaultVoidResource.class);

        ArgumentCaptor<AuthenticationRequest> authenticationRequestArgumentCaptor = ArgumentCaptor.forClass(AuthenticationRequest.class);
        inOrder.verify(restTemplate).postForObject(eq("http://127.0.0.1/auth/login"), authenticationRequestArgumentCaptor.capture(), eq(DefaultAuthenticationResponse.class));

        AuthenticationRequest actualAuthenticationRequest = authenticationRequestArgumentCaptor.getValue();
        assertEquals("someLogin", actualAuthenticationRequest.getUsername());
        assertEquals("somePassword", actualAuthenticationRequest.getPassword());

        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void tokenShouldBeRefreshedProperlyWhenItWillBeExpiredSoon() {
        authenticationResponse.setExpiresIn(50);

        when(restTemplate.getForObject("http://127.0.0.1", DefaultVoidResource.class)).thenReturn(defaultVoidResource);
        when(restTemplate.postForObject(eq("http://127.0.0.1/auth/login"), any(AuthenticationRequest.class), eq(DefaultAuthenticationResponse.class))).thenReturn(authenticationResponse);

        // Initial login.

        authenticationService.ensureAuthentication();

        assertTrue(authenticationService.isAuthenticated());
        assertNotNull(authenticationService.getTokens());
        assertEquals("someAccessToken", authenticationService.getTokens().getAccessToken());
        assertEquals(50, authenticationService.getTokens().getExpiresIn());
        assertEquals("someRefreshToken", authenticationService.getTokens().getRefreshToken());

        InOrder inOrder = inOrder(restTemplate);
        inOrder.verify(restTemplate).getForObject("http://127.0.0.1", DefaultVoidResource.class);

        ArgumentCaptor<AuthenticationRequest> authenticationRequestArgumentCaptor = ArgumentCaptor.forClass(AuthenticationRequest.class);
        inOrder.verify(restTemplate).postForObject(eq("http://127.0.0.1/auth/login"), authenticationRequestArgumentCaptor.capture(), eq(DefaultAuthenticationResponse.class));

        AuthenticationRequest actualAuthenticationRequest = authenticationRequestArgumentCaptor.getValue();
        assertEquals("someLogin", actualAuthenticationRequest.getUsername());
        assertEquals("somePassword", actualAuthenticationRequest.getPassword());

        // Refreshing tokens.

        DefaultAuthenticationResponse refreshAuthenticationResponse = new DefaultAuthenticationResponse();
        refreshAuthenticationResponse.setAccessToken("refreshedAccessToken");
        refreshAuthenticationResponse.setExpiresIn(100);
        refreshAuthenticationResponse.setRefreshToken("refreshedRefreshToken");

        when(restTemplate.postForObject(eq("http://127.0.0.1/auth/token"), any(RefreshTokenRequest.class), eq(DefaultAuthenticationResponse.class))).thenReturn(refreshAuthenticationResponse);
        authenticationService.ensureAuthentication();

        assertTrue(authenticationService.isAuthenticated());
        assertNotNull(authenticationService.getTokens());
        assertEquals("refreshedAccessToken", authenticationService.getTokens().getAccessToken());
        assertEquals(100, authenticationService.getTokens().getExpiresIn());
        assertEquals("refreshedRefreshToken", authenticationService.getTokens().getRefreshToken());

        ArgumentCaptor<RefreshTokenRequest> refreshTokenRequestArgumentCaptor = ArgumentCaptor.forClass(RefreshTokenRequest.class);
        inOrder.verify(restTemplate).postForObject(eq("http://127.0.0.1/auth/token"), refreshTokenRequestArgumentCaptor.capture(), eq(DefaultAuthenticationResponse.class));
        RefreshTokenRequest refreshTokenRequest = refreshTokenRequestArgumentCaptor.getValue();
        assertEquals("someRefreshToken", refreshTokenRequest.getRefreshToken());

        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void ifRefreshTokenExpiredThenServiceShouldLoginAgainWithLoginAndPassword() {
        authenticationResponse.setExpiresIn(50);

        when(restTemplate.getForObject("http://127.0.0.1", DefaultVoidResource.class)).thenReturn(defaultVoidResource);
        when(restTemplate.postForObject(eq("http://127.0.0.1/auth/login"), any(AuthenticationRequest.class), eq(DefaultAuthenticationResponse.class))).thenReturn(authenticationResponse);

        // Initial login.

        authenticationService.ensureAuthentication();

        assertTrue(authenticationService.isAuthenticated());
        assertNotNull(authenticationService.getTokens());
        assertEquals("someAccessToken", authenticationService.getTokens().getAccessToken());
        assertEquals(50, authenticationService.getTokens().getExpiresIn());
        assertEquals("someRefreshToken", authenticationService.getTokens().getRefreshToken());

        InOrder inOrder = inOrder(restTemplate);
        inOrder.verify(restTemplate).getForObject("http://127.0.0.1", DefaultVoidResource.class);

        ArgumentCaptor<AuthenticationRequest> authenticationRequestArgumentCaptor = ArgumentCaptor.forClass(AuthenticationRequest.class);
        inOrder.verify(restTemplate).postForObject(eq("http://127.0.0.1/auth/login"), authenticationRequestArgumentCaptor.capture(), eq(DefaultAuthenticationResponse.class));

        AuthenticationRequest actualAuthenticationRequest = authenticationRequestArgumentCaptor.getValue();
        assertEquals("someLogin", actualAuthenticationRequest.getUsername());
        assertEquals("somePassword", actualAuthenticationRequest.getPassword());

        // Refreshing tokens and login again.

        DefaultAuthenticationResponse authenticationResponse = new DefaultAuthenticationResponse();
        authenticationResponse.setAccessToken("someNewAccessToken");
        authenticationResponse.setExpiresIn(200);
        authenticationResponse.setRefreshToken("someNewRefreshToken");

        DefaultError defaultError = new DefaultError();
        defaultError.setTitle("Unauthorized");
        defaultError.setStatus(401);
        defaultError.setDetail("Refresh token expired");
        BackendException backendException = new BackendException(defaultError);

        when(restTemplate.postForObject(eq("http://127.0.0.1/auth/token"), any(RefreshTokenRequest.class), eq(DefaultAuthenticationResponse.class))).thenThrow(backendException);
        doReturn(authenticationResponse).when(restTemplate).postForObject(eq("http://127.0.0.1/auth/login"), any(AuthenticationRequest.class), eq(DefaultAuthenticationResponse.class));

        authenticationService.ensureAuthentication();

        assertTrue(authenticationService.isAuthenticated());
        assertNotNull(authenticationService.getTokens());
        assertEquals("someNewAccessToken", authenticationService.getTokens().getAccessToken());
        assertEquals(200, authenticationService.getTokens().getExpiresIn());
        assertEquals("someNewRefreshToken", authenticationService.getTokens().getRefreshToken());

        ArgumentCaptor<RefreshTokenRequest> refreshTokenRequestArgumentCaptor = ArgumentCaptor.forClass(RefreshTokenRequest.class);
        inOrder.verify(restTemplate).postForObject(eq("http://127.0.0.1/auth/token"), refreshTokenRequestArgumentCaptor.capture(), eq(DefaultAuthenticationResponse.class));
        RefreshTokenRequest refreshTokenRequest = refreshTokenRequestArgumentCaptor.getValue();
        assertEquals("someRefreshToken", refreshTokenRequest.getRefreshToken());

        ArgumentCaptor<AuthenticationRequest> authenticationRequestArgumentCaptor2 = ArgumentCaptor.forClass(AuthenticationRequest.class);
        inOrder.verify(restTemplate).postForObject(eq("http://127.0.0.1/auth/login"), authenticationRequestArgumentCaptor2.capture(), eq(DefaultAuthenticationResponse.class));

        AuthenticationRequest actualAuthenticationRequest2 = authenticationRequestArgumentCaptor2.getValue();
        assertEquals("someLogin", actualAuthenticationRequest2.getUsername());
        assertEquals("somePassword", actualAuthenticationRequest2.getPassword());

        verifyNoMoreInteractions(restTemplate);
    }

}