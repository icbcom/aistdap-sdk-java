package ru.icbcom.aistdapsdkjava.api.objecttype;

import java.util.Optional;

/**
 * Интерфейс {@link ObjectTypeActions} содержит все основные функции для работы с типами объектов.
 */
public interface ObjectTypeActions {

    /**
     * Создание нового типа объекта в системе.
     */
    ObjectType create(ObjectType objectType);

    /**
     * Получение списка всех типов объектов зарегистрированных в системе.
     */
    ObjectTypeList getAll();

    /**
     * Получение списка всех типов объектов зарегистрированных в системе.
     */
    ObjectTypeList getAll(ObjectTypeCriteria criteria);

    /**
     * Получение списка активных ({@code enabled == true}) типов объектов, которые описывают устройства ({@code device == true}).
     */
    ObjectTypeList getAllEnabledDevices();

    /**
     * Получение списка активных ({@code enabled == true}) типов объектов, которые описывают устройства ({@code device == true}).
     */
    ObjectTypeList getAllEnabledDevices(ObjectTypeCriteria criteria);

    /**
     * Получение списка типов объектов, которые описывают устройства ({@code device == true}).
     */
    ObjectTypeList getAllDevices();

    /**
     * Получение списка типов объектов, которые описывают устройства ({@code device == true}).
     */
    ObjectTypeList getAllDevices(ObjectTypeCriteria criteria);

    /**
     * Получение списка активных ({@code enabled == true}) типов объектов.
     */
    ObjectTypeList getAllEnabled();

    /**
     * Получение списка активных ({@code enabled == true}) типов объектов.
     */
    ObjectTypeList getAllEnabled(ObjectTypeCriteria criteria);

    /**
     * Получение типа объекта по его идентификатору.
     */
    Optional<ObjectType> getById(Long objectTypeId);

}
