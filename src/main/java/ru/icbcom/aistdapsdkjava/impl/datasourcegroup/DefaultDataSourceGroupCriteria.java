package ru.icbcom.aistdapsdkjava.impl.datasourcegroup;

import lombok.ToString;
import ru.icbcom.aistdapsdkjava.api.datasourcegroup.DataSourceGroupCriteria;
import ru.icbcom.aistdapsdkjava.impl.query.DefaultCriteria;

import static ru.icbcom.aistdapsdkjava.impl.datasourcegroup.DefaultDataSourceGroup.CAPTION_PROPERTY;
import static ru.icbcom.aistdapsdkjava.impl.datasourcegroup.DefaultDataSourceGroup.OBJECT_TYPE_ID_PROPERTY;

@ToString(callSuper = true)
public class DefaultDataSourceGroupCriteria extends DefaultCriteria<DataSourceGroupCriteria> implements DataSourceGroupCriteria {
    @Override
    public DataSourceGroupCriteria orderByObjectTypeId() {
        return orderBy(OBJECT_TYPE_ID_PROPERTY);
    }

    @Override
    public DataSourceGroupCriteria orderByCaption() {
        return orderBy(CAPTION_PROPERTY);
    }
}
