package ru.icbcom.aistdapsdkjava.api.client;

import ru.icbcom.aistdapsdkjava.impl.client.DefaultClientBuilder;

/**
 *
 */
public final class Clients {

    /**
     *
     */
    public static ClientBuilder builder() {
        return new DefaultClientBuilder();
    }

}
