package ru.icbcom.aistdapsdkjava.impl.objectType;

import org.springframework.hateoas.Link;
import org.springframework.util.Assert;
import ru.icbcom.aistdapsdkjava.api.exception.AistDapBackendException;
import ru.icbcom.aistdapsdkjava.api.exception.LinkNotFoundException;
import ru.icbcom.aistdapsdkjava.api.objecttype.*;
import ru.icbcom.aistdapsdkjava.api.resource.VoidResource;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.resource.AbstractResourceActions;
import ru.icbcom.aistdapsdkjava.impl.resource.DefaultVoidResource;
import ru.icbcom.aistdapsdkjava.impl.utils.LinkUtils;

import java.util.Map;
import java.util.Optional;

public class DefaultObjectTypeActions extends AbstractResourceActions implements ObjectTypeActions {

    public DefaultObjectTypeActions(Link baseLink, DataStore dataStore) {
        super(baseLink, dataStore);
    }

    @Override
    public ObjectTypeList getAll() {
        return getAll(ObjectTypes.criteria());
    }

    @Override
    public ObjectTypeList getAll(ObjectTypeCriteria criteria) {
        Link objectTypesLink = getRootResourceLink("dap:objectTypes");
        return dataStore.getResource(objectTypesLink, DefaultObjectTypeList.class, criteria);
    }

    @Override
    public ObjectType createObjectType(ObjectType objectType) {
        Link objectTypesLink = getRootResourceLink("dap:objectTypes");
        return dataStore.create(objectTypesLink, objectType);
    }

    @Override
    public ObjectTypeList getAllEnabledDevices() {
        return getAllEnabledDevices(ObjectTypes.criteria());
    }

    @Override
    public ObjectTypeList getAllEnabledDevices(ObjectTypeCriteria criteria) {
        Link objectTypesLink = getRootResourceLink("dap:objectTypes");
        Link objectTypesSearchLink = getResourceLink(objectTypesLink.expand(Map.of("size", "1")), "search");
        Link findAllEnabledDevicesLink = getResourceLink(objectTypesSearchLink, "dap:findAllEnabledDevices");
        return dataStore.getResource(findAllEnabledDevicesLink, DefaultObjectTypeList.class, criteria);
    }

    @Override
    public ObjectTypeList getAllDevices() {
        return getAllDevices(ObjectTypes.criteria());
    }

    @Override
    public ObjectTypeList getAllDevices(ObjectTypeCriteria criteria) {
        Link objectTypesLink = getRootResourceLink("dap:objectTypes");
        Link objectTypesSearchLink = getResourceLink(objectTypesLink.expand(Map.of("size", "1")), "search");
        Link findAllDevicesLink = getResourceLink(objectTypesSearchLink, "dap:findAllDevices");
        return dataStore.getResource(findAllDevicesLink, DefaultObjectTypeList.class, criteria);
    }

    @Override
    public ObjectTypeList getAllEnabled() {
        return getAllEnabled(ObjectTypes.criteria());
    }

    @Override
    public ObjectTypeList getAllEnabled(ObjectTypeCriteria criteria) {
        Link objectTypesLink = getRootResourceLink("dap:objectTypes");
        Link objectTypesSearchLink = getResourceLink(objectTypesLink.expand(Map.of("size", "1")), "search");
        Link findAllEnabledLink = getResourceLink(objectTypesSearchLink, "dap:findAllEnabled");
        return dataStore.getResource(findAllEnabledLink, DefaultObjectTypeList.class, criteria);
    }

    @Override
    public Optional<ObjectType> getById(Long objectTypeId) {
        Assert.notNull(objectTypeId, "objectTypeId cannot be null");
        Link objectTypesLink = getRootResourceLink("dap:objectTypes");
        return getLongIdResource(objectTypesLink, objectTypeId, DefaultObjectType.class);
    }

}
