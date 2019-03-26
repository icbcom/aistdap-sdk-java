package ru.icbcom.aistdapsdkjava.impl.device;

import com.fasterxml.jackson.annotation.JacksonInject;
import lombok.EqualsAndHashCode;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.resource.AbstractResource;

@EqualsAndHashCode(callSuper = false)
public class DefaultAttachDeviceMethodArgumentResource extends AbstractResource implements AttachDeviceMethodArgumentResource {

    private Long physicalStructureObjectId;

    public DefaultAttachDeviceMethodArgumentResource(@JacksonInject DataStore dataStore) {
        super(dataStore);
    }

    @Override
    public Long getPhysicalStructureObjectId() {
        return physicalStructureObjectId;
    }

    @Override
    public AttachDeviceMethodArgumentResource setPhysicalStructureObjectId(Long physicalStructureObjectId) {
        this.physicalStructureObjectId = physicalStructureObjectId;
        return this;
    }

}
