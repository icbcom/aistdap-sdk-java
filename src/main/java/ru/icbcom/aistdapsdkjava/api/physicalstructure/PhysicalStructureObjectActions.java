package ru.icbcom.aistdapsdkjava.api.physicalstructure;

import java.util.Optional;

/**
 * Интерфейс {@link PhysicalStructureObjectActions} содержит все основные функции для работы с объектами физической структуры.
 */
public interface PhysicalStructureObjectActions {

    /**
     * Получение списка всех объектов физической структуры {@link PhysicalStructureObject} находящихся в корне.
     *
     * @return список устройств {@link PhysicalStructureObject} находящихся в корне
     */
    PhysicalStructureObjectList getAllInRoot();

    /**
     * Получение списка всех объектов физической структуры {@link PhysicalStructureObject} находящихся в корне.
     * Данный метод позволяет задавать {@link PhysicalStructureObject}-специфичные критерии запроса.
     *
     * @return список устройств {@link PhysicalStructureObject} находящихся в корне
     */
    PhysicalStructureObjectList getAllInRoot(PhysicalStructureObjectCriteria criteria);

    /**
     * Получение объекта физической структуры {@link PhysicalStructureObject} по его идентификатору.
     *
     * @param physicalStructureObjectId идентификатор объекта физической структуры {@link PhysicalStructureObject}
     * @return {@link Optional} содержащий объект физической структуры с соответствующим идентификатором, если такой объект существует.
     * Либо {@link Optional#empty()}, если объекта физической структуры с заданным идентификатором не существует.
     */
    Optional<PhysicalStructureObject> getById(Long physicalStructureObjectId);

    /**
     * Создание нового объекта физической структуры {@link PhysicalStructureObject} в корне дерева.
     *
     * @param physicalStructureObject создаваемый объект физической структуры.
     * @return объект физической структуры {@link PhysicalStructureObject} сохраненный в платформе
     */
    PhysicalStructureObject createInRoot(PhysicalStructureObject physicalStructureObject);

}
