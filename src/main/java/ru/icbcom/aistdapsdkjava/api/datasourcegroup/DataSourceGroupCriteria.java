package ru.icbcom.aistdapsdkjava.api.datasourcegroup;

import ru.icbcom.aistdapsdkjava.api.query.Criteria;

/**
 * {@link DataSourceGroup}-специфичный интрерфейс критериев {@link Criteria}.
 */
public interface DataSourceGroupCriteria extends Criteria<DataSourceGroupCriteria> {

    /**
     * Включает сортировку результатов запроса групп источников данных {@link DataSourceGroup} по полю {@code objectTypeId}.
     *
     * @return данный объект {@code this} для объединения вызовов в цепочку
     */
    DataSourceGroupCriteria orderByObjectTypeId();

    /**
     * Включает сортировку результатов запроса групп источников данных {@link DataSourceGroup} по полю {@code caption}.
     *
     * @return данный объект {@code this} для объединения вызовов в цепочку
     */
    DataSourceGroupCriteria orderByCaption();

}
