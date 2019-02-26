package ru.icbcom.aistdapsdkjava.api.objecttype;

import ru.icbcom.aistdapsdkjava.api.resource.CollectionResource;

public interface ObjectTypeList extends CollectionResource<ObjectType> {
    @Override
    ObjectTypeList first();

    @Override
    ObjectTypeList previous();

    @Override
    ObjectTypeList next();

    @Override
    ObjectTypeList last();
}
