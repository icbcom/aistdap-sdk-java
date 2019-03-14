package ru.icbcom.aistdapsdkjava.api.objecttype;

import ru.icbcom.aistdapsdkjava.api.datasource.DataSource;
import ru.icbcom.aistdapsdkjava.api.datasource.DataSourceCriteria;
import ru.icbcom.aistdapsdkjava.api.datasource.DataSourceList;
import ru.icbcom.aistdapsdkjava.api.resource.Deletable;
import ru.icbcom.aistdapsdkjava.api.resource.Resource;
import ru.icbcom.aistdapsdkjava.api.resource.Savable;

import java.util.Collection;
import java.util.Optional;

/**
 * Тип объекта.
 */
public interface ObjectType extends Resource, Savable, Deletable {

    /**
     * Возвращает идентификатор данного типа объекта.
     */
    Long getId();

    /**
     * Устанавливает идентификатор данного типа объекта.
     */
    ObjectType setId(Long id);

    /**
     * Возвращает имя данного типа объекта.
     */
    String getName();

    /**
     * Устанавливает имя данного типа объекта.
     */
    ObjectType setName(String name);

    /**
     * Возвращает заголовок данного типа объекта.
     */
    String getCaption();

    /**
     * Устанавливает заголовок данного типа объекта
     */
    ObjectType setCaption(String caption);

    /**
     * Описывает ли данный тип объекта устройство или нет.
     *
     * @return true - данный тип объекта описывает устройство, false - данный тип объекта описывает объект физической структуры.
     */
    boolean isDevice();

    /**
     * Устанавливает признак описывающий является ли данный тип объекта устройством или нет.
     */
    ObjectType setDevice(boolean device);

    /**
     * Возвращает коллекцию секций данного типа объекта.
     */
    Collection<Section> getSections();

    /**
     * Устанавливает коллекцию секций данного типа объекта.
     */
    ObjectType setSections(Collection<Section> sections);

    /**
     * Включен ли данный тип объекта.
     * <p>Если тип объекта выключен, то нельзя создавать новые объекты данного типа.</p>
     *
     * @return true - данный тип объекта включен, false - данный тип объекта выключен.
     */
    boolean isEnabled();

    /**
     * Устанавливает признак описывающий включен ли данный тип объекта.
     */
    ObjectType setEnabled(boolean enabled);

    /**
     * Возвращает коллекцию всех атрибутов данного типа объекта.
     */
    Collection<Attribute> getAttributes();

    /**
     * Получение секции по ее названию для данного типа объекта.
     *
     * @param name название секции
     * @return {@link Optional} содержащий секцию с заданным именем (если такая секция существует), либо пустой {@link Optional}.
     */
    Optional<Section> getSectionByName(String name);

    /**
     * Получение атрибута по его названию для данного типа объекта.
     *
     * @param name название атрибута
     * @return {@link Optional} содержащий атрибут с заданным именем (если такой атрибут существует), либо пустой {@link Optional}.
     */
    Optional<Attribute> getAttributeByName(String name);

    /**
     * Добавляет секцию к данному типу объектов.
     */
    ObjectType addSection(Section section);

    /**
     * Получение списка источников {@link DataSource} данных принадлежащих данному типу объекта.
     */
    DataSourceList getDataSources();

    /**
     * Получение списка источников {@link DataSource} данных принадлежащих данному типу объекта.
     */
    DataSourceList getDataSources(DataSourceCriteria criteria);

    /**
     * Создание нового источника данных для данного типа объекта.
     */
    DataSource createDataSource(DataSource dataSource);

    /**
     * Получение источника данных для данного типа объекта по идентификатору источника данных.
     */
    Optional<DataSource> getDataSourceById(Long dataSourceId);

}