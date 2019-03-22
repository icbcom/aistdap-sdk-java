package ru.icbcom.aistdapsdkjava.impl.datastore.objectmapper;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleAbstractTypeResolver;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.hateoas.hal.Jackson2HalModule;
import ru.icbcom.aistdapsdkjava.api.datasource.DataSource;
import ru.icbcom.aistdapsdkjava.api.datasourcegroup.DataSourceGroup;
import ru.icbcom.aistdapsdkjava.api.device.Device;
import ru.icbcom.aistdapsdkjava.api.objecttype.Attribute;
import ru.icbcom.aistdapsdkjava.api.objecttype.EnumSetValue;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectType;
import ru.icbcom.aistdapsdkjava.api.objecttype.Section;
import ru.icbcom.aistdapsdkjava.impl.datasource.DefaultDataSource;
import ru.icbcom.aistdapsdkjava.impl.datasourcegroup.DefaultDataSourceGroup;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.device.DefaultDevice;
import ru.icbcom.aistdapsdkjava.impl.objectType.DefaultAttribute;
import ru.icbcom.aistdapsdkjava.impl.objectType.DefaultEnumSetValue;
import ru.icbcom.aistdapsdkjava.impl.objectType.DefaultObjectType;
import ru.icbcom.aistdapsdkjava.impl.objectType.DefaultSection;

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
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    private void configureDataStoreInjection(DataStore dataStore, ObjectMapper objectMapper) {
        InjectableValues.Std injectableValues = new InjectableValues.Std();
        injectableValues.addValue(DataStore.class, dataStore);
        objectMapper.setInjectableValues(injectableValues);
    }

    private void configureTypeResolution(ObjectMapper objectMapper) {
        SimpleModule module = new SimpleModule("TypeResolverModule", Version.unknownVersion());
        SimpleAbstractTypeResolver resolver = new SimpleAbstractTypeResolver();

        // TODO: Вынести эту информацию в отдельное место.
        resolver.addMapping(EnumSetValue.class, DefaultEnumSetValue.class);
        resolver.addMapping(Attribute.class, DefaultAttribute.class);
        resolver.addMapping(Section.class, DefaultSection.class);
        resolver.addMapping(ObjectType.class, DefaultObjectType.class);
        resolver.addMapping(DataSource.class, DefaultDataSource.class);
        resolver.addMapping(DataSourceGroup.class, DefaultDataSourceGroup.class);
        resolver.addMapping(Device.class, DefaultDevice.class);

        module.setAbstractTypes(resolver);
        objectMapper.registerModule(module);
    }

}
