package ru.icbcom.aistdapsdkjava.impl.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DefaultAuthenticationRequest implements AuthenticationRequest {
    private final String username;
    private final String password;
}
