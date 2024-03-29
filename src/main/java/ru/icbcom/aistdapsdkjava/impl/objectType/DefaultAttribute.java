/*
 * Copyright © 2018-2019 Icbcom
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package ru.icbcom.aistdapsdkjava.impl.objectType;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.icbcom.aistdapsdkjava.api.objecttype.Attribute;
import ru.icbcom.aistdapsdkjava.api.objecttype.AttributeType;
import ru.icbcom.aistdapsdkjava.api.objecttype.EnumSetValue;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.resource.AbstractResource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@ToString
@EqualsAndHashCode(callSuper = false)
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
