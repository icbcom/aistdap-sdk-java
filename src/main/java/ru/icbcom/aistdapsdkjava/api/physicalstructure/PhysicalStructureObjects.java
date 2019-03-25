package ru.icbcom.aistdapsdkjava.api.physicalstructure;

import ru.icbcom.aistdapsdkjava.impl.physicalstructure.DefaulPhysicalStructureObjectCriteria;

public final class PhysicalStructureObjects {

    public static PhysicalStructureObjectCriteria criteria() {
        return new DefaulPhysicalStructureObjectCriteria();
    }

}
