package ru.icbcom.aistdapsdkjava.impl.auth;

public interface AuthenticationResponse {
    String getAccessToken();
    int getExpiresIn();
    String getRefreshToken();
}
