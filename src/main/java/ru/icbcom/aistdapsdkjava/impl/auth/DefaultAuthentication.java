package ru.icbcom.aistdapsdkjava.impl.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DefaultAuthentication implements Authentication {
    private final String login;
    private final String password;
}
