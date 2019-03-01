package ru.icbcom.aistdapsdkjava.impl.objectmapper;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleAbstractTypeResolver;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.hateoas.hal.Jackson2HalModule;
import ru.icbcom.aistdapsdkjava.api.objecttype.EnumSetValue;
import ru.icbcom.aistdapsdkjava.impl.objectType.DefaultEnumSetValue;

public class ObjectMappers {

    public static ObjectMapper create() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Jackson2HalModule());
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        SimpleModule module = new SimpleModule("TypeResolverModule", Version.unknownVersion());
        SimpleAbstractTypeResolver resolver = new SimpleAbstractTypeResolver();
        resolver.addMapping(EnumSetValue.class, DefaultEnumSetValue.class);
        module.setAbstractTypes(resolver);
        objectMapper.registerModule(module);

        return objectMapper;
    }

}
