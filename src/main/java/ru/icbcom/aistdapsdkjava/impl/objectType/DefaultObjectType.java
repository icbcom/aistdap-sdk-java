package ru.icbcom.aistdapsdkjava.impl.objectType;

import ru.icbcom.aistdapsdkjava.api.objecttype.Attribute;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectType;
import ru.icbcom.aistdapsdkjava.api.objecttype.Section;
import ru.icbcom.aistdapsdkjava.impl.resource.AbstractResource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class DefaultObjectType extends AbstractResource implements ObjectType {

    static final String ID_PROPERTY = "id";
    static final String NAME_PROPERTY = "name";
    static final String CAPTION_PROPERTY = "caption";

    private Long id;
    private String name;
    private String caption;
    private boolean device;
    private Collection<Section> sections = new ArrayList<>();
    private boolean enabled;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public ObjectType setId(Long id) {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public ObjectType setName(String name) {
        return null;
    }

    @Override
    public String getCaption() {
        return null;
    }

    @Override
    public ObjectType setCaption(String caption) {
        return null;
    }

    @Override
    public boolean isDevice() {
        return false;
    }

    @Override
    public ObjectType setDevice(boolean device) {
        return null;
    }

    @Override
    public Collection<Section> getSections() {
        return null;
    }

    @Override
    public ObjectType setSections(Collection<Section> sections) {
        return null;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public ObjectType setEnabled(boolean enabled) {
        return null;
    }

    @Override
    public Collection<Attribute> getAttributes() {
        return null;
    }

    @Override
    public Optional<Section> getSectionByName(String name) {
        return Optional.empty();
    }

    @Override
    public Optional<Attribute> getAttributeByName(String name) {
        return Optional.empty();
    }

    @Override
    public ObjectType addSection(Section section) {
        return null;
    }

}
