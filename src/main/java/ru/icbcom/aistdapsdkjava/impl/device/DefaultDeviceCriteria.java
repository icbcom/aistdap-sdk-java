package ru.icbcom.aistdapsdkjava.impl.device;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.icbcom.aistdapsdkjava.api.device.DeviceCriteria;
import ru.icbcom.aistdapsdkjava.impl.query.DefaultCriteria;

import static ru.icbcom.aistdapsdkjava.impl.device.DefaultDevice.ID_PROPERTY;
import static ru.icbcom.aistdapsdkjava.impl.device.DefaultDevice.NAME_PROPERTY;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DefaultDeviceCriteria extends DefaultCriteria<DeviceCriteria> implements DeviceCriteria {
    @Override
    public DeviceCriteria orderById() {
        return orderBy(ID_PROPERTY);
    }

    @Override
    public DeviceCriteria orderByName() {
        return orderBy(NAME_PROPERTY);
    }
}
