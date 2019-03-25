package ru.icbcom.aistdapsdkjava.api.device;

import java.util.Optional;

public interface DeviceActions {

    DeviceList getAll();

    DeviceList getAll(DeviceCriteria criteria);

    Optional<Device> getById(Long deviceId);

    Device create(Device device);

}
