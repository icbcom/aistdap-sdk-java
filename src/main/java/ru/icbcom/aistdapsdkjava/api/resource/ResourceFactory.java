package ru.icbcom.aistdapsdkjava.api.resource;

public interface ResourceFactory {

    <T extends Resource> T instantiate(Class<T> clazz);

}