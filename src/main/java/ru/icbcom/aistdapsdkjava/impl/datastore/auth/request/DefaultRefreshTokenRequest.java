package ru.icbcom.aistdapsdkjava.impl.datastore.auth.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DefaultRefreshTokenRequest implements RefreshTokenRequest {
    private final String refreshToken;
}