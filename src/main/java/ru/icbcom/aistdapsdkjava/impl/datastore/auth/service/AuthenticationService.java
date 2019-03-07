package ru.icbcom.aistdapsdkjava.impl.datastore.auth.service;

import ru.icbcom.aistdapsdkjava.impl.datastore.auth.tokens.Tokens;

/**
 * Сервис аутентификации в платформе AistDap.
 */
public interface AuthenticationService {
    /**
     * Данный метод либо осуществляет вход пользователя в систему (с получением токенов), либо обновляет уже
     * существующие токены (если срок действия токена доступа подходит к концу).
     */
    void ensureAuthentication();

    /**
     * Проверка, осуществлен ли успешный вход в систему AistDap.
     */
    boolean isAuthenticated();

    /**
     * Возвращает объект содержащий токены для доступа к AistDap.
     */
    Tokens getTokens();
}
