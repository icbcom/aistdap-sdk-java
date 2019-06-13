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

package ru.icbcom.aistdapsdkjava.api.physicalstructure;

import java.util.Optional;

/**
 * Интерфейс {@link PhysicalStructureObjectActions} содержит все основные функции для работы с объектами физической структуры.
 */
public interface PhysicalStructureObjectActions {

    /**
     * Получение списка всех объектов физической структуры {@link PhysicalStructureObject} находящихся в корне.
     *
     * @return список устройств {@link PhysicalStructureObject} находящихся в корне
     */
    PhysicalStructureObjectList getAllInRoot();

    /**
     * Получение списка всех объектов физической структуры {@link PhysicalStructureObject} находящихся в корне.
     * Данный метод позволяет задавать {@link PhysicalStructureObject}-специфичные критерии запроса.
     *
     * @return список устройств {@link PhysicalStructureObject} находящихся в корне
     */
    PhysicalStructureObjectList getAllInRoot(PhysicalStructureObjectCriteria criteria);

    /**
     * Получение объекта физической структуры {@link PhysicalStructureObject} по его идентификатору.
     *
     * @param physicalStructureObjectId идентификатор объекта физической структуры {@link PhysicalStructureObject}
     * @return {@link Optional} содержащий объект физической структуры с соответствующим идентификатором, если такой объект существует.
     * Либо {@link Optional#empty()}, если объекта физической структуры с заданным идентификатором не существует.
     */
    Optional<PhysicalStructureObject> getById(Long physicalStructureObjectId);

    /**
     * Создание нового объекта физической структуры {@link PhysicalStructureObject} в корне дерева.
     *
     * @param physicalStructureObject создаваемый объект физической структуры.
     * @return объект физической структуры {@link PhysicalStructureObject} сохраненный в платформе
     */
    PhysicalStructureObject createInRoot(PhysicalStructureObject physicalStructureObject);

}
