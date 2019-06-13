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
