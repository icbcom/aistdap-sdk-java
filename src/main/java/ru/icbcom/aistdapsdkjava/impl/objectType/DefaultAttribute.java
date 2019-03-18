package ru.icbcom.aistdapsdkjava.impl.objectType;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.ToString;
import ru.icbcom.aistdapsdkjava.integrationtest.objecttype.Attribute;
import ru.icbcom.aistdapsdkjava.integrationtest.objecttype.AttributeType;
import ru.icbcom.aistdapsdkjava.integrationtest.objecttype.EnumSetValue;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.resource.AbstractResource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DefaultAttribute extends AbstractResource implements Attribute {

    private String name;
    private String caption;
    private AttributeType type;
    private String min;
    private String max;
    private String mask;
    private Collection<EnumSetValue> enumSetValues = new ArrayList<>();
    private String defaultValue;
    private String comment;

    public DefaultAttribute(@JacksonInject DataStore dataStore) {
        super(dataStore);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Attribute setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String getCaption() {
        return caption;
    }

    @Override
    public Attribute setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    @Override
    public AttributeType getType() {
        return type;
    }

    @Override
    public Attribute setType(AttributeType type) {
        this.type = type;
        return this;
    }

    @Override
    public String getMin() {
        return min;
    }

    @Override
    public Attribute setMin(String min) {
        this.min = min;
        return this;
    }

    @Override
    public String getMax() {
        return max;
    }

    @Override
    public Attribute setMax(String max) {
        this.max = max;
        return this;
    }

    @Override
    public String getMask() {
        return mask;
    }

    @Override
    public Attribute setMask(String mask) {
        this.mask = mask;
        return this;
    }

    @Override
    public Collection<EnumSetValue> getEnumSetValues() {
        return enumSetValues;
    }

    @Override
    public Attribute setEnumSetValues(Collection<EnumSetValue> enumSetValues) {
        this.enumSetValues = enumSetValues;
        return null;
    }

    @Override
    public Optional<EnumSetValue> getEnumSetValueByNumber(int number) {
        return getEnumSetValues() == null ? Optional.empty() :
                getEnumSetValues().stream()
                        .filter(enumSetValue -> enumSetValue.getNumber() == number)
                        .findAny();
    }

    @Override
    public String getDefaultValue() {
        return defaultValue;
    }

    @Override
    public Attribute setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    @Override
    public String getComment() {
        return comment;
    }

    @Override
    public Attribute setComment(String comment) {
        this.comment = comment;
        return this;
    }

    @Override
    public Attribute addEnumSetValue(EnumSetValue enumSetValue) {
        enumSetValues.add(enumSetValue);
        return this;
    }

}
