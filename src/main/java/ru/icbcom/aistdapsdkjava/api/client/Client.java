package ru.icbcom.aistdapsdkjava.api.client;

import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectType;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectTypeCriteria;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectTypeList;
import ru.icbcom.aistdapsdkjava.api.resource.Resource;

public interface Client {

    <T extends Resource> T instantiate(Class<T> clazz);

    ObjectTypeList getObjectTypes();

    ObjectTypeList getObjectTypes(ObjectTypeCriteria criteria);

    ObjectType createObjectType(ObjectType objectType);

}
