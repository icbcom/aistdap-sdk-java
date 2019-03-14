package ru.icbcom.aistdapsdkjava.impl.objectType;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.ToString;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.UriTemplate;
import org.springframework.util.Assert;
import ru.icbcom.aistdapsdkjava.api.datasource.DataSource;
import ru.icbcom.aistdapsdkjava.api.datasource.DataSourceCriteria;
import ru.icbcom.aistdapsdkjava.api.datasource.DataSourceList;
import ru.icbcom.aistdapsdkjava.api.datasource.DataSources;
import ru.icbcom.aistdapsdkjava.api.exception.AistDapBackendException;
import ru.icbcom.aistdapsdkjava.api.exception.LinkNotFoundException;
import ru.icbcom.aistdapsdkjava.api.objecttype.Attribute;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectType;
import ru.icbcom.aistdapsdkjava.api.objecttype.Section;
import ru.icbcom.aistdapsdkjava.impl.datasource.DefaultDataSource;
import ru.icbcom.aistdapsdkjava.impl.datasource.DefaultDataSourceList;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.resource.AbstractSavableResource;
import ru.icbcom.aistdapsdkjava.impl.utils.LinkUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@ToString
public class DefaultObjectType extends AbstractSavableResource implements ObjectType {

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

    @Override
    public void delete() {
        getDataStore().delete(this);
    }

    // TODO: Протестировать новые методы.

    @Override
    @JsonIgnore
    public DataSourceList getDataSources() {
        return getDataSources(DataSources.criteria());
    }

    @Override
    @JsonIgnore
    public DataSourceList getDataSources(DataSourceCriteria criteria) {
        Link dataSourcesLink = getDataSourcesLink();
        return getDataStore().getResource(dataSourcesLink, DefaultDataSourceList.class, criteria);
    }

    private Link getDataSourcesLink() {
        return getLink("dap:dataSources").orElseThrow(
                () -> new LinkNotFoundException("Link 'dap:dataSources' was not found in the current ObjectType object. Some methods " +
                        "may only be called on ObjectType objects that have already been persisted and have an existing 'dap:dataSources' link.", null, "dap:dataSources"));
    }

    @Override
    public DataSource createDataSource(DataSource dataSource) {
        Link dataSourcesLink = getDataSourcesLink();
        dataSource.setObjectTypeId(getId());
        return getDataStore().create(dataSourcesLink, dataSource);
    }

    @Override
    public Optional<DataSource> getDataSourceById(Long dataSourceId) {
        Assert.notNull(dataSourceId, "dataSourceId cannot be null");
        Link dataSourcesLink = getDataSourcesLink();
        Link singleDataSourceLink = LinkUtils.appendLongIdToLink(dataSourcesLink, dataSourceId);
        return getSingleDataSource(singleDataSourceLink);
    }

    private Optional<DataSource> getSingleDataSource(Link singleDataSourceLink) {
        try {
            DefaultDataSource dataSource = getDataStore().getResource(singleDataSourceLink, DefaultDataSource.class);
            return Optional.ofNullable(dataSource);
        } catch (AistDapBackendException e) {
            if (e.getStatus() == 404) {
                return Optional.empty();
            } else {
                throw e;
            }
        }
    }

}
