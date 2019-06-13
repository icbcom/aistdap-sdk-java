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

package ru.icbcom.aistdapsdkjava.impl.datasource;

import lombok.ToString;
import ru.icbcom.aistdapsdkjava.api.datasource.DataSourceCriteria;
import ru.icbcom.aistdapsdkjava.impl.query.DefaultCriteria;

import static ru.icbcom.aistdapsdkjava.impl.datasource.DefaultDataSource.*;

@ToString(callSuper = true)
public class DefaultDataSourceCriteria extends DefaultCriteria<DataSourceCriteria> implements DataSourceCriteria {
    @Override
    public DataSourceCriteria orderByDataSourceId() {
        return orderBy(DATA_SOURCE_ID_PROPERTY);
    }

    @Override
    public DataSourceCriteria orderByObjectTypeId() {
        return orderBy(OBJECT_TYPE_ID_PROPERTY);
    }

    @Override
    public DataSourceCriteria orderByCaption() {
        return orderBy(CAPTION_PROPERTY);
    }

    @Override
    public DataSourceCriteria orderByMeasureItem() {
        return orderBy(MEASURE_ITEM_PROPERTY);
    }
}
