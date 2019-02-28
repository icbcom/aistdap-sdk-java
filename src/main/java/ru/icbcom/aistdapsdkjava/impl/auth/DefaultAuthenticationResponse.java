package ru.icbcom.aistdapsdkjava.impl.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DefaultAuthenticationResponse implements AuthenticationResponse {
    private String accessToken;
    private int expiresIn;
    private String refreshToken;
}
