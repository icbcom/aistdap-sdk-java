package ru.icbcom.aistdapsdkjava.impl.physicalstructure;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.icbcom.aistdapsdkjava.api.physicalstructure.PhysicalStructureObjectCriteria;
import ru.icbcom.aistdapsdkjava.impl.query.DefaultCriteria;

import static ru.icbcom.aistdapsdkjava.impl.physicalstructure.DefaultPhysicalStructureObject.ID_PROPERTY;
import static ru.icbcom.aistdapsdkjava.impl.physicalstructure.DefaultPhysicalStructureObject.NAME_PROPERTY;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DefaulPhysicalStructureObjectCriteria extends DefaultCriteria<PhysicalStructureObjectCriteria> implements PhysicalStructureObjectCriteria {
    @Override
    public PhysicalStructureObjectCriteria orderById() {
        return orderBy(ID_PROPERTY);
    }

    @Override
    public PhysicalStructureObjectCriteria orderByName() {
        return orderBy(NAME_PROPERTY);
    }
}
