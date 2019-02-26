package ru.icbcom.aistdapsdkjava.api.client;

import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectTypeCriteria;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectTypeList;

public interface Client {

    ObjectTypeList getObjectTypes();

    ObjectTypeList getObjectTypes(ObjectTypeCriteria criteria);

}
