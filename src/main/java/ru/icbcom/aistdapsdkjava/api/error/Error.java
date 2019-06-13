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

package ru.icbcom.aistdapsdkjava.api.error;

import java.util.Map;
import java.util.Optional;

/**
 * Ошибка.
 */
public interface Error {

    /**
     * Краткое, человеко-читаемое описание данной проблемы.
     */
    String getTitle();

    /**
     * Код статуса HTTP ответа полученный от сервера.
     */
    int getStatus();

    /**
     * Детальное описание возникшей проблемы.
     */
    String getDetail();

    /**
     * Дополнительная информация о проблеме в формате ключ - значение.
     */
    Map<String, Object> getMoreInfo();

    /**
     * Получение значения из таблицы дополнительной информации {@code moreInfo}, и его приведение (cast) к
     * заданному параметром {@code clazz} типу.
     */
    <T> Optional<T> getMoreInfoValue(String key, Class<T> clazz);

}
