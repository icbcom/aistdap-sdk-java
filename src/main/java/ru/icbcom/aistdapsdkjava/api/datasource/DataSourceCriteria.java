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

import ru.icbcom.aistdapsdkjava.api.query.Criteria;

/**
 * {@link DataSource}-специфичный интрерфейс критериев {@link Criteria}.
 */
public interface DataSourceCriteria extends Criteria<DataSourceCriteria> {

    /**
     * Включает сортировку результатов запроса источников данных {@link DataSource} по полю {@code dataSourceId}.
     *
     * @return данный объект {@code this} для объединения вызовов в цепочку
     */
    DataSourceCriteria orderByDataSourceId();

    /**
     * Включает сортировку результатов запроса источников данных {@link DataSource} по полю {@code objectTypeId}.
     *
     * @return данный объект {@code this} для объединения вызовов в цепочку
     */
    DataSourceCriteria orderByObjectTypeId();

    /**
     * Включает сортировку результатов запроса источников данных {@link DataSource} по полю {@code caption}.
     *
     * @return данный объект {@code this} для объединения вызовов в цепочку
     */
    DataSourceCriteria orderByCaption();

    /**
     * Включает сортировку результатов запроса источников данных {@link DataSource} по полю {@code measureItem}.
     *
     * @return данный объект {@code this} для объединения вызовов в цепочку
     */
    DataSourceCriteria orderByMeasureItem();

}
