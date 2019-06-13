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

package ru.icbcom.aistdapsdkjava.impl.physicalstructure;

import org.springframework.hateoas.Link;
import org.springframework.util.Assert;
import ru.icbcom.aistdapsdkjava.api.physicalstructure.*;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.resource.AbstractResourceActions;

import java.util.Optional;

// TODO: Протестировать данный класс

public class DefaultPhysicalStructureObjectActions extends AbstractResourceActions implements PhysicalStructureObjectActions {
    public DefaultPhysicalStructureObjectActions(Link baseLink, DataStore dataStore) {
        super(baseLink, dataStore);
    }

    @Override
    public PhysicalStructureObjectList getAllInRoot() {
        return getAllInRoot(PhysicalStructureObjects.criteria());
    }

    @Override
    public PhysicalStructureObjectList getAllInRoot(PhysicalStructureObjectCriteria criteria) {
        Link physicalStructureLink = getRootResourceLink("dap:physicalStructure");
        return dataStore.getResource(physicalStructureLink, DefaultPhysicalStructureObjectList.class, criteria);
    }

    @Override
    public Optional<PhysicalStructureObject> getById(Long physicalStructureObjectId) {
        Assert.notNull(physicalStructureObjectId, "physicalStructureObjectId cannot be null");
        Link physicalStructureLink = getRootResourceLink("dap:physicalStructure");
        return getLongIdResource(physicalStructureLink, physicalStructureObjectId, DefaultPhysicalStructureObject.class);
    }

    @Override
    public PhysicalStructureObject createInRoot(PhysicalStructureObject physicalStructureObject) {
        Link physicalStructureLink = getRootResourceLink("dap:physicalStructure");
        return dataStore.create(physicalStructureLink, physicalStructureObject);
    }
}
