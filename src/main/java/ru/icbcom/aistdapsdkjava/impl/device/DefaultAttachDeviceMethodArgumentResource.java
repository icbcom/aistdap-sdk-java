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

import com.fasterxml.jackson.annotation.JacksonInject;
import lombok.EqualsAndHashCode;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.resource.AbstractResource;

@EqualsAndHashCode(callSuper = false)
public class DefaultAttachDeviceMethodArgumentResource extends AbstractResource implements AttachDeviceMethodArgumentResource {

    private Long physicalStructureObjectId;

    public DefaultAttachDeviceMethodArgumentResource(@JacksonInject DataStore dataStore) {
        super(dataStore);
    }

    @Override
    public Long getPhysicalStructureObjectId() {
        return physicalStructureObjectId;
    }

    @Override
    public AttachDeviceMethodArgumentResource setPhysicalStructureObjectId(Long physicalStructureObjectId) {
        this.physicalStructureObjectId = physicalStructureObjectId;
        return this;
    }

}
