package ru.icbcom.aistdapsdkjava.impl.auth;

import lombok.Data;

@Data
public class DefaultAuthenticationResponse implements AuthenticationResponse {
    private String accessToken;
    private int expiresIn;
    private String refreshToken;
}
