package ru.icbcom.aistdapsdkjava.api.client;

import ru.icbcom.aistdapsdkjava.api.device.DeviceActions;
import ru.icbcom.aistdapsdkjava.api.measureddata.MeasuredDataActions;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectTypeActions;
import ru.icbcom.aistdapsdkjava.api.physicalstructure.PhysicalStructureObject;
import ru.icbcom.aistdapsdkjava.api.physicalstructure.PhysicalStructureObjectActions;
import ru.icbcom.aistdapsdkjava.api.resource.Resource;

public interface Client {

    <T extends Resource> T instantiate(Class<T> clazz);

    ObjectTypeActions objectTypes();

    DeviceActions devices();

    PhysicalStructureObjectActions physicalStructure();

    MeasuredDataActions measuredData();

}
