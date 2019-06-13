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
