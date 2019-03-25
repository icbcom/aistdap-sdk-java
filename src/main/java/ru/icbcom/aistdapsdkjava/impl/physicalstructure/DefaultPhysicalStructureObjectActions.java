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
