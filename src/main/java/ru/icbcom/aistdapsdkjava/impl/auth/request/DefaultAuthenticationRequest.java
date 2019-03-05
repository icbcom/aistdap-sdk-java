package ru.icbcom.aistdapsdkjava.impl.auth.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DefaultAuthenticationRequest implements AuthenticationRequest {
    private String username;
    private String password;
}
