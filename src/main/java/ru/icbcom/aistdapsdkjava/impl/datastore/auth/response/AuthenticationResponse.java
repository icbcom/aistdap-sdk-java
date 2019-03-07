package ru.icbcom.aistdapsdkjava.impl.datastore.auth.response;

public interface AuthenticationResponse {
    String getAccessToken();
    int getExpiresIn();
    String getRefreshToken();
}
