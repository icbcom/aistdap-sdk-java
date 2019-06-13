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
 * Секция для группировки атрибутов типов объектов.
 */
public interface Section extends Resource {

    /**
     * Возвращает название данной секции.
     */
    String getName();

    /**
     * Устанавливает название данной секции.
     */
    Section setName(String name);

    /**
     * Возвращает заголовок данной секции.
     */
    String getCaption();

    /**
     * Устанавливает заголовок данной секции.
     */
    Section setCaption(String caption);

    /**
     * Возвращает комментарий к данной секции.
     */
    String getComment();

    /**
     * Устанавливает комментарий к данной секции.
     */
    Section setComment(String comment);

    /**
     * Возвращает коллекцию атрибутов принадлежащих данной секции.
     */
    Collection<Attribute> getAttributes();

    /**
     * Устанавливает коллекцию атрибутов принадлежащих к данной секции.
     */
    Section setAttributes(Collection<Attribute> attributes);

    /**
     * Получение атрибута по его названию для данного типа объекта.
     *
     * @param name название атрибута
     * @return {@link Optional} содержащий атрибут с заданным именем (если такой атрибут существует), либо пустой {@link Optional}.
     */
    Optional<Attribute> getAttributeByName(String name);

    /**
     * Добавляет атрибут к данной секции.
     */
    Section addAttribute(Attribute attribute);

}
