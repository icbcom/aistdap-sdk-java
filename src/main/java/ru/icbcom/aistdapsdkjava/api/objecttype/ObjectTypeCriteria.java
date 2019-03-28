package ru.icbcom.aistdapsdkjava.api.objecttype;

import ru.icbcom.aistdapsdkjava.api.query.Criteria;

/**
 * {@link ObjectType}-специфичный интрерфейс критериев {@link Criteria}.
 */
public interface ObjectTypeCriteria extends Criteria<ObjectTypeCriteria> {

    /**
     * Включает сортировку результатов запроса типов объектов {@link ObjectType} по полю {@code id}.
     *
     * @return данный объект {@code this} для объединения вызовов в цепочку
     */
    ObjectTypeCriteria orderById();

    /**
     * Включает сортировку результатов запроса типов объектов {@link ObjectType} по полю {@code name}.
     *
     * @return данный объект {@code this} для объединения вызовов в цепочку
     */
    ObjectTypeCriteria orderByName();

    /**
     * Включает сортировку результатов запроса типов объектов {@link ObjectType} по полю {@code caption}.
     *
     * @return данный объект {@code this} для объединения вызовов в цепочку
     */
    ObjectTypeCriteria orderByCaption();

}
