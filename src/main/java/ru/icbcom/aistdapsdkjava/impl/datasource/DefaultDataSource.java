package ru.icbcom.aistdapsdkjava.impl.datasource;

import com.fasterxml.jackson.annotation.JacksonInject;
import lombok.ToString;
import ru.icbcom.aistdapsdkjava.api.datasource.DataSource;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.resource.AbstractSavableResource;

@ToString
public class DefaultDataSource extends AbstractSavableResource implements DataSource {

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
        throw new IllegalStateException("Not implemented yet");
    }

}
