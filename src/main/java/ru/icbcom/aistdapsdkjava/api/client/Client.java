package ru.icbcom.aistdapsdkjava.api.client;

import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectTypeCriteria;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectTypeList;
import ru.icbcom.aistdapsdkjava.api.resource.Resource;

public interface Client {

    /**
     *
     * @param clazz
     * @param <T>
     * @return
     */
    <T extends Resource> T instantiate(Class<T> clazz);

    /**
     *
     * @return
     */
    ObjectTypeList getObjectTypes();

    /**
     *
     * @return
     */
    ObjectTypeList getObjectTypes(ObjectTypeCriteria criteria);

}
