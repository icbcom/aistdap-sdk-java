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

package ru.icbcom.aistdapsdkjava.api.resource;

/**
 * Коллекция ресурсов.
 */
public interface CollectionResource<T extends Resource> extends Resource, Iterable<T> {

    /**
     * Возвращает размер запрошенной страницы.
     *
     * <p>Значение данного параметра не означает, что реальное количество объектов в одной странице равно данному значению.
     * Этот параметр лишь задает максимальное возможное количество объектов, полученных в рамках одного REST HTTP запроса. </p>
     */
    long getSize();

    /**
     * Возвращает общее количество доступных элементов во всей коллекции.
     */
    long getTotalElements();

    /**
     * Возвращает общее количество доступных страниц для все коллекции.
     */
    long getTotalPages();

    /**
     * Возвращает номер текущей страницы.
     */
    long getNumber();

}
