package ru.icbcom.aistdapsdkjava.impl.mapper;

import ru.icbcom.aistdapsdkjava.api.resource.Resource;

public interface JsonMapper {

    String toJson(Resource resource);

    <T extends Resource> T fromJson(String json, Class<T> clazz);

}
