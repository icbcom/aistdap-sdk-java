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

package ru.icbcom.aistdapsdkjava.api.device;

import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectType;
import ru.icbcom.aistdapsdkjava.api.physicalstructure.PhysicalStructureObject;
import ru.icbcom.aistdapsdkjava.api.resource.Deletable;
import ru.icbcom.aistdapsdkjava.api.resource.Resource;
import ru.icbcom.aistdapsdkjava.api.resource.Savable;

import java.util.Map;
import java.util.Optional;

/**
 * Устройство.
 */
public interface Device extends Resource, Savable, Deletable {

    /**
     * Возвращает идентификатор данного устройства.
     *
     * @return идентификатор данного устройства
     */
    Long getId();

    /**
     * Устанавливает идентификатор данного устройства.
     *
     * @param id идентификатор устройства
     * @return данный объект {@code this} для объединения вызовов в цепочку
     */
    Device setId(Long id);

    /**
     * Возвращает идентификатор типа объекта данного устройства.
     *
     * @return идентификатор типа объекта данного устройства
     */
    Long getObjectTypeId();

    /**
     * Установка идентификатора типа ообъекта данного устройства.
     *
     * @param objectTypeId идентификатор типа объекта данного устройства
     * @return данный объект {@code this} для объединения вызовов в цепочку
     */
    Device setObjectTypeId(Long objectTypeId);

    /**
     * Возвращает название данного устройства.
     *
     * @return название данного истройства
     */
    String getName();

    /**
     * Устанавлиивает название данного устройства.
     *
     * @param name названиеи данного устройства
     * @return данный объект {@code this} для объединения вызовов в цепочку
     */
    Device setName(String name);

    /**
     * Возвращает значения атрибутов данного объекта.
     *
     * @return значения атрибутов данного объекта
     */
    Map<String, String> getAttributeValues();

    /**
     * Установка таблицы значений атрибутов данного объекта.
     *
     * @param attributeValues значения атрибутов данного объекта
     * @return данный объект {@code this} для объединения вызовов в цепочку
     */
    Device setAttributeValues(Map<String, String> attributeValues);

    /**
     * Возвращает значение одного атрибута с заданным именем {@code name}.
     *
     * @param name имя атрибута
     * @return {@link Optional} содержащий значение атрибута с соответствующим именем, если такой атрибут существует.
     * Если такого атрибута нет, то будет возвращен пустой объект {@link Optional}.
     */
    Optional<String> getAttributeValueByName(String name);

    /**
     * Установка значения атрибута с заданным именем.
     * <p>Если атрибут с заданным именем уже существует, то его значение просто перезапишется новым значением.
     * Если же атрибут с заданным именем не существет, то такой атрибут будет создан с соответствующим значением.</p>
     *
     * @param attributeName  имя атрибута
     * @param attributeValue значение атрибута
     * @return данный объект {@code this} для объединения вызовов в цепочку
     */
    Device setAttributeValue(String attributeName, String attributeValue);

    /**
     * Удаление значения атрибута по его имени.
     *
     * @param attributeName имя удаляемого атрибута
     * @return данный объект {@code this} для объединения вызовов в цепочку
     */
    Device removeAttributeValueByName(String attributeName);

    /**
     * Возвращает тип объекта данного устройства.
     *
     * @return тип объекта данного устройства
     */
    ObjectType getObjectType();

    /**
     * Привязано ли данное устройство к какому-либо объекту физической структуры.
     * <p>Данный метод возвращает правильное значение только для тех объектов, которые были сохранены или получены из
     * платформы (persisted objects).</p>
     *
     * @return true - данное устройство привязано к какому-либо объекту физической структуры, false - данное устройство
     * не привязано ни к одному объекту физической структуры.
     */
    boolean isAttached();

    /**
     * Возвращает объект физической структуры, к которому привязано данное устройство.
     *
     * @return {@link Optional} содержащий объект физической структуры, к которому привязано данное устройство, либо
     * пустой {@link Optional} если данное устройство не привязано ни к одному объекту физической структуры.
     */
    Optional<PhysicalStructureObject> getPhysicalStructureObjectDeviceAttachedTo();

    /**
     * Отвязать данное устройство от объекта физической структуры.
     */
    void detach();

    /**
     * Привязка данного устройства к объекту физической структуры.
     *
     * @param physicalStructureObjectId идентификатор объекта физической структуры, к которому производится привязка
     *                                  данного устройства
     */
    void attach(Long physicalStructureObjectId);

}