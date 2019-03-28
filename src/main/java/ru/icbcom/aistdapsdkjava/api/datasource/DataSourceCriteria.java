package ru.icbcom.aistdapsdkjava.api.datasource;

import ru.icbcom.aistdapsdkjava.api.query.Criteria;

/**
 * {@link DataSource}-специфичный интрерфейс критериев {@link Criteria}.
 */
public interface DataSourceCriteria extends Criteria<DataSourceCriteria> {

    /**
     * Включает сортировку результатов запроса источников данных {@link DataSource} по полю {@code dataSourceId}.
     *
     * @return данный объект {@code this} для объединения вызовов в цепочку
     */
    DataSourceCriteria orderByDataSourceId();

    /**
     * Включает сортировку результатов запроса источников данных {@link DataSource} по полю {@code objectTypeId}.
     *
     * @return данный объект {@code this} для объединения вызовов в цепочку
     */
    DataSourceCriteria orderByObjectTypeId();

    /**
     * Включает сортировку результатов запроса источников данных {@link DataSource} по полю {@code caption}.
     *
     * @return данный объект {@code this} для объединения вызовов в цепочку
     */
    DataSourceCriteria orderByCaption();

    /**
     * Включает сортировку результатов запроса источников данных {@link DataSource} по полю {@code measureItem}.
     *
     * @return данный объект {@code this} для объединения вызовов в цепочку
     */
    DataSourceCriteria orderByMeasureItem();

}
