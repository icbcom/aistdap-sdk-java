package ru.icbcom.aistdapsdkjava.impl.objectType;

import lombok.ToString;
import ru.icbcom.aistdapsdkjava.api.objecttype.EnumSetValue;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.resource.AbstractResource;

@ToString
public class DefaultEnumSetValue extends AbstractResource implements EnumSetValue {

    private int number;
    private String caption;

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
