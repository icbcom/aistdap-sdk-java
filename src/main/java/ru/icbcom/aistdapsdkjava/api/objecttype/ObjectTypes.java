package ru.icbcom.aistdapsdkjava.api.objecttype;

import ru.icbcom.aistdapsdkjava.impl.objectType.DefaultObjectTypeCriteria;

public final class ObjectTypes {

    public static ObjectTypeCriteria criteria() {
        return new DefaultObjectTypeCriteria();
    }

}
