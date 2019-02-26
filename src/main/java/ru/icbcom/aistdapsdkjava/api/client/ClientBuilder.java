package ru.icbcom.aistdapsdkjava.api.client;

public interface ClientBuilder {

    ClientBuilder setLogin(String login);

    ClientBuilder setPassword(String password);

    ClientBuilder setBaseUrl(String baseUrl);

    Client build();

}
