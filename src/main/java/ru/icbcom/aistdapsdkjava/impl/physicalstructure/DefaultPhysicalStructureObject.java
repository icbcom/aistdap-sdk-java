package ru.icbcom.aistdapsdkjava.impl.physicalstructure;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.hateoas.Link;
import ru.icbcom.aistdapsdkjava.api.device.DeviceCriteria;
import ru.icbcom.aistdapsdkjava.api.device.DeviceList;
import ru.icbcom.aistdapsdkjava.api.device.Devices;
import ru.icbcom.aistdapsdkjava.api.exception.LinkNotFoundException;
import ru.icbcom.aistdapsdkjava.api.exception.NotPersistedException;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectType;
import ru.icbcom.aistdapsdkjava.api.physicalstructure.PhysicalStructureObject;
import ru.icbcom.aistdapsdkjava.api.physicalstructure.PhysicalStructureObjectCriteria;
import ru.icbcom.aistdapsdkjava.api.physicalstructure.PhysicalStructureObjectList;
import ru.icbcom.aistdapsdkjava.api.physicalstructure.PhysicalStructureObjects;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.device.DefaultDeviceList;
import ru.icbcom.aistdapsdkjava.impl.objectType.DefaultObjectType;
import ru.icbcom.aistdapsdkjava.impl.resource.AbstractInstanceResource;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

// TODO: Протестировать данный класс.
// TODO: Реализовать тиипизиирыванные attributeValues.

@ToString
@EqualsAndHashCode(callSuper = false)
public class DefaultPhysicalStructureObject extends AbstractInstanceResource implements PhysicalStructureObject {

    public static final String ID_PROPERTY = "id";
    public static final String NAME_PROPERTY = "name";

    private Long id;
    private Long objectTypeId;
    private String name;
    private Map<String, String> attributeValues = new LinkedHashMap<>();
    private Long descendantsCount;
    private Long devicesCount;

    public DefaultPhysicalStructureObject(@JacksonInject DataStore dataStore) {
        super(dataStore);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public PhysicalStructureObject setId(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public Long getObjectTypeId() {
        return objectTypeId;
    }

    @Override
    public PhysicalStructureObject setObjectTypeId(Long objectTypeId) {
        this.objectTypeId = objectTypeId;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public PhysicalStructureObject setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public Map<String, String> getAttributeValues() {
        return attributeValues;
    }

    @Override
    public PhysicalStructureObject setAttributeValues(Map<String, String> attributeValues) {
        this.attributeValues = attributeValues;
        return this;
    }

    @Override
    public Optional<String> getAttributeValueByName(String name) {
        return Optional.ofNullable(attributeValues.get(name));
    }

    @Override
    public PhysicalStructureObject setAttributeValue(String attributeName, String attributeValue) {
        attributeValues.put(attributeName, attributeValue);
        return this;
    }

    @Override
    public PhysicalStructureObject removeAttributeValueByName(String attributeName) {
        attributeValues.remove(attributeName);
        return this;
    }

    @Override
    @JsonIgnore
    public Long getDescendantsCount() {
        return descendantsCount;
    }

    @Override
    @JsonProperty
    public PhysicalStructureObject setDescendantsCount(Long descendantsCount) {
        this.descendantsCount = descendantsCount;
        return this;
    }

    @Override
    @JsonIgnore
    public Long getDevicesCount() {
        return devicesCount;
    }

    @Override
    @JsonProperty
    public PhysicalStructureObject setDevicesCount(Long devicesCount) {
        this.devicesCount = devicesCount;
        return this;
    }

    @Override
    public boolean hasDescendants() {
        return descendantsCount != null && descendantsCount > 0;
    }

    @Override
    public boolean hasAttachedDevices() {
        return devicesCount != null && devicesCount > 0;
    }

    @Override
    @JsonIgnore
    public ObjectType getObjectType() {
        Link objectTypeLink = getObjectTypeLink();
        return getDataStore().getResource(objectTypeLink, DefaultObjectType.class);
    }

    private Link getObjectTypeLink() {
        return getLink("dap:objectType").orElseThrow(
                () -> new LinkNotFoundException("Link 'dap:objectType' was not found in the current PhysicalStructureObject. Method 'getObjectType()' " +
                        "may only be called on PhysicalStructureObjects that have already been persisted and have an existing 'dap:objectType' link.", null, "dap:objectType"));
    }

    @Override
    @JsonIgnore
    public PhysicalStructureObjectList getDescendants() {
        return getDescendants(PhysicalStructureObjects.criteria());
    }

    @Override
    @JsonIgnore
    public PhysicalStructureObjectList getDescendants(PhysicalStructureObjectCriteria criteria) {
        Link descendantsLink = getDescendantsLink();
        return getDataStore().getResource(descendantsLink, DefaultPhysicalStructureObjectList.class, criteria);
    }

    private Link getDescendantsLink() {
        return getLink("dap:descendants").orElseThrow(
                () -> new LinkNotFoundException("Link 'dap:descendants' was not found in the current PhysicalStructureObject. Methods 'getDescendants()' and 'createDescendant()' " +
                        "may only be called on PhysicalStructureObjects that have already been persisted and have an existing 'dap:descendants' link.", null, "dap:descendants"));
    }

    @Override
    @JsonIgnore
    public DeviceList getAttachedDevices() {
        return getAttachedDevices(Devices.criteria());
    }

    @Override
    @JsonIgnore
    public DeviceList getAttachedDevices(DeviceCriteria criteria) {
        Link attachedDevicesLink = getAttachedDevicesLink();
        return getDataStore().getResource(attachedDevicesLink, DefaultDeviceList.class, criteria);
    }

    private Link getAttachedDevicesLink() {
        return getLink("dap:attachedDevices").orElseThrow(
                () -> new LinkNotFoundException("Link 'dap:attachedDevices' was not found in the current PhysicalStructureObject. Method 'getAttachedDevices()' " +
                        "may only be called on PhysicalStructureObjects that have already been persisted and have an existing 'dap:attachedDevices' link.", null, "dap:attachedDevices"));
    }

    @Override
    public PhysicalStructureObject createDescendant(PhysicalStructureObject physicalStructureObject) {
        Link descendantsLink = getDescendantsLink();
        return getDataStore().create(descendantsLink, physicalStructureObject);
    }

    @Override
    @JsonIgnore
    public Optional<PhysicalStructureObject> getParent() {
        if (!hasLinks()) {
            throw new NotPersistedException("There are no any existing links in this PhysicalStructureObject. " +
                    "Method 'getParent()' may only be called on PhysicalStructureObjects that have already been persisted.");
        }
        Optional<Link> parentLinkOptional = getLink("dap:parent");
        if (parentLinkOptional.isPresent()) {
            PhysicalStructureObject parent = getDataStore().getResource(parentLinkOptional.get(), DefaultPhysicalStructureObject.class);
            return Optional.of(parent);
        } else {
            return Optional.empty();
        }
    }

    @Override
    @JsonIgnore
    public boolean isInRoot() {
        return !hasLink("dap:parent");
    }
}
