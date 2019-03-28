package ru.icbcom.aistdapsdkjava.api.client;

import ru.icbcom.aistdapsdkjava.impl.client.DefaultClientBuilder;

/**
 * Статический вспомогательный класс для работы с объектами связанными с {@link Client}. Например:
 * <pre>
 * <b>Clients.builder()</b>
 *      // ...
 *      .build();
 * </pre>
 */
public final class Clients {

    /**
     * Возвращает новый экземпляр {@link ClientBuilder} используемый для создания экземпляров {@link Client}.
     *
     * @return новый экземпляр {@link ClientBuilder} используемый для создания экземпляров {@link Client}
     */
    public static ClientBuilder builder() {
        return new DefaultClientBuilder();
    }

}
