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

package ru.icbcom.aistdapsdkjava.api.objecttype;

import java.util.Optional;

/**
 * Интерфейс {@link ObjectTypeActions} содержит все основные функции для работы с типами объектов.
 */
public interface ObjectTypeActions {

    /**
     * Создание нового типа объекта в системе.
     */
    ObjectType create(ObjectType objectType);

    /**
     * Получение списка всех типов объектов зарегистрированных в системе.
     */
    ObjectTypeList getAll();

    /**
     * Получение списка всех типов объектов зарегистрированных в системе.
     */
    ObjectTypeList getAll(ObjectTypeCriteria criteria);

    /**
     * Получение списка активных ({@code enabled == true}) типов объектов, которые описывают устройства ({@code device == true}).
     */
    ObjectTypeList getAllEnabledDevices();

    /**
     * Получение списка активных ({@code enabled == true}) типов объектов, которые описывают устройства ({@code device == true}).
     */
    ObjectTypeList getAllEnabledDevices(ObjectTypeCriteria criteria);

    /**
     * Получение списка типов объектов, которые описывают устройства ({@code device == true}).
     */
    ObjectTypeList getAllDevices();

    /**
     * Получение списка типов объектов, которые описывают устройства ({@code device == true}).
     */
    ObjectTypeList getAllDevices(ObjectTypeCriteria criteria);

    /**
     * Получение списка активных ({@code enabled == true}) типов объектов.
     */
    ObjectTypeList getAllEnabled();

    /**
     * Получение списка активных ({@code enabled == true}) типов объектов.
     */
    ObjectTypeList getAllEnabled(ObjectTypeCriteria criteria);

    /**
     * Получение типа объекта по его идентификатору.
     */
    Optional<ObjectType> getById(Long objectTypeId);

}
