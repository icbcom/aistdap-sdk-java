package ru.icbcom.aistdapsdkjava.impl.device;

import ru.icbcom.aistdapsdkjava.api.resource.Resource;

interface AttachDeviceMethodArgumentResource extends Resource {
    Long getPhysicalStructureObjectId();

    AttachDeviceMethodArgumentResource setPhysicalStructureObjectId(Long physicalStructureObjectId);
}