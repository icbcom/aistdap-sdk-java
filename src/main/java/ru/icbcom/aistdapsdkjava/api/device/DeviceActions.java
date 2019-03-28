package ru.icbcom.aistdapsdkjava.api.device;

import java.util.Optional;

/**
 * Интерфейс {@link DeviceActions} содержит все основные функции для работы с устройствами.
 */
public interface DeviceActions {

    /**
     * Получение списка всех устройств {@link Device} в платформе.
     *
     * @return список устройств {@link Device}
     */
    DeviceList getAll();

    /**
     * Получение списка всех устройств {@link Device} в платформе. Данный метод позволяет задавать
     * {@link Device}-специфичные критерии запроса.
     *
     * @return список устройств {@link Device}
     */
    DeviceList getAll(DeviceCriteria criteria);

    /**
     * Получение устройства {@link Device} по его идентификатору.
     *
     * @param deviceId идентификатор устройства {@link Device}
     * @return {@link Optional} содержащий устройство с соответствующим идентификатором, если такое устройство существует.
     * Либо {@link Optional#empty()}, если устройства с заданным идентификатором не существует.
     */
    Optional<Device> getById(Long deviceId);

    /**
     * Создание нового устройства {@link Device}.
     *
     * @param device создаваемое устройство.
     * @return объект устройства {@link Device} сохраненного в платформе
     */
    Device create(Device device);

}
