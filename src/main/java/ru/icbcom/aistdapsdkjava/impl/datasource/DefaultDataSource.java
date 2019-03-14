package ru.icbcom.aistdapsdkjava.impl.datasource;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.ToString;
import org.springframework.hateoas.Link;
import ru.icbcom.aistdapsdkjava.api.datasource.DataSource;
import ru.icbcom.aistdapsdkjava.api.exception.LinkNotFoundException;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectType;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.objectType.DefaultObjectType;
import ru.icbcom.aistdapsdkjava.impl.resource.AbstractSavableResource;

// TODO: Протестировать данный класс.

@ToString
public class DefaultDataSource extends AbstractSavableResource implements DataSource {

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
    public void delete() {
        getDataStore().delete(this);
    }

    // TODO: Протестировать данный метод: getObjectType()

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
}
