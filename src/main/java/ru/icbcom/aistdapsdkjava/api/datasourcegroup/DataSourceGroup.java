package ru.icbcom.aistdapsdkjava.api.datasourcegroup;

import ru.icbcom.aistdapsdkjava.api.resource.Deletable;
import ru.icbcom.aistdapsdkjava.api.resource.Resource;
import ru.icbcom.aistdapsdkjava.api.resource.Savable;

/**
 * Группа источников данных.
 */
public interface DataSourceGroup extends Resource, Savable, Deletable {

    /**
     * Возвращает идентификатор группы источников данных.
     */
    Long getDataSourceGroupId();

    /**
     * Устанавливает идентификатор группы источников данных.
     */
    DataSourceGroup setDataSourceGroupId(Long dataSourceGroupId);

    /**
     * Возвращает идентификатор типа объекта, которому принадлежит данная группа источников данных.
     */
    Long getObjectTypeId();

    /**
     * Устанавливает идентификатор типа объекта, которому принадлежит данная группа источников данных.
     */
    DataSourceGroup setObjectTypeId(Long objectTypeId);

    /**
     * Возвращает название данногй группы источников данных.
     */
    String getCaption();

    /**
     * Устанавливает название данной группы источников данных.
     */
    DataSourceGroup setCaption(String caption);

}
