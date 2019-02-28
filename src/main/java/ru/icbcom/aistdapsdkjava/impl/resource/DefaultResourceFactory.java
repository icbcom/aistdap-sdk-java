package ru.icbcom.aistdapsdkjava.impl.resource;

import ru.icbcom.aistdapsdkjava.api.lang.InstantiationException;
import ru.icbcom.aistdapsdkjava.api.lang.UnknownClassException;
import ru.icbcom.aistdapsdkjava.api.objecttype.Attribute;
import ru.icbcom.aistdapsdkjava.api.objecttype.EnumSetValue;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectType;
import ru.icbcom.aistdapsdkjava.api.objecttype.Section;
import ru.icbcom.aistdapsdkjava.api.resource.Resource;
import ru.icbcom.aistdapsdkjava.api.resource.ResourceFactory;
import ru.icbcom.aistdapsdkjava.api.resource.VoidResource;
import ru.icbcom.aistdapsdkjava.impl.objectType.DefaultAttribute;
import ru.icbcom.aistdapsdkjava.impl.objectType.DefaultEnumSetValue;
import ru.icbcom.aistdapsdkjava.impl.objectType.DefaultObjectType;
import ru.icbcom.aistdapsdkjava.impl.objectType.DefaultSection;

import java.lang.reflect.Constructor;
import java.util.Map;

public class DefaultResourceFactory implements ResourceFactory {

    private final static Map<Class<? extends Resource>, Class<? extends Resource>> implementationMap = Map.of(
            EnumSetValue.class, DefaultEnumSetValue.class,
            Attribute.class, DefaultAttribute.class,
            Section.class, DefaultSection.class,
            ObjectType.class, DefaultObjectType.class,
            VoidResource.class, DefaultVoidResource.class
    );

    @Override
    public <T extends Resource> T instantiate(Class<T> clazz) {
        if (clazz == null) {
            throw new NullPointerException("Resource class cannot be null");
        }

        Class<T> implClass = getImplementationClass(clazz);

        Constructor<T> constructor;
        try {
            constructor = implClass.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException(e);
        }
        try {
            return constructor.newInstance();
        } catch (Exception e) {
            throw new InstantiationException("Unable to instantiate instance with constructor: " + implClass.getName(), e);
        }
    }

    private <T extends Resource> Class<T> getImplementationClass(Class<T> clazz) {
        if (clazz.isInterface()) {
            return convertToImplClass(clazz);
        }
        return clazz;
    }

    private <T extends Resource> Class<T> convertToImplClass(Class<T> clazz) {
        Class implementationClass = implementationMap.get(clazz);
        if (implementationClass == null) {
            throw new UnknownClassException("Implementation class not found for: " + clazz.getName());
        }
        return implementationClass;
    }

}
