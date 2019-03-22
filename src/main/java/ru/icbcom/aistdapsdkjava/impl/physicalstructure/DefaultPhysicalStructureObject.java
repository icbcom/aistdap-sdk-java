package ru.icbcom.aistdapsdkjava.impl.physicalstructure;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectType;
import ru.icbcom.aistdapsdkjava.api.physicalstructure.PhysicalStructureObject;
import ru.icbcom.aistdapsdkjava.impl.resource.AbstractInstanceResource;

import java.util.Map;
import java.util.Optional;

// TODO: Протестировать данный класс.
// TODO: Реализовать тиипизиирыванные attributeValues.

@ToString
@EqualsAndHashCode(callSuper = false)
public class DefaultPhysicalStructureObject extends AbstractInstanceResource implements PhysicalStructureObject {



    @Override
    public Long getId() {
        return null;
    }

    @Override
    public PhysicalStructureObject setId(Long id) {
        return null;
    }

    @Override
    public Long getObjectTypeId() {
        return null;
    }

    @Override
    public PhysicalStructureObject setObjectTypeId(Long objectTypeId) {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public PhysicalStructureObject setName(String name) {
        return null;
    }

    @Override
    public Map<String, String> getAttributeValues() {
        return null;
    }

    @Override
    public PhysicalStructureObject setAttributeValues(Map<String, String> attributeValues) {
        return null;
    }

    @Override
    public Optional<String> getAttributeValueByName(String name) {
        return Optional.empty();
    }

    @Override
    public PhysicalStructureObject setAttributeValue(String attributeName, String attributeValue) {
        return null;
    }

    @Override
    public PhysicalStructureObject removeAttributeValueByName(String attributeName) {
        return null;
    }

    @Override
    public Long getDescendantsCount() {
        return null;
    }

    @Override
    public PhysicalStructureObject setDescendantsCount(Long descendantsCount) {
        return null;
    }

    @Override
    public Long getDevicesCount() {
        return null;
    }

    @Override
    public PhysicalStructureObject setDevicesCount(Long devicesCount) {
        return null;
    }

    @Override
    public boolean hasDescendants() {
        return false;
    }

    @Override
    public boolean hasAttachedDevices() {
        return false;
    }

    @Override
    public ObjectType getObjectType() {
        return null;
    }
}
