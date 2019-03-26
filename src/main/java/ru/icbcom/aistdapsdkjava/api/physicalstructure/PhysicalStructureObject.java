package ru.icbcom.aistdapsdkjava.api.physicalstructure;

import ru.icbcom.aistdapsdkjava.api.device.DeviceCriteria;
import ru.icbcom.aistdapsdkjava.api.device.DeviceList;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectType;
import ru.icbcom.aistdapsdkjava.api.resource.Deletable;
import ru.icbcom.aistdapsdkjava.api.resource.Resource;
import ru.icbcom.aistdapsdkjava.api.resource.Savable;

import java.util.Map;
import java.util.Optional;

/**
 * Объект физической структуры.
 */
public interface PhysicalStructureObject extends Resource, Savable, Deletable {

    /**
     * Возвращает идентификатор данного объекта физической структуры.
     *
     * @return идентификатор данного объекта физической структуры
     */
    Long getId();

    /**
     * Устанавливает идентификатор данного объекта физической структуры.
     *
     * @param id идентификатор объекта физической структуры
     * @return данный объект {@code this} для объединения вызовов в цепочку
     */
    PhysicalStructureObject setId(Long id);

    /**
     * Возвращает идентификатор типа объекта данного объекта физической структуры.
     *
     * @return идентификатор типа объекта данного объекта физической структуры
     */
    Long getObjectTypeId();

    /**
     * Установка идентификатора типа ообъекта данного объекта физической структуры.
     *
     * @param objectTypeId идентификатор типа объекта данного объекта физической структуры
     * @return данный объект {@code this} для объединения вызовов в цепочку
     */
    PhysicalStructureObject setObjectTypeId(Long objectTypeId);

    /**
     * Возвращает название данного объекта физической структуры.
     *
     * @return название данного объекта физической структуры
     */
    String getName();

    /**
     * Устанавлиивает название данного объекта физической структуры.
     *
     * @param name название данного объекта физической структуры
     * @return данный объект {@code this} для объединения вызовов в цепочку
     */
    PhysicalStructureObject setName(String name);

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
    PhysicalStructureObject setAttributeValues(Map<String, String> attributeValues);

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
    PhysicalStructureObject setAttributeValue(String attributeName, String attributeValue);

    /**
     * Удаление значения атрибута по его имени.
     *
     * @param attributeName имя удаляемого атрибута
     * @return данный объект {@code this} для объединения вызовов в цепочку
     */
    PhysicalStructureObject removeAttributeValueByName(String attributeName);

    /**
     * Возвращает количество дочерних объектов физической структуры для данного объекта.
     *
     * @return количество дочерних объектов физической структуры для данного объекта
     */
    Long getDescendantsCount();

    /**
     * Устанавливает количество дочерних объектов физической структуры для данного объекта.
     *
     * @param descendantsCount количество дочерних объектов физической структуры для данного объекта
     * @return данный объект {@code this} для объединения вызовов в цепочку
     */
    PhysicalStructureObject setDescendantsCount(Long descendantsCount);

    /**
     * Возвращает количество устройств привязанных к данному объекту физической структуры.
     *
     * @return количество устройств привязанных к данному объекту физической структуры
     */
    Long getDevicesCount();

    /**
     * Устанавливает количество устройств привязанных к данному объекту физической структуры.
     *
     * @param devicesCount количество устройств привязанных к данному объекту физической структуры
     * @return данный объект {@code this} для объединения вызовов в цепочку
     */
    PhysicalStructureObject setDevicesCount(Long devicesCount);

    /**
     * Проверяет, есть ли у данного объекта физической структуры дочерние объекты.
     * <p>Данная функция эквивалентна проверке {@code getDescendantsCount() != null && getDescendantsCount() != 0}. </p>
     *
     * @return true - есть дочерние объекты, false - дочерних объектов нет
     */
    boolean hasDescendants();

    /**
     * Проверяет, есть ли у данного объекта физической структуры привязанные устройства.
     * <p>Данная функция эквивалентна проверке {@code getDevicesCount() != null && getDevicesCount() != 0}. </p>
     *
     * @return true - есть привязанные устройства, false - привязанных устройств нет
     */
    boolean hasAttachedDevices();

    /**
     * Возвращает тип объекта данного объекта физической структуры.
     *
     * @return тип объекта данного объекта физической структуры
     */
    ObjectType getObjectType();

    /**
     * Возвращает список дочерних объектов физической структуры.
     *
     * @return список дочерних объектов физической структуры
     */
    PhysicalStructureObjectList getDescendants();

    /**
     * Возвращает список дочерних объектов физической структуры.
     *
     * @return список дочерних объектов физической структуры
     */
    PhysicalStructureObjectList getDescendants(PhysicalStructureObjectCriteria criteria);

    /**
     * Возвращает список устройств привязанных к данному объекту физической структуры.
     *
     * @return список устройств привязанных к данному объекту физической структуры
     */
    DeviceList getAttachedDevices();

    /**
     * Возвращает список устройств привязанных к данному объекту физической структуры.
     *
     * @return список устройств привязанных к данному объекту физической структуры
     */
    DeviceList getAttachedDevices(DeviceCriteria criteria);

    /**
     * Создание нового объекта физической структуры в качестве дочернего к данному.
     *
     * @param physicalStructureObject создаваемый объект физической структуры
     * @return созданный объект физической структуры
     */
    PhysicalStructureObject createDescendant(PhysicalStructureObject physicalStructureObject);

    /**
     * Возвращает родительский объект физической структуры.
     *
     * @return {@link Optional} содержащий родительский объект физической структуры, если данный объект находится не в
     * корне, иначе возвращается пустой {@link Optional}
     */
    Optional<PhysicalStructureObject> getParent();

    /**
     * Находится ли данный объект в корне дерева физической структуры.
     * <p>Данный метод возвращает правильное значение только для тех объектов, которые были сохранены или получены из
     * платформы (persisted objects).</p>
     *
     * @return true - данный объект находится в корне физической структуры, false - данный объект не находится в корне
     * физической структуры
     */
    boolean isInRoot();

}
