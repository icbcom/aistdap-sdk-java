package ru.icbcom.aistdapsdkjava.impl.objectmapper;

import ru.icbcom.aistdapsdkjava.api.resource.Resource;

public interface JsonMapper {

    String toJson(Resource resource);

    <T extends Resource> T fromJson(String json, Class<T> clazz);

}
