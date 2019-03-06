package ru.icbcom.aistdapsdkjava.impl.auth.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Link;
import org.springframework.web.client.RestTemplate;
import ru.icbcom.aistdapsdkjava.api.resource.VoidResource;
import ru.icbcom.aistdapsdkjava.impl.auth.*;
import ru.icbcom.aistdapsdkjava.impl.auth.request.AuthenticationRequest;
import ru.icbcom.aistdapsdkjava.impl.auth.request.DefaultAuthenticationRequest;
import ru.icbcom.aistdapsdkjava.impl.auth.request.DefaultRefreshTokenRequest;
import ru.icbcom.aistdapsdkjava.impl.auth.request.RefreshTokenRequest;
import ru.icbcom.aistdapsdkjava.impl.auth.response.AuthenticationResponse;
import ru.icbcom.aistdapsdkjava.impl.auth.response.DefaultAuthenticationResponse;
import ru.icbcom.aistdapsdkjava.impl.exception.LinkNotFoundException;
import ru.icbcom.aistdapsdkjava.impl.resource.DefaultVoidResource;

import java.time.Instant;

// TODO: Протестировать данный класс.

@RequiredArgsConstructor
@Slf4j
public class DefaultAuthenticationController implements AuthenticationController {

    private final String baseUrl;
    private final AuthenticationKey authentication;
    private final RestTemplate restTemplate;

    private Tokens tokens;
    private Link loginLink;
    private Link refreshTokenLink;

    // Можно запустить поток, который будет периодически выполнять обновления токена. Надо это сделать!

    @Override
    public void ensureAuthentication() {
        if (loginLink == null || refreshTokenLink == null) {
            requestAndSaveTokenRelatedLinks();
        }
        if (!isAuthenticated()) {
            log.info("Not authenticated. Trying to authenticate...");
            login();
        } else {
            if (tokens.secondsToExpiration() <= 60) {
                log.info("Refreshing tokens...");
                refreshToken();
                log.info("Tokens refreshed");
            }
            log.debug("Already authenticated. No authentication required.");
        }
    }

    private void refreshToken() {
        RefreshTokenRequest refreshTokenRequest = new DefaultRefreshTokenRequest(tokens.getRefreshToken());
        AuthenticationResponse authenticationResponse = restTemplate.postForObject(refreshTokenLink.getHref(), refreshTokenRequest, DefaultAuthenticationResponse.class);
        tokens = createTokensFor(authenticationResponse);
    }

    private void login() {
        AuthenticationRequest authenticationRequest = new DefaultAuthenticationRequest(authentication.getLogin(), authentication.getPassword());
        AuthenticationResponse authenticationResponse = restTemplate.postForObject(loginLink.getHref(), authenticationRequest, DefaultAuthenticationResponse.class);
        tokens = createTokensFor(authenticationResponse);
    }

    private void requestAndSaveTokenRelatedLinks() {
        VoidResource rootResource = restTemplate.getForObject(baseUrl, DefaultVoidResource.class);
        loginLink = rootResource.getLink("dap:login").orElseThrow(() -> new LinkNotFoundException(baseUrl, "dap:login"));
        refreshTokenLink = rootResource.getLink("dap:refreshToken").orElseThrow(() -> new LinkNotFoundException(baseUrl, "dap:refreshToken"));
    }

    private Tokens createTokensFor(AuthenticationResponse authenticationResponse) {
        return Tokens.builder()
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
