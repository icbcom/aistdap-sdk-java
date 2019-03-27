package ru.icbcom.aistdapsdkjava.impl.device;

import org.springframework.hateoas.Link;
import org.springframework.util.Assert;
import ru.icbcom.aistdapsdkjava.api.device.*;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.resource.AbstractResourceActions;

import java.util.Optional;

public class DefaultDeviceActions extends AbstractResourceActions implements DeviceActions {

    public DefaultDeviceActions(Link baseLink, DataStore dataStore) {
        super(baseLink, dataStore);
    }

    @Override
    public DeviceList getAll() {
        return getAll(Devices.criteria());
    }

    @Override
    public DeviceList getAll(DeviceCriteria criteria) {
        Link devicesLink = getRootResourceLink("dap:devices");
        return dataStore.getResource(devicesLink, DefaultDeviceList.class, criteria);
    }

    @Override
    public Optional<Device> getById(Long deviceId) {
        Assert.notNull(deviceId, "deviceId cannot be null");
        Link devicesLink = getRootResourceLink("dap:devices");
        return getLongIdResource(devicesLink, deviceId, DefaultDevice.class);
    }

    @Override
    public Device create(Device device) {
        Link devicesLink = getRootResourceLink("dap:devices");
        return dataStore.create(devicesLink, device);
    }

}
