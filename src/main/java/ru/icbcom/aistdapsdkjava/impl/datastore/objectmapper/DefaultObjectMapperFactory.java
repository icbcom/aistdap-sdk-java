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

package ru.icbcom.aistdapsdkjava.impl.datastore.objectmapper;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleAbstractTypeResolver;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.ClassKey;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.hateoas.hal.Jackson2HalModule;
import ru.icbcom.aistdapsdkjava.api.resource.Resource;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.registry.ImplementationClassRegistry;

public class DefaultObjectMapperFactory implements ObjectMapperFactory {

    @Override
    public ObjectMapper create(DataStore dataStore) {
        ObjectMapper objectMapper = new ObjectMapper();
        configureMainParameters(objectMapper);
        configureTypeResolution(objectMapper);
        configureDataStoreInjection(dataStore, objectMapper);
        return objectMapper;
    }

    private void configureMainParameters(ObjectMapper objectMapper) {
        objectMapper.registerModule(new Jackson2HalModule());
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    private void configureDataStoreInjection(DataStore dataStore, ObjectMapper objectMapper) {
        InjectableValues.Std injectableValues = new InjectableValues.Std();
        injectableValues.addValue(DataStore.class, dataStore);
        objectMapper.setInjectableValues(injectableValues);
    }

    private void configureTypeResolution(ObjectMapper objectMapper) {
        SimpleModule module = new SimpleModule("TypeResolverModule", Version.unknownVersion());
        SimpleAbstractTypeResolver resolver = new RegistryBasedSimpleAbstractTypeResolver();
        module.setAbstractTypes(resolver);
        objectMapper.registerModule(module);
    }

    private static class RegistryBasedSimpleAbstractTypeResolver extends SimpleAbstractTypeResolver {
        RegistryBasedSimpleAbstractTypeResolver() {
            ImplementationClassRegistry.getAllEntries().forEach(entry -> {
                Class<? extends Resource> interfaceClass = entry.getKey();
                Class<? extends Resource> implementationClass = entry.getValue();
                _mappings.put(new ClassKey(interfaceClass), implementationClass);
            });
        }
    }

}
