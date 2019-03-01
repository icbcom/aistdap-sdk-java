package ru.icbcom.aistdapsdkjava.impl.objectType;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.ToString;
import ru.icbcom.aistdapsdkjava.api.objecttype.Attribute;
import ru.icbcom.aistdapsdkjava.api.objecttype.Section;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.resource.AbstractResource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DefaultSection extends AbstractResource implements Section {

    private String name;
    private String caption;
    private String comment;
    private Collection<Attribute> attributes = new ArrayList<>();

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
