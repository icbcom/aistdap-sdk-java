package ru.icbcom.aistdapsdkjava.impl.resource;

import ru.icbcom.aistdapsdkjava.api.resource.Resource;

public interface ResourceFactory {

    <T extends Resource> T instantiate(Class<T> clazz);

}