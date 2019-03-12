package ru.icbcom.aistdapsdkjava.impl.datastore.auth.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Link;
import org.springframework.web.client.RestTemplate;
import ru.icbcom.aistdapsdkjava.api.exception.AistDapBackendException;
import ru.icbcom.aistdapsdkjava.api.resource.VoidResource;
import ru.icbcom.aistdapsdkjava.impl.datastore.auth.key.AuthenticationKey;
import ru.icbcom.aistdapsdkjava.impl.datastore.auth.tokens.DefaultTokens;
import ru.icbcom.aistdapsdkjava.impl.datastore.auth.tokens.Tokens;
import ru.icbcom.aistdapsdkjava.impl.datastore.auth.request.AuthenticationRequest;
import ru.icbcom.aistdapsdkjava.impl.datastore.auth.request.DefaultAuthenticationRequest;
import ru.icbcom.aistdapsdkjava.impl.datastore.auth.request.DefaultRefreshTokenRequest;
import ru.icbcom.aistdapsdkjava.impl.datastore.auth.request.RefreshTokenRequest;
import ru.icbcom.aistdapsdkjava.impl.datastore.auth.response.AuthenticationResponse;
import ru.icbcom.aistdapsdkjava.impl.datastore.auth.response.DefaultAuthenticationResponse;
import ru.icbcom.aistdapsdkjava.api.exception.LinkNotFoundException;
import ru.icbcom.aistdapsdkjava.impl.resource.DefaultVoidResource;
import ru.icbcom.aistdapsdkjava.impl.utils.Utils;

import java.time.Instant;

@Slf4j
public class DefaultAuthenticationService implements AuthenticationService {

    private static final int REFRESH_TOKEN_EXPIRATION_MARGIN = 60;

    private final String baseUrl;
    private final AuthenticationKey authentication;
    private final RestTemplate restTemplate;

    private Tokens tokens;
    private Link loginLink;
    private Link refreshTokenLink;

    public DefaultAuthenticationService(String baseUrl, AuthenticationKey authentication, RestTemplate restTemplate) {
        this.baseUrl = baseUrl;
        this.authentication = authentication;
        this.restTemplate = restTemplate;
    }

    @Override
    public void ensureAuthentication() {
        if (loginLink == null || refreshTokenLink == null) {
            requestAndSaveTokenRelatedLinks();
        }
        if (!isAuthenticated()) {
            login();
        } else {
            if (tokens.getSecondsToExpiration() <= REFRESH_TOKEN_EXPIRATION_MARGIN) {
                refreshTokenOrReloginIfRefreshFailed();
            }
        }
    }

    private void refreshTokenOrReloginIfRefreshFailed() {
        try {
            refreshToken();
        } catch (AistDapBackendException e) {
            boolean refreshTokenExpired = e.getStatus() == 401 && e.getDetail().equals("Refresh token expired");
            if (refreshTokenExpired) {
                log.info("Cannot refresh access token due to refresh token expiration. Trying to re-login instead.");
                login();
            } else {
                throw e;
            }
        }
    }

    private void refreshToken() {
        log.info("Refreshing tokens...");
        RefreshTokenRequest refreshTokenRequest = new DefaultRefreshTokenRequest(tokens.getRefreshToken());
        AuthenticationResponse authenticationResponse = restTemplate.postForObject(refreshTokenLink.getHref(), refreshTokenRequest, DefaultAuthenticationResponse.class);
        Utils.assertResourceNotNull(authenticationResponse, "Received authentication response was null");
        tokens = createTokensFor(authenticationResponse);
        log.info("Successfully refreshed tokens.");
    }

    private void login() {
        log.info("Trying to login...");
        AuthenticationRequest authenticationRequest = new DefaultAuthenticationRequest(authentication.getLogin(), authentication.getPassword());
        AuthenticationResponse authenticationResponse = restTemplate.postForObject(loginLink.getHref(), authenticationRequest, DefaultAuthenticationResponse.class);
        Utils.assertResourceNotNull(authenticationResponse, "Received authentication response was null");
        tokens = createTokensFor(authenticationResponse);
        log.info("Successfully logged in.");
    }

    private void requestAndSaveTokenRelatedLinks() {
        VoidResource rootResource = restTemplate.getForObject(baseUrl, DefaultVoidResource.class);
        Utils.assertResourceNotNull(rootResource, "Received root resource was null");
        loginLink = rootResource.getLink("dap:login").orElseThrow(() -> new LinkNotFoundException(baseUrl, "dap:login"));
        refreshTokenLink = rootResource.getLink("dap:refreshToken").orElseThrow(() -> new LinkNotFoundException(baseUrl, "dap:refreshToken"));
    }

    private Tokens createTokensFor(AuthenticationResponse authenticationResponse) {
        return DefaultTokens.builder()
                .accessToken(authenticationResponse.getAccessToken())
                .expiresIn(authenticationResponse.getExpiresIn())
                .refreshToken(authenticationResponse.getRefreshToken())
                .obtainedAt(Instant.now())
                .build();
    }

    @Override
    public boolean isAuthenticated() {
        return tokens != null;
    }

    @Override
    public Tokens getTokens() {
        return tokens;
    }

}
