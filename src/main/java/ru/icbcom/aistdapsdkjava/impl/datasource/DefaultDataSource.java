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

package ru.icbcom.aistdapsdkjava.impl.datasource;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.hateoas.Link;
import ru.icbcom.aistdapsdkjava.api.datasource.DataSource;
import ru.icbcom.aistdapsdkjava.api.datasourcegroup.DataSourceGroup;
import ru.icbcom.aistdapsdkjava.api.exception.LinkNotFoundException;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectType;
import ru.icbcom.aistdapsdkjava.impl.datasourcegroup.DefaultDataSourceGroup;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.objectType.DefaultObjectType;
import ru.icbcom.aistdapsdkjava.impl.resource.AbstractInstanceResource;

@ToString
@EqualsAndHashCode(callSuper = false)
public class DefaultDataSource extends AbstractInstanceResource implements DataSource {

    static final String DATA_SOURCE_ID_PROPERTY = "dataSourceId";
    static final String OBJECT_TYPE_ID_PROPERTY = "objectTypeId";
    static final String CAPTION_PROPERTY = "caption";
    static final String MEASURE_ITEM_PROPERTY = "measureItem";

    private Long dataSourceId;
    private Long objectTypeId;
    private String caption;
    private String measureItem;
    private Long dataSourceGroupId;

    public DefaultDataSource(@JacksonInject DataStore dataStore) {
        super(dataStore);
    }

    @Override
    public Long getDataSourceId() {
        return dataSourceId;
    }

    @Override
    public DataSource setDataSourceId(Long dataSourceId) {
        this.dataSourceId = dataSourceId;
        return this;
    }

    @Override
    public Long getObjectTypeId() {
        return objectTypeId;
    }

    @Override
    public DataSource setObjectTypeId(Long objectTypeId) {
        this.objectTypeId = objectTypeId;
        return this;
    }

    @Override
    public String getCaption() {
        return caption;
    }

    @Override
    public DataSource setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    @Override
    public String getMeasureItem() {
        return measureItem;
    }

    @Override
    public DataSource setMeasureItem(String measureItem) {
        this.measureItem = measureItem;
        return this;
    }

    @Override
    public Long getDataSourceGroupId() {
        return dataSourceGroupId;
    }

    @Override
    public DataSource setDataSourceGroupId(Long dataSourceGroupId) {
        this.dataSourceGroupId = dataSourceGroupId;
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
                () -> new LinkNotFoundException("Link 'dap:objectType' was not found in the current DataSource object. Method 'getObjectType()' " +
                        "may only be called on DataSource objects that have already been persisted and have an existing 'dap:objectType' link.", null, "dap:objectType"));
    }

    @Override
    @JsonIgnore
    public DataSourceGroup getDataSourceGroup() {
        Link dataSourceGroupLink = getDataSourceGroupLink();
        return getDataStore().getResource(dataSourceGroupLink, DefaultDataSourceGroup.class);
    }

    private Link getDataSourceGroupLink() {
        return getLink("dap:dataSourceGroup").orElseThrow(
                () -> new LinkNotFoundException("Link 'dap:dataSourceGroup' was not found in the current DataSource object. Method 'getDataSourceGroup()' " +
                        "may only be called on DataSource objects that have already been persisted and have an existing 'dap:dataSourceGroup' link.", null, "dap:dataSourceGroup"));
    }

}
