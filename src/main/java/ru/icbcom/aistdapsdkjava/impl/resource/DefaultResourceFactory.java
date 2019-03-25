package ru.icbcom.aistdapsdkjava.impl.resource;

import ru.icbcom.aistdapsdkjava.api.exception.InstantiationException;
import ru.icbcom.aistdapsdkjava.api.exception.UnknownClassException;
import ru.icbcom.aistdapsdkjava.api.resource.Resource;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.registry.ImplementationClassRegistry;

import java.lang.reflect.Constructor;

public class DefaultResourceFactory implements ResourceFactory {

    private final DataStore dataStore;

    public DefaultResourceFactory(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public <T extends Resource> T instantiate(Class<T> clazz) {
        if (clazz == null) {
            throw new NullPointerException("Resource class cannot be null");
        }
        Class<T> implementationClass = getImplementationClass(clazz);
        Constructor<T> constructor = getConstructor(implementationClass);
        return instantiate(implementationClass, constructor);
    }

    private <T extends Resource> T instantiate(Class<T> implementationClass, Constructor<T> constructor) {
        try {
            return constructor.newInstance(dataStore);
        } catch (Exception e) {
            throw new InstantiationException("Unable to instantiate instance of the class: " + implementationClass.getName(), e);
        }
    }

    private <T extends Resource> Constructor<T> getConstructor(Class<T> implementationClass) {
        Constructor<T> constructor;
        try {
            constructor = implementationClass.getConstructor(DataStore.class);
        } catch (NoSuchMethodException e) {
            String errorMessage = String.format("Unable to find public constructor that takes exactly one argument of type '%s' " +
                    "for class: %s", DataStore.class.getName(), implementationClass.getName());
            throw new IllegalStateException(errorMessage, e);
        }
        return constructor;
    }

    private <T extends Resource> Class<T> getImplementationClass(Class<T> clazz) {
        if (clazz.isInterface()) {
            return convertToImplClass(clazz);
        }
        return clazz;
    }

    private <T extends Resource> Class<T> convertToImplClass(Class<T> clazz) {
        Class implementationClass = ImplementationClassRegistry.getImplementationClassFor(clazz);
        if (implementationClass == null) {
            throw new UnknownClassException("Implementation class not found for: " + clazz.getName());
        }
        return implementationClass;
    }

}
