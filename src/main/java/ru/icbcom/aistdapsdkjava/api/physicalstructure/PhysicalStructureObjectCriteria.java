package ru.icbcom.aistdapsdkjava.api.physicalstructure;

import ru.icbcom.aistdapsdkjava.api.query.Criteria;

public interface PhysicalStructureObjectCriteria extends Criteria<PhysicalStructureObjectCriteria> {

    PhysicalStructureObjectCriteria orderById();

    PhysicalStructureObjectCriteria orderByName();

}
