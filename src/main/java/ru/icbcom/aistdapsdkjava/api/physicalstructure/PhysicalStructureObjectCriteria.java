package ru.icbcom.aistdapsdkjava.api.physicalstructure;

import ru.icbcom.aistdapsdkjava.api.query.Criteria;

/**
 * {@link PhysicalStructureObject}-специфичный интрерфейс критериев {@link Criteria}.
 */
public interface PhysicalStructureObjectCriteria extends Criteria<PhysicalStructureObjectCriteria> {

    /**
     * Включает сортировку результатов запроса объектов физической структуры {@link PhysicalStructureObject} по полю {@code id}.
     *
     * @return данный объект {@code this} для объединения вызовов в цепочку
     */
    PhysicalStructureObjectCriteria orderById();

    /**
     * Включает сортировку результатов запроса объектов физической структуры {@link PhysicalStructureObject} по полю {@code name}.
     *
     * @return данный объект {@code this} для объединения вызовов в цепочку
     */
    PhysicalStructureObjectCriteria orderByName();

}
