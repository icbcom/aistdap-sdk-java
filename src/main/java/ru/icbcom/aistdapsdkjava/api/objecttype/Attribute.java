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

import ru.icbcom.aistdapsdkjava.api.resource.Resource;

import java.util.Collection;
import java.util.Optional;

/**
 * Атрибут типа объекта.
 */
public interface Attribute extends Resource {

    /**
     * Возвращает название данного атрибута.
     */
    String getName();

    /**
     * Устанавливает название данного атрибута.
     */
    Attribute setName(String name);

    /**
     * Возвращает заголовок данного атрибута.
     */
    String getCaption();

    /**
     * Устанавлиивает заголовок данного атрибута.
     */
    Attribute setCaption(String caption);

    /**
     * Возвращает тип данного атрибута.
     */
    AttributeType getType();

    /**
     * Устанавливает тип данного атрибута.
     */
    Attribute setType(AttributeType type);

    /**
     * Возвращает ограничение на минимально возможное значение атрибута.
     */
    String getMin();

    /**
     * Устанавливает ограничение на минимально возможное значение атрибута.
     */
    Attribute setMin(String min);

    /**
     * Возвращает ограничение на максимально возможное значение атрибута.
     */
    String getMax();

    /**
     * Устанавливает ограничение на максимально возможное значение атрибута.
     */
    Attribute setMax(String max);

    /**
     * Возвращает маску ограничивающую строковое значение атрибута.
     */
    String getMask();

    /**
     * Устанавливает маску ограничивающую строковое значение атрибута.
     */
    Attribute setMask(String mask);

    /**
     * Возвращает список, описывающий возможные значения для атрибутов типа ENUMERATION и SET.
     */
    Collection<EnumSetValue> getEnumSetValues();

    /**
     * Устанавливает коллекцию элементов перечиисления/множества.
     */
    Attribute setEnumSetValues(Collection<EnumSetValue> enumSetValues);

    /**
     * Возвращает элемент перечисления/множества по его номеру.
     */
    Optional<EnumSetValue> getEnumSetValueByNumber(int number);

    /**
     * Возвращает атрибута по умолчанию.
     */
    String getDefaultValue();

    /**
     * Устанавливает значение атрибута по умолчанию.
     */
    Attribute setDefaultValue(String defaultValue);

    /**
     * Возвращает комментарий к данному атрибуту.
     */
    String getComment();

    /**
     * Устанавливает комментарий к данному атрибуту.
     */
    Attribute setComment(String comment);

    /**
     * Добавляет элемент перечисления/множества в соответствующую коллекцию.
     */
    Attribute addEnumSetValue(EnumSetValue enumSetValue);

}
