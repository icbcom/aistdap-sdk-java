package ru.icbcom.aistdapsdkjava.api.device;

import ru.icbcom.aistdapsdkjava.impl.device.DefaultDeviceCriteria;

public final class Devices {

    public static DeviceCriteria criteria() {
        return new DefaultDeviceCriteria();
    }

}
