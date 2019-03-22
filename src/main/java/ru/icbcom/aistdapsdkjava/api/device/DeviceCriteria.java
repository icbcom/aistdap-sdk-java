package ru.icbcom.aistdapsdkjava.api.device;

import ru.icbcom.aistdapsdkjava.api.query.Criteria;

public interface DeviceCriteria extends Criteria<DeviceCriteria> {

    DeviceCriteria orderById();

    DeviceCriteria orderByName();

}
