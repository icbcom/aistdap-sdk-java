package ru.icbcom.aistdapsdkjava.impl.auth.controller;

import ru.icbcom.aistdapsdkjava.impl.auth.Tokens;

public interface AuthenticationController {
    void ensureAuthentication();

    boolean isAuthenticated();

    Tokens getTokens();
}
