package ru.icbcom.aistdapsdkjava.impl.datasourcegroup;

import com.fasterxml.jackson.annotation.JacksonInject;
import lombok.ToString;
import ru.icbcom.aistdapsdkjava.api.datasourcegroup.DataSourceGroup;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.resource.AbstractInstanceResource;

@ToString
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

}
