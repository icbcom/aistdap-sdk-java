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
