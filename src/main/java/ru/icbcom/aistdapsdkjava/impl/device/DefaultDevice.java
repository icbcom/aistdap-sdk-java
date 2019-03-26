package ru.icbcom.aistdapsdkjava.impl.device;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.hateoas.Link;
import org.springframework.util.Assert;
import ru.icbcom.aistdapsdkjava.api.device.Device;
import ru.icbcom.aistdapsdkjava.api.exception.AistDapSdkException;
import ru.icbcom.aistdapsdkjava.api.exception.LinkNotFoundException;
import ru.icbcom.aistdapsdkjava.api.exception.NotPersistedException;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectType;
import ru.icbcom.aistdapsdkjava.api.physicalstructure.PhysicalStructureObject;
import ru.icbcom.aistdapsdkjava.api.resource.Resource;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.objectType.DefaultObjectType;
import ru.icbcom.aistdapsdkjava.impl.physicalstructure.DefaultPhysicalStructureObject;
import ru.icbcom.aistdapsdkjava.impl.resource.AbstractInstanceResource;
import ru.icbcom.aistdapsdkjava.impl.resource.AbstractResource;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@ToString
@EqualsAndHashCode(callSuper = false)
public class DefaultDevice extends AbstractInstanceResource implements Device {

    public static final String ID_PROPERTY = "id";
    public static final String NAME_PROPERTY = "name";

    private Long id;
    private Long objectTypeId;
    private String name;
    private Map<String, String> attributeValues = new LinkedHashMap<>();

    public DefaultDevice(@JacksonInject DataStore dataStore) {
        super(dataStore);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public Device setId(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public Long getObjectTypeId() {
        return objectTypeId;
    }

    @Override
    public Device setObjectTypeId(Long objectTypeId) {
        this.objectTypeId = objectTypeId;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Device setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public Map<String, String> getAttributeValues() {
        return attributeValues;
    }

    @Override
    public Device setAttributeValues(Map<String, String> attributeValues) {
        this.attributeValues = attributeValues;
        return this;
    }

    @Override
    @JsonIgnore
    public Optional<String> getAttributeValueByName(String name) {
        return Optional.ofNullable(attributeValues.get(name));
    }

    @Override
    @JsonIgnore
    public Device setAttributeValue(String attributeName, String attributeValue) {
        attributeValues.put(attributeName, attributeValue);
        return this;
    }

    @Override
    public Device removeAttributeValueByName(String attributeName) {
        attributeValues.remove(attributeName);
        return this;
    }

    @Override
    @JsonIgnore
    public ObjectType getObjectType() {
        Link objectTypeLink = getObjectTypeLink();
        return getDataStore().getResource(objectTypeLink, DefaultObjectType.class);
    }

    private Link getObjectTypeLink() {
        return getLink("dap:objectType").orElseThrow(
                () -> new LinkNotFoundException("Link 'dap:objectType' was not found in the current Device object. Method 'getObjectType()' " +
                        "may only be called on Device objects that have already been persisted and have an existing 'dap:objectType' link.", null, "dap:objectType"));
    }

    @Override
    @JsonIgnore
    public boolean isAttached() {
        return hasLink("dap:physicalStructureObjectAttachedTo");
    }

    @Override
    @JsonIgnore
    public Optional<PhysicalStructureObject> getPhysicalStructureObjectDeviceAttachedTo() {
        if (!hasLinks()) {
            throwNotPersistedException("getPhysicalStructureObjectDeviceAttachedTo()");
        }
        Optional<Link> attachedToLinkOptional = getLink("dap:physicalStructureObjectAttachedTo");
        if (attachedToLinkOptional.isPresent()) {
            PhysicalStructureObject physicalStructureObject = getDataStore().getResource(attachedToLinkOptional.get(), DefaultPhysicalStructureObject.class);
            return Optional.of(physicalStructureObject);
        } else {
            return Optional.empty();
        }
    }

    private void throwNotPersistedException(String methodName) {
        String errorMessage = String.format("There are no any existing links in this Device object. " +
                "Method '%s' may only be called on Device objects that have already been persisted.", methodName);
        throw new NotPersistedException(errorMessage);
    }

    @Override
    public void detach() {
        if (!hasLinks()) {
            throwNotPersistedException("detach()");
        }
        Optional<Link> detachLinkOptional = getLink("dap:detach");
        if (detachLinkOptional.isEmpty()) {
            throw new AistDapSdkException("Device is not attached to any physical structure object");
        }
        getDataStore().callMethod(detachLinkOptional.get());
    }

    @Override
    public void attach(Long physicalStructureObjectId) {
        Assert.notNull(physicalStructureObjectId, "physicalStructureObjectId argument cannot be null");
        if (!hasLinks()) {
            throwNotPersistedException("attach()");
        }
        Optional<Link> attachLinkOptional = getLink("dap:attach");
        if (attachLinkOptional.isEmpty()) {
            throw new AistDapSdkException("Device is already attached to physical structure object");
        }
        AttachDeviceMethodArgumentResource methodArgumentResource =
                new DefaultAttachDeviceMethodArgumentResource(getDataStore()).setPhysicalStructureObjectId(physicalStructureObjectId);
        getDataStore().callMethod(attachLinkOptional.get(), methodArgumentResource);
    }

}
