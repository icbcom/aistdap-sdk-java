package ru.icbcom.aistdapsdkjava.impl.objectmapper;

import ru.icbcom.aistdapsdkjava.api.resource.Resource;

public class DefaultJsonMapper implements JsonMapper {
    @Override
    public String toJson(Resource resource) {
        return null;
    }

    @Override
    public <T extends Resource> T fromJson(String json, Class<T> clazz) {
        return null;
    }
}
