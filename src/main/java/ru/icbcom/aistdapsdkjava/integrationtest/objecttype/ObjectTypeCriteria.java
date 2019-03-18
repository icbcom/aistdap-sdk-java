package ru.icbcom.aistdapsdkjava.integrationtest.objecttype;

import ru.icbcom.aistdapsdkjava.api.query.Criteria;

/**
 *
 */
public interface ObjectTypeCriteria extends Criteria<ObjectTypeCriteria> {

    /**
     *
     * @return
     */
    ObjectTypeCriteria orderById();

    /**
     *
     * @return
     */
    ObjectTypeCriteria orderByName();

    /**
     *
     * @return
     */
    ObjectTypeCriteria orderByCaption();

}
