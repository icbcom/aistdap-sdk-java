package ru.icbcom.aistdapsdkjava.api.datasourcegroup;

import ru.icbcom.aistdapsdkjava.impl.datasourcegroup.DefaultDataSourceGroupCriteria;

public final class DataSourceGroups {

    public static DataSourceGroupCriteria criteria() {
        return new DefaultDataSourceGroupCriteria();
    }

}
