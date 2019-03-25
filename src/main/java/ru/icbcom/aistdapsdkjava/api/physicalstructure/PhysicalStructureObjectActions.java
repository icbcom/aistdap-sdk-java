package ru.icbcom.aistdapsdkjava.api.physicalstructure;

import java.util.Optional;

public interface PhysicalStructureObjectActions {

    PhysicalStructureObjectList getAllInRoot();

    PhysicalStructureObjectList getAllInRoot(PhysicalStructureObjectCriteria criteria);

    Optional<PhysicalStructureObject> getById(Long physicalStructureObjectId);

    PhysicalStructureObject createInRoot(PhysicalStructureObject physicalStructureObject);

}
