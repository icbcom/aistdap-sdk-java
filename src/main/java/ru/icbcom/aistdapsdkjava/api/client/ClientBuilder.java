package ru.icbcom.aistdapsdkjava.api.client;

/**
 * Интерфейс {@link ClientBuilder} используется для построения экземпляров {@link Client} с соответствующими настройками
 * подключения к REST API платформы Aist Dap.
 *
 * <h3>Использованиие</h3>
 *
 * <p>Для создания экземпляра {@link Client} вам протребуется вызвать соответствующие методы настройки подключения и
 * вызвать метод {@link #build() build()}, например:</p>
 *
 * <pre>
 * Client client = {@link Clients Clients}.builder()
 *                  .setBaseUrl("http://api.aistdap.ru:8080/")
 *                  .setLogin("Login")
 *                  .setPassword("password")
 *                  .{@link #build() build()};
 * </pre>
 *
 * <p>В данном примере:</p>
 * <ul>
 *     <li>Задается базовый URL к REST API платформы Aist Dap;</li>
 *     <li>Задается имя пользователя для входа в платформу Aist Dap;</li>
 *     <li>Задается пароль данного пользователя;</li>
 *     <li>Создается экземпляр {@link Client} с соответствующими настройками.</li>
 * </ul>
 */
public interface ClientBuilder {

    /**
     * Установка имени пользователя используемого для входа в платформу Aist Dap.
     *
     * @param login имя пользователя
     * @return данный объект {@code this} для объединения вызовов в цепочку
     */
    ClientBuilder setLogin(String login);

    /**
     * Установка пароля соответствующего пользователя.
     *
     * @param password пароль пользователя
     * @return данный объект {@code this} для объединения вызовов в цепочку
     */
    ClientBuilder setPassword(String password);

    /**
     * Устанавливает базовый URL для подключения к REST API платформы Aist Dap.
     *
     * @param baseUrl базовый URL для подключения к REST API платформы Aist Dap
     * @return данный объект {@code this} для объединения вызовов в цепочку
     */
    ClientBuilder setBaseUrl(String baseUrl);

    /**
     * Создание объекта {@link Client} с соответствующиими настройками.
     *
     * @return созданный экземпляр {@link Client}
     */
    Client build();

}
