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
