package ru.icbcom.aistdapsdkjava.api.device;

import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectType;
import ru.icbcom.aistdapsdkjava.api.resource.Deletable;
import ru.icbcom.aistdapsdkjava.api.resource.Resource;
import ru.icbcom.aistdapsdkjava.api.resource.Savable;

import java.util.Map;
import java.util.Optional;

/**
 * Устройство.
 */
public interface Device extends Resource, Savable, Deletable {

    /**
     * Возвращает идентификатор данного устройства.
     *
     * @return идентификатор данного устройства
     */
    Long getId();

    /**
     * Устанавливает идентификатор данного устройства.
     *
     * @param id идентификатор устройства
     * @return данный объект {@code this} для объединения вызовов в цепочку
     */
    Device setId(Long id);

    /**
     * Возвращает идентификатор типа объекта данного устройства.
     *
     * @return идентификатор типа объекта данного устройства
     */
    Long getObjectTypeId();

    /**
     * Установка идентификатора типа ообъекта данного устройства.
     *
     * @param objectTypeId идентификатор типа объекта данного устройства
     * @return данный объект {@code this} для объединения вызовов в цепочку
     */
    Device setObjectTypeId(Long objectTypeId);

    /**
     * Возвращает название данного устройства.
     *
     * @return название данного истройства
     */
    String getName();

    /**
     * Устанавлиивает название данного устройства.
     *
     * @param name названиеи данного устройства
     * @return данный объект {@code this} для объединения вызовов в цепочку
     */
    Device setName(String name);

    /**
     * Возвращает значения атрибутов данного объекта.
     *
     * @return значения атрибутов данного объекта
     */
    Map<String, String> getAttributeValues();

    /**
     * Установка таблицы значений атрибутов данного объекта.
     *
     * @param attributeValues значения атрибутов данного объекта
     * @return данный объект {@code this} для объединения вызовов в цепочку
     */
    Device setAttributeValues(Map<String, String> attributeValues);

    /**
     * Возвращает значение одного атрибута с заданным именем {@code name}.
     *
     * @param name имя атрибута
     * @return {@link Optional} содержащий значение атрибута с соответствующим именем, если такой атрибут существует.
     * Если такого атрибута нет, то будет возвращен пустой объект {@link Optional}.
     */
    Optional<String> getAttributeValueByName(String name);

    /**
     * Установка значения атрибута с заданным именем.
     * <p>Если атрибут с заданным именем уже существует, то его значение просто перезапишется новым значением.
     * Если же атрибут с заданным именем не существет, то такой атрибут будет создан с соответствующим значением.</p>
     *
     * @param attributeName  имя атрибута
     * @param attributeValue значение атрибута
     * @return данный объект {@code this} для объединения вызовов в цепочку
     */
    Device setAttributeValue(String attributeName, String attributeValue);

    /**
     * Удаление значения атрибута по его имени.
     *
     * @param attributeName имя удаляемого атрибута
     * @return данный объект {@code this} для объединения вызовов в цепочку
     */
    Device removeAttributeValueByName(String attributeName);

    /**
     * Возвращает тип объекта данного устройства.
     *
     * @return тип объекта данного устройства
     */
    ObjectType getObjectType();

}