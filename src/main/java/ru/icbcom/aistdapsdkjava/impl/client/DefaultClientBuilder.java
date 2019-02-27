package ru.icbcom.aistdapsdkjava.impl.client;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.util.Assert;
import ru.icbcom.aistdapsdkjava.api.client.Client;
import ru.icbcom.aistdapsdkjava.api.client.ClientBuilder;

public class DefaultClientBuilder implements ClientBuilder {

    private final UrlValidator urlValidator = new UrlValidator(new String[]{"http", "https"});

    private String baseUrl;
    private String login;
    private String password;

    @Override
    public ClientBuilder setLogin(String login) {
        Assert.notNull(login, "login cannot be null");
        Assert.isTrue(!login.isBlank(), "login cannon be blank");
        this.login = login;
        return this;
    }

    @Override
    public ClientBuilder setPassword(String password) {
        Assert.notNull(password, "password cannot be null");
        Assert.isTrue(!password.isBlank(), "password cannon be blank");
        this.password = password;
        return this;
    }

    @Override
    public ClientBuilder setBaseUrl(String baseUrl) {
        Assert.notNull(baseUrl, "baseUrl cannot be null");
        Assert.isTrue(urlValidator.isValid(baseUrl), "baseUrl should be valid");
        this.baseUrl = baseUrl;
        return this;
    }

    @Override
    public Client build() {
        Assert.notNull(baseUrl, "AistDap base url must not be null");
        Assert.notNull(login, "AistDap login must not be null");
        Assert.notNull(password, "AistDap password must not be null");
        return new DefaultClient(baseUrl, login, password);
    }

}
