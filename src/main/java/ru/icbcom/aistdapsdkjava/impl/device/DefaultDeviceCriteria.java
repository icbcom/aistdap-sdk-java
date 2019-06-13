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

package ru.icbcom.aistdapsdkjava.impl.device;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.icbcom.aistdapsdkjava.api.device.DeviceCriteria;
import ru.icbcom.aistdapsdkjava.impl.query.DefaultCriteria;

import static ru.icbcom.aistdapsdkjava.impl.device.DefaultDevice.ID_PROPERTY;
import static ru.icbcom.aistdapsdkjava.impl.device.DefaultDevice.NAME_PROPERTY;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DefaultDeviceCriteria extends DefaultCriteria<DeviceCriteria> implements DeviceCriteria {
    @Override
    public DeviceCriteria orderById() {
        return orderBy(ID_PROPERTY);
    }

    @Override
    public DeviceCriteria orderByName() {
        return orderBy(NAME_PROPERTY);
    }
}
