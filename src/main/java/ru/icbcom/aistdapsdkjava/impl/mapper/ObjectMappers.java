package ru.icbcom.aistdapsdkjava.impl.mapper;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleAbstractTypeResolver;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.hateoas.hal.Jackson2HalModule;
import ru.icbcom.aistdapsdkjava.api.objecttype.Attribute;
import ru.icbcom.aistdapsdkjava.api.objecttype.EnumSetValue;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectType;
import ru.icbcom.aistdapsdkjava.api.objecttype.Section;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.objectType.DefaultAttribute;
import ru.icbcom.aistdapsdkjava.impl.objectType.DefaultEnumSetValue;
import ru.icbcom.aistdapsdkjava.impl.objectType.DefaultObjectType;
import ru.icbcom.aistdapsdkjava.impl.objectType.DefaultSection;

public class ObjectMappers {

    public static ObjectMapper create(DataStore dataStore) {
        ObjectMapper objectMapper = new ObjectMapper();
        configureMainParameters(objectMapper);
        configureTypeResolution(objectMapper);
        configureDataStoreInjection(dataStore, objectMapper);
        return objectMapper;
    }

    private static void configureMainParameters(ObjectMapper objectMapper) {
        objectMapper.registerModule(new Jackson2HalModule());
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    private static void configureDataStoreInjection(DataStore dataStore, ObjectMapper objectMapper) {
        InjectableValues.Std injectableValues = new InjectableValues.Std();
        injectableValues.addValue(DataStore.class, dataStore);
        objectMapper.setInjectableValues(injectableValues);
    }

    private static void configureTypeResolution(ObjectMapper objectMapper) {
        SimpleModule module = new SimpleModule("TypeResolverModule", Version.unknownVersion());
        SimpleAbstractTypeResolver resolver = new SimpleAbstractTypeResolver();
        resolver.addMapping(EnumSetValue.class, DefaultEnumSetValue.class);
        resolver.addMapping(Attribute.class, DefaultAttribute.class);
        resolver.addMapping(Section.class, DefaultSection.class);
        resolver.addMapping(ObjectType.class, DefaultObjectType.class);
        module.setAbstractTypes(resolver);
        objectMapper.registerModule(module);
    }

}
