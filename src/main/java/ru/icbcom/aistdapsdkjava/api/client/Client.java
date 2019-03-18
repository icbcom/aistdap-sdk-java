package ru.icbcom.aistdapsdkjava.api.client;

import ru.icbcom.aistdapsdkjava.integrationtest.objecttype.ObjectTypeActions;
import ru.icbcom.aistdapsdkjava.api.resource.Resource;

public interface Client {

    <T extends Resource> T instantiate(Class<T> clazz);

    ObjectTypeActions objectTypes();

}
