package ru.icbcom.aistdapsdkjava.impl.objectType;

import com.fasterxml.jackson.annotation.JacksonInject;
import lombok.ToString;
import ru.icbcom.aistdapsdkjava.integrationtest.objecttype.EnumSetValue;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.resource.AbstractResource;

@ToString
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
