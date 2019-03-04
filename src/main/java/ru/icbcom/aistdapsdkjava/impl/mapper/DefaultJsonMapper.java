package ru.icbcom.aistdapsdkjava.impl.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.icbcom.aistdapsdkjava.api.resource.Resource;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;

import java.io.IOException;

public class DefaultJsonMapper implements JsonMapper {

    private final ObjectMapper objectMapper;

    public DefaultJsonMapper(DataStore dataStore) {
        this.objectMapper = ObjectMappers.create(dataStore);
    }

    @Override
    public String toJson(Resource resource) {
        try {
            return objectMapper.writeValueAsString(resource);
        } catch (JsonProcessingException e) {
            throw new JsonMapperException(e.getMessage(), e);
        }
    }

    @Override
    public <T extends Resource> T fromJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new JsonMapperException(e.getMessage(), e);
        }
    }

}
