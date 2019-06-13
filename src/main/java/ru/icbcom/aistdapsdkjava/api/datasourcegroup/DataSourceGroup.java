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

package ru.icbcom.aistdapsdkjava.api.datasourcegroup;

import ru.icbcom.aistdapsdkjava.api.datasource.DataSourceCriteria;
import ru.icbcom.aistdapsdkjava.api.datasource.DataSourceList;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectType;
import ru.icbcom.aistdapsdkjava.api.resource.Deletable;
import ru.icbcom.aistdapsdkjava.api.resource.Resource;
import ru.icbcom.aistdapsdkjava.api.resource.Savable;

/**
 * Группа источников данных.
 */
public interface DataSourceGroup extends Resource, Savable, Deletable {

    /**
     * Возвращает идентификатор группы источников данных.
     */
    Long getDataSourceGroupId();

    /**
     * Устанавливает идентификатор группы источников данных.
     */
    DataSourceGroup setDataSourceGroupId(Long dataSourceGroupId);

    /**
     * Возвращает идентификатор типа объекта, которому принадлежит данная группа источников данных.
     */
    Long getObjectTypeId();

    /**
     * Устанавливает идентификатор типа объекта, которому принадлежит данная группа источников данных.
     */
    DataSourceGroup setObjectTypeId(Long objectTypeId);

    /**
     * Возвращает название данногй группы источников данных.
     */
    String getCaption();

    /**
     * Устанавливает название данной группы источников данных.
     */
    DataSourceGroup setCaption(String caption);

    /**
     * Получение типа объекта, которому принадлежит данная группа источников данных.
     */
    ObjectType getObjectType();

    /**
     * Получение источников данных принадлежащих данной группе.
     */
    DataSourceList getDataSources();

    /**
     * Получение источников данных принадлежащих данной группе.
     */
    DataSourceList getDataSources(DataSourceCriteria criteria);

}
