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
