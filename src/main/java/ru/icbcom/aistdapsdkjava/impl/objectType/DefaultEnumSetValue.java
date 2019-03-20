package ru.icbcom.aistdapsdkjava.impl.objectType;

import com.fasterxml.jackson.annotation.JacksonInject;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.icbcom.aistdapsdkjava.api.objecttype.EnumSetValue;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.resource.AbstractResource;

@ToString
@EqualsAndHashCode(callSuper = false)
public class DefaultEnumSetValue extends AbstractResource implements EnumSetValue {

    private int number;
    private String caption;

    public DefaultEnumSetValue(@JacksonInject DataStore dataStore) {
        super(dataStore);
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public String getCaption() {
        return caption;
    }

    @Override
    public EnumSetValue setNumber(int number) {
        this.number = number;
        return this;
    }

    @Override
    public EnumSetValue setCaption(String caption) {
        this.caption = caption;
        return this;
    }

}
