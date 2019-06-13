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

package ru.icbcom.aistdapsdkjava.api.datasource;

import ru.icbcom.aistdapsdkjava.api.datasourcegroup.DataSourceGroup;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectType;
import ru.icbcom.aistdapsdkjava.api.resource.Deletable;
import ru.icbcom.aistdapsdkjava.api.resource.Resource;
import ru.icbcom.aistdapsdkjava.api.resource.Savable;

/**
 * Источник данных.
 */
public interface DataSource extends Resource, Savable, Deletable {

    /**
     * Возвращает идентификатор данного источника данных.
     */
    Long getDataSourceId();

    /**
     * Устанавливает идентификатор источника данных.
     */
    DataSource setDataSourceId(Long dataSourceId);

    /**
     * Возвращает идентификатор типа объекта, которому принадлежит данный источник данных.
     */
    Long getObjectTypeId();

    /**
     * Устанавливает идентификатор типа объекта, которому принадлежит данный источник данных.
     */
    DataSource setObjectTypeId(Long objectTypeId);

    /**
     * Возвращает название данного источника данных.
     */
    String getCaption();

    /**
     * Устанавливает название данного источника данных.
     */
    DataSource setCaption(String caption);

    /**
     * Возвращает единицу измерения данного источника данных.
     */
    String getMeasureItem();

    /**
     * Устанавливает едииницу измерения данного источника данных.
     */
    DataSource setMeasureItem(String measureItem);

    /**
     * Возвращает идентификатор группы источников данных, которой принадлежит данный источник.
     */
    Long getDataSourceGroupId();

    /**
     * Устанавливает идентификатор группы источников данных, которой принадлежит данный источник.
     */
    DataSource setDataSourceGroupId(Long dataSourceGroupId);

    /**
     * Получение типа объекта, которому принадлежит данный источник данных.
     */
    ObjectType getObjectType();

    /**
     * Получение группы источников данный, к которой принадлежит данный источник.
     */
    DataSourceGroup getDataSourceGroup();

}
