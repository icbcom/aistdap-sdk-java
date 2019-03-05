package ru.icbcom.aistdapsdkjava.impl.auth.response;

import ru.icbcom.aistdapsdkjava.api.resource.Resource;

public interface AuthenticationResponse {
    String getAccessToken();
    int getExpiresIn();
    String getRefreshToken();
}
