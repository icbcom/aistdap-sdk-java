/*
 * Copyright © 2018-2019 Icbcom
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

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
