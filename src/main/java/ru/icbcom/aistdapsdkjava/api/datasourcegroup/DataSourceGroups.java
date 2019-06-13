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

import ru.icbcom.aistdapsdkjava.impl.datasourcegroup.DefaultDataSourceGroupCriteria;

/**
 * Статический вспомогательный класс для работы с объектами связанными с {@link DataSourceGroup}. Например:
 * <pre>
 * <b>DataSourceGroups.criteria()</b>
 *      .orderByCaption().ascending()
 *      .build();
 * </pre>
 */
public final class DataSourceGroups {

    /**
     * Возвращает новый экземпляр {@link DataSourceGroupCriteria} используемый для настройки параметров запроса коллекции объектов {@link DataSourceGroup}.
     *
     * @return новый экземпляр {@link DataSourceGroupCriteria}
     */
    public static DataSourceGroupCriteria criteria() {
        return new DefaultDataSourceGroupCriteria();
    }

}
