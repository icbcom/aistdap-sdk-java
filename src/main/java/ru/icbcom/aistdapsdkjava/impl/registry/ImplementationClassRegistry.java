package ru.icbcom.aistdapsdkjava.impl.registry;

import ru.icbcom.aistdapsdkjava.api.datasource.DataSource;
import ru.icbcom.aistdapsdkjava.api.datasourcegroup.DataSourceGroup;
import ru.icbcom.aistdapsdkjava.api.device.Device;
import ru.icbcom.aistdapsdkjava.api.objecttype.Attribute;
import ru.icbcom.aistdapsdkjava.api.objecttype.EnumSetValue;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectType;
import ru.icbcom.aistdapsdkjava.api.objecttype.Section;
import ru.icbcom.aistdapsdkjava.api.physicalstructure.PhysicalStructureObject;
import ru.icbcom.aistdapsdkjava.api.resource.Resource;
import ru.icbcom.aistdapsdkjava.api.resource.VoidResource;
import ru.icbcom.aistdapsdkjava.impl.datasource.DefaultDataSource;
import ru.icbcom.aistdapsdkjava.impl.datasourcegroup.DefaultDataSourceGroup;
import ru.icbcom.aistdapsdkjava.impl.device.DefaultDevice;
import ru.icbcom.aistdapsdkjava.impl.objectType.DefaultAttribute;
import ru.icbcom.aistdapsdkjava.impl.objectType.DefaultEnumSetValue;
import ru.icbcom.aistdapsdkjava.impl.objectType.DefaultObjectType;
import ru.icbcom.aistdapsdkjava.impl.objectType.DefaultSection;
import ru.icbcom.aistdapsdkjava.impl.physicalstructure.DefaultPhysicalStructureObject;
import ru.icbcom.aistdapsdkjava.impl.resource.DefaultVoidResource;

import java.util.Map;
import java.util.Set;

public final class ImplementationClassRegistry {
    private final static Map<Class<? extends Resource>, Class<? extends Resource>> implementationMap = Map.of(
            EnumSetValue.class, DefaultEnumSetValue.class,
            Attribute.class, DefaultAttribute.class,
            Section.class, DefaultSection.class,
            ObjectType.class, DefaultObjectType.class,
            VoidResource.class, DefaultVoidResource.class,
            DataSource.class, DefaultDataSource.class,
            DataSourceGroup.class, DefaultDataSourceGroup.class,
            Device.class, DefaultDevice.class,
            PhysicalStructureObject.class, DefaultPhysicalStructureObject.class
    );

    public static Class<? extends Resource> getImplementationClassFor(Class<? extends Resource> interfaceClass) {
        return implementationMap.get(interfaceClass);
    }

    public static Set<Map.Entry<Class<? extends Resource>, Class<? extends Resource>>> getAllEntries() {
        return implementationMap.entrySet();
    }
}
