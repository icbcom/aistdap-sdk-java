package ru.icbcom.aistdapsdkjava.impl.datastore.auth.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DefaultAuthenticationResponse implements AuthenticationResponse {
    private String accessToken;
    private int expiresIn;
    private String refreshToken;
}