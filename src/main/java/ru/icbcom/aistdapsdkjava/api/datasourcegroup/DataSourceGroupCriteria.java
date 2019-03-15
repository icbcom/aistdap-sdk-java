package ru.icbcom.aistdapsdkjava.api.datasourcegroup;

import ru.icbcom.aistdapsdkjava.api.query.Criteria;

public interface DataSourceGroupCriteria extends Criteria<DataSourceGroupCriteria> {

    DataSourceGroupCriteria orderByObjectTypeId();

    DataSourceGroupCriteria orderByCaption();

}
