package ru.icbcom.aistdapsdkjava.api.datasource;

import ru.icbcom.aistdapsdkjava.impl.datasource.DefaultDataSourceCriteria;

/**
 * Статический вспомогательный класс для работы с объектами связанными с {@link DataSource}. Например:
 * <pre>
 * <b>DataSources.criteria()</b>
 *      .orderByCaption().ascending()
 *      .build();
 * </pre>
 */
public final class DataSources {

    /**
     * Возвращает новый экземпляр {@link DataSourceCriteria} используемый для настройки параметров запроса коллекции объектов {@link DataSource}.
     *
     * @return новый экземпляр {@link DataSourceCriteria}
     */
    public static DataSourceCriteria criteria() {
        return new DefaultDataSourceCriteria();
    }

}
