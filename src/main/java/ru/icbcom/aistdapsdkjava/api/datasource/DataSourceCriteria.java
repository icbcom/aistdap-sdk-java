package ru.icbcom.aistdapsdkjava.api.datasource;

import ru.icbcom.aistdapsdkjava.api.query.Criteria;

public interface DataSourceCriteria extends Criteria<DataSourceCriteria> {

    DataSourceCriteria orderByDataSourceId();

    DataSourceCriteria orderByObjectTypeId();

    DataSourceCriteria orderByCaption();

    DataSourceCriteria orderByMeasureItem();

}
