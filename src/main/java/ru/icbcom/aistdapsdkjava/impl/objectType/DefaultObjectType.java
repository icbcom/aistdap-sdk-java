package ru.icbcom.aistdapsdkjava.impl.objectType;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.ToString;
import ru.icbcom.aistdapsdkjava.api.objecttype.Attribute;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectType;
import ru.icbcom.aistdapsdkjava.api.objecttype.Section;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.resource.AbstractResource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
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

    public DefaultObjectType(@JacksonInject DataStore dataStore) {
        super(dataStore);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public ObjectType setId(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ObjectType setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String getCaption() {
        return caption;
    }

    @Override
    public ObjectType setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    @Override
    public boolean isDevice() {
        return device;
    }

    @Override
    public ObjectType setDevice(boolean device) {
        this.device = device;
        return this;
    }

    @Override
    public Collection<Section> getSections() {
        return sections;
    }

    @Override
    public ObjectType setSections(Collection<Section> sections) {
        this.sections = sections;
        return this;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public ObjectType setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Override
    @JsonIgnore
    public Collection<Attribute> getAttributes() {
        return getSections() == null ? null :
                getSections().stream()
                        .filter(section -> section.getAttributes() != null)
                        .flatMap(section -> section.getAttributes().stream())
                        .collect(Collectors.toList());
    }

    @Override
    public Optional<Section> getSectionByName(String name) {
        return getSections() == null ? Optional.empty() :
                getSections().stream()
                        .filter(section -> section.getName().equals(name))
                        .findAny();
    }

    @Override
    public Optional<Attribute> getAttributeByName(String name) {
        return getAttributes() == null ? Optional.empty() :
                getAttributes().stream()
                        .filter(attribute -> attribute.getName().equals(name))
                        .findAny();
    }

    @Override
    public ObjectType addSection(Section section) {
        sections.add(section);
        return this;
    }

}
