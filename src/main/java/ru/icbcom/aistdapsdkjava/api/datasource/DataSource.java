package ru.icbcom.aistdapsdkjava.api.datasource;

import ru.icbcom.aistdapsdkjava.api.datasourcegroup.DataSourceGroup;
import ru.icbcom.aistdapsdkjava.integrationtest.objecttype.ObjectType;
import ru.icbcom.aistdapsdkjava.api.resource.Deletable;
import ru.icbcom.aistdapsdkjava.api.resource.Resource;
import ru.icbcom.aistdapsdkjava.api.resource.Savable;

/**
 * Источник данных.
 */
public interface DataSource extends Resource, Savable, Deletable {

    /**
     * Возвращает идентификатор данного источника данных.
     */
    Long getDataSourceId();

    /**
     * Устанавливает идентификатор источника данных.
     */
    DataSource setDataSourceId(Long dataSourceId);

    /**
     * Возвращает идентификатор типа объекта, которому принадлежит данный источник данных.
     */
    Long getObjectTypeId();

    /**
     * Устанавливает идентификатор типа объекта, которому принадлежит данный источник данных.
     */
    DataSource setObjectTypeId(Long objectTypeId);

    /**
     * Возвращает название данного источника данных.
     */
    String getCaption();

    /**
     * Устанавливает название данного источника данных.
     */
    DataSource setCaption(String caption);

    /**
     * Возвращает единицу измерения данного источника данных.
     */
    String getMeasureItem();

    /**
     * Устанавливает едииницу измерения данного источника данных.
     */
    DataSource setMeasureItem(String measureItem);

    /**
     * Возвращает идентификатор группы источников данных, которой принадлежит данный источник.
     */
    Long getDataSourceGroupId();

    /**
     * Устанавливает идентификатор группы источников данных, которой принадлежит данный источник.
     */
    DataSource setDataSourceGroupId(Long dataSourceGroupId);

    /**
     * Получение типа объекта, которому принадлежит данный источник данных.
     */
    ObjectType getObjectType();

    /**
     * Получение группы источников данный, к которой принадлежит данный источник.
     */
    DataSourceGroup getDataSourceGroup();

}
