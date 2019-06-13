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

package ru.icbcom.aistdapsdkjava.impl.datasourcegroup;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.hateoas.Link;
import ru.icbcom.aistdapsdkjava.api.datasource.DataSourceCriteria;
import ru.icbcom.aistdapsdkjava.api.datasource.DataSourceList;
import ru.icbcom.aistdapsdkjava.api.datasource.DataSources;
import ru.icbcom.aistdapsdkjava.api.datasourcegroup.DataSourceGroup;
import ru.icbcom.aistdapsdkjava.api.exception.LinkNotFoundException;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectType;
import ru.icbcom.aistdapsdkjava.impl.datasource.DefaultDataSourceList;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.objectType.DefaultObjectType;
import ru.icbcom.aistdapsdkjava.impl.resource.AbstractInstanceResource;

@ToString
@EqualsAndHashCode(callSuper = false)
public class DefaultDataSourceGroup extends AbstractInstanceResource implements DataSourceGroup {

    static final String CAPTION_PROPERTY = "caption";
    static final String OBJECT_TYPE_ID_PROPERTY = "objectTypeId";

    private Long dataSourceGroupId;
    private Long objectTypeId;
    private String caption;

    public DefaultDataSourceGroup(@JacksonInject DataStore dataStore) {
        super(dataStore);
    }

    @Override
    public Long getDataSourceGroupId() {
        return dataSourceGroupId;
    }

    @Override
    public DataSourceGroup setDataSourceGroupId(Long dataSourceGroupId) {
        this.dataSourceGroupId = dataSourceGroupId;
        return this;
    }

    @Override
    public Long getObjectTypeId() {
        return objectTypeId;
    }

    @Override
    public DataSourceGroup setObjectTypeId(Long objectTypeId) {
        this.objectTypeId = objectTypeId;
        return this;
    }

    @Override
    public String getCaption() {
        return caption;
    }

    @Override
    public DataSourceGroup setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    @Override
    @JsonIgnore
    public ObjectType getObjectType() {
        Link objectTypeLink = getObjectTypeLink();
        return getDataStore().getResource(objectTypeLink, DefaultObjectType.class);
    }

    private Link getObjectTypeLink() {
        return getLink("dap:objectType").orElseThrow(
                () -> new LinkNotFoundException("Link 'dap:objectType' was not found in the current DataSourceGroup object. Method 'getObjectType()' " +
                        "may only be called on DataSourceGroup objects that have already been persisted and have an existing 'dap:objectType' link.", null, "dap:objectType"));
    }

    @Override
    @JsonIgnore
    public DataSourceList getDataSources() {
        return getDataSources(DataSources.criteria());
    }

    @Override
    @JsonIgnore
    public DataSourceList getDataSources(DataSourceCriteria criteria) {
        Link dataSourcesInGroupLink = getDataSourcesInGroupLink();
        return getDataStore().getResource(dataSourcesInGroupLink, DefaultDataSourceList.class, criteria);
    }

    private Link getDataSourcesInGroupLink() {
        return getLink("dap:dataSourcesInGroup").orElseThrow(
                () -> new LinkNotFoundException("Link 'dap:dataSourcesInGroup' was not found in the current DataSourceGroup object. Some methods " +
                        "may only be called on DataSourceGroup objects that have already been persisted and have an existing 'dap:dataSourcesInGroup' link.", null, "dap:dataSourcesInGroup"));
    }

}
