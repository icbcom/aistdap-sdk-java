package ru.icbcom.aistdapsdkjava.api.device;

import ru.icbcom.aistdapsdkjava.api.query.Criteria;

/**
 * {@link Device}-специфичный интрерфейс критериев {@link Criteria}.
 */
public interface DeviceCriteria extends Criteria<DeviceCriteria> {

    /**
     * Включает сортировку результатов запроса устройств {@link Device} по полю {@code id}.
     *
     * @return данный объект {@code this} для объединения вызовов в цепочку
     */
    DeviceCriteria orderById();

    /**
     * Включает сортировку результатов запроса устройств {@link Device} по полю {@code name}.
     *
     * @return данный объект {@code this} для объединения вызовов в цепочку
     */
    DeviceCriteria orderByName();

}
