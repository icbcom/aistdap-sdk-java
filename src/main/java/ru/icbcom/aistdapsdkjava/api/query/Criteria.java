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

package ru.icbcom.aistdapsdkjava.api.query;

/**
 * Базовый интерфейс {@link Criteria} для настройки параметров запросов коллекций ресурсов.
 */
public interface Criteria<T extends Criteria<T>> {

    /**
     * Установка порядка сортировки по возрастанию соответствующего параметра, который указывается соответствующим
     * вызовом orderBy(). Например:
     * <pre>
     *     Devices.criteria()
     *          .orderByName().ascending()
     *          .pageSize(100)
     * </pre>
     * В данном примере создается критерий запроса устройств с сортировкой по полю {@code name} в возрастающем порядке.
     */
    T ascending();

    /**
     * Установка порядка сортировки по убыванию соответствующего параметра, который указывается соответствующим
     * вызовом orderBy(). Например:
     * <pre>
     *     Devices.criteria()
     *          .orderByName().descending()
     *          .pageSize(100)
     * </pre>
     * В данном примере создается критерий запроса устройств с сортировкой по полю {@code name} в убывающем порядке.
     */
    T descending();

    /**
     * Указывает размер страницы при запросе коллекции ресурсов.
     */
    T pageSize(int pageSize);

    /**
     * Указывает номер запрашиваемой страницы при запросе коллекции ресурсов.
     */
    T pageNumber(int pageNumber);

    /**
     * Возвращает {@code true} если данный объект не содержит никаких условий или выражений orderBy, {@code false}
     * в противном случае.
     */
    boolean isEmpty();

}
