package ru.icbcom.aistdapsdkjava.impl.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.resource.AbstractResource;

@AllArgsConstructor
@Getter
public class DefaultAuthenticationRequest implements AuthenticationRequest {
    private String username;
    private String password;
}
