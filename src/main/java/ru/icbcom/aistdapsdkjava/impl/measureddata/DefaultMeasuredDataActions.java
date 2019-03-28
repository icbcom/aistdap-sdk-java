package ru.icbcom.aistdapsdkjava.impl.measureddata;

import org.springframework.hateoas.Link;
import ru.icbcom.aistdapsdkjava.api.measureddata.MeasuredData;
import ru.icbcom.aistdapsdkjava.api.measureddata.MeasuredDataActions;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.resource.AbstractResourceActions;

public class DefaultMeasuredDataActions extends AbstractResourceActions implements MeasuredDataActions {
    private Link cachedMeasuredDataLink;

    public DefaultMeasuredDataActions(Link baseLink, DataStore dataStore) {
        super(baseLink, dataStore);
    }

    @Override
    public void insert(MeasuredData measuredData) {
        if (cachedMeasuredDataLink == null) {
            cachedMeasuredDataLink = getRootResourceLink("dap:measuredData");
        }
        dataStore.callMethod(cachedMeasuredDataLink, measuredData);
    }
}
