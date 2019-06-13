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

import ru.icbcom.aistdapsdkjava.impl.device.DefaultDeviceCriteria;

/**
 * Статический вспомогательный класс для работы с объектами связанными с {@link Device}. Например:
 * <pre>
 * <b>Devices.criteria()</b>
 *      .orderByName().ascending()
 *      .build();
 * </pre>
 */
public final class Devices {

    /**
     * Возвращает новый экземпляр {@link DeviceCriteria} используемый для настройки параметров запроса коллекции объектов {@link Device}.
     *
     * @return новый экземпляр {@link DeviceCriteria}
     */
    public static DeviceCriteria criteria() {
        return new DefaultDeviceCriteria();
    }

}
