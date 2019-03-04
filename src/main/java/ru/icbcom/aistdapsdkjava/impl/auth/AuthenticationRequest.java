package ru.icbcom.aistdapsdkjava.impl.auth;

import ru.icbcom.aistdapsdkjava.api.resource.Resource;

public interface AuthenticationRequest {
    String getUsername();
    String getPassword();
}
