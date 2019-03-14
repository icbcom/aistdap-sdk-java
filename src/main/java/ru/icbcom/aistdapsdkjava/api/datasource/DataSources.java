package ru.icbcom.aistdapsdkjava.api.datasource;

import ru.icbcom.aistdapsdkjava.impl.datasource.DefaultDataSourceCriteria;

public final class DataSources {

    public static DataSourceCriteria criteria() {
        return new DefaultDataSourceCriteria();
    }

}
