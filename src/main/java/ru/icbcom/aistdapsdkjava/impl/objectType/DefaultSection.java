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
import ru.icbcom.aistdapsdkjava.api.objecttype.Section;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.resource.AbstractResource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@ToString
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DefaultSection extends AbstractResource implements Section {

    private String name;
    private String caption;
    private String comment;
    private Collection<Attribute> attributes = new ArrayList<>();

    public DefaultSection(@JacksonInject DataStore dataStore) {
        super(dataStore);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Section setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String getCaption() {
        return caption;
    }

    @Override
    public Section setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    @Override
    public String getComment() {
        return comment;
    }

    @Override
    public Section setComment(String comment) {
        this.comment = comment;
        return this;
    }

    @Override
    public Collection<Attribute> getAttributes() {
        return attributes;
    }

    @Override
    public Section setAttributes(Collection<Attribute> attributes) {
        this.attributes = attributes;
        return this;
    }

    @Override
    public Optional<Attribute> getAttributeByName(String name) {
        return getAttributes() == null ? Optional.empty() :
                getAttributes().stream()
                        .filter(attribute -> attribute.getName().equals(name))
                        .findAny();
    }

    @Override
    public Section addAttribute(Attribute attribute) {
        attributes.add(attribute);
        return this;
    }

}
