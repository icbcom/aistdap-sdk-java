package ru.icbcom.aistdapsdkjava.impl.objectType;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.icbcom.aistdapsdkjava.integrationtest.objecttype.ObjectTypeCriteria;
import ru.icbcom.aistdapsdkjava.impl.query.DefaultCriteria;

import static ru.icbcom.aistdapsdkjava.impl.objectType.DefaultObjectType.*;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DefaultObjectTypeCriteria extends DefaultCriteria<ObjectTypeCriteria> implements ObjectTypeCriteria {
    @Override
    public ObjectTypeCriteria orderById() {
        return orderBy(ID_PROPERTY);
    }

    @Override
    public ObjectTypeCriteria orderByName() {
        return orderBy(NAME_PROPERTY);
    }

    @Override
    public ObjectTypeCriteria orderByCaption() {
        return orderBy(CAPTION_PROPERTY);
    }
}
