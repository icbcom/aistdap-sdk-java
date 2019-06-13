/*
 * Copyright © 2018-2019 Icbcom
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

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
