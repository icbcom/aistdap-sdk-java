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
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.hateoas.Link;
import org.springframework.util.Assert;
import ru.icbcom.aistdapsdkjava.api.datasource.DataSource;
import ru.icbcom.aistdapsdkjava.api.datasource.DataSourceCriteria;
import ru.icbcom.aistdapsdkjava.api.datasource.DataSourceList;
import ru.icbcom.aistdapsdkjava.api.datasource.DataSources;
import ru.icbcom.aistdapsdkjava.api.datasourcegroup.DataSourceGroup;
import ru.icbcom.aistdapsdkjava.api.datasourcegroup.DataSourceGroupCriteria;
import ru.icbcom.aistdapsdkjava.api.datasourcegroup.DataSourceGroupList;
import ru.icbcom.aistdapsdkjava.api.datasourcegroup.DataSourceGroups;
import ru.icbcom.aistdapsdkjava.api.exception.AistDapBackendException;
import ru.icbcom.aistdapsdkjava.api.exception.LinkNotFoundException;
import ru.icbcom.aistdapsdkjava.api.objecttype.Attribute;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectType;
import ru.icbcom.aistdapsdkjava.api.objecttype.Section;
import ru.icbcom.aistdapsdkjava.impl.datasource.DefaultDataSource;
import ru.icbcom.aistdapsdkjava.impl.datasource.DefaultDataSourceList;
import ru.icbcom.aistdapsdkjava.impl.datasourcegroup.DefaultDataSourceGroup;
import ru.icbcom.aistdapsdkjava.impl.datasourcegroup.DefaultDataSourceGroupList;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.resource.AbstractInstanceResource;
import ru.icbcom.aistdapsdkjava.impl.utils.LinkUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@ToString
@EqualsAndHashCode(callSuper = false)
public class DefaultObjectType extends AbstractInstanceResource implements ObjectType {

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
    @JsonInclude(JsonInclude.Include.NON_NULL)
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

    @Override
    @JsonIgnore
    public DataSourceGroupList getDataSourceGroups() {
        return getDataSourceGroups(DataSourceGroups.criteria());
    }

    @Override
    @JsonIgnore
    public DataSourceGroupList getDataSourceGroups(DataSourceGroupCriteria criteria) {
        Link dataSourceGroupsLink = getDataSourceGroupsLink();
        return getDataStore().getResource(dataSourceGroupsLink, DefaultDataSourceGroupList.class, criteria);
    }

    private Link getDataSourceGroupsLink() {
        return getLink("dap:dataSourceGroups").orElseThrow(
                () -> new LinkNotFoundException("Link 'dap:dataSourceGroups' was not found in the current ObjectType object. Some methods " +
                        "may only be called on ObjectType objects that have already been persisted and have an existing 'dap:dataSourceGroups' link.", null, "dap:dataSourceGroups"));
    }

    @Override
    public Optional<DataSourceGroup> getDataSourceGroupById(Long dataSourceGroupId) {
        Assert.notNull(dataSourceGroupId, "dataSourceId cannot be null");
        Link dataSourceGroupsLink = getDataSourceGroupsLink();
        Link singleDataSourceGroupLink = LinkUtils.appendLongIdToLink(dataSourceGroupsLink, dataSourceGroupId);
        return getSingleDataSourceGroup(singleDataSourceGroupLink);
    }

    private Optional<DataSourceGroup> getSingleDataSourceGroup(Link singleDataSourceGroupLink) {
        try {
            DataSourceGroup dataSourceGroup = getDataStore().getResource(singleDataSourceGroupLink, DefaultDataSourceGroup.class);
            return Optional.ofNullable(dataSourceGroup);
        } catch (AistDapBackendException e) {
            if (e.getStatus() == 404) {
                return Optional.empty();
            } else {
                throw e;
            }
        }
    }

    @Override
    public DataSourceGroup createDataSourceGroup(DataSourceGroup dataSourceGroup) {
        Link dataSourceGroupsLink = getDataSourceGroupsLink();
        dataSourceGroup.setObjectTypeId(getId());
        return getDataStore().create(dataSourceGroupsLink, dataSourceGroup);
    }

}
