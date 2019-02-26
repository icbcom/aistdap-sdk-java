package ru.icbcom.aistdapsdkjava.api.objecttype;

import ru.icbcom.aistdapsdkjava.api.query.Criteria;

public interface ObjectTypeCriteria extends Criteria<ObjectTypeCriteria> {

    ObjectTypeCriteria orderById();

    ObjectTypeCriteria orderByName();

    ObjectTypeCriteria orderByCaption();

}
