package ru.icbcom.aistdapsdkjava.impl.objectType;

import ru.icbcom.aistdapsdkjava.api.objecttype.Attribute;
import ru.icbcom.aistdapsdkjava.api.objecttype.Section;

import java.util.Collection;
import java.util.Optional;

public class DefaultSection implements Section {
    @Override
    public String getName() {
        return null;
    }

    @Override
    public Section setName(String name) {
        return null;
    }

    @Override
    public String getCaption() {
        return null;
    }

    @Override
    public Section setCaption(String caption) {
        return null;
    }

    @Override
    public String getComment() {
        return null;
    }

    @Override
    public Section setComment(String comment) {
        return null;
    }

    @Override
    public Collection<Attribute> getAttributes() {
        return null;
    }

    @Override
    public Section setAttributes(Collection<Attribute> attributes) {
        return null;
    }

    @Override
    public Optional<Attribute> getAttributeByName(String name) {
        return Optional.empty();
    }

    @Override
    public Section addAttribute(Attribute attribute) {
        return null;
    }
}
