package ru.icbcom.aistdapsdkjava.impl.datastore.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DefaultAuthenticationKey implements AuthenticationKey {
    private final String login;
    private final String password;
}
