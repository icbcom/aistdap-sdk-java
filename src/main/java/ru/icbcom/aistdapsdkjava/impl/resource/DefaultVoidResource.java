package ru.icbcom.aistdapsdkjava.impl.resource;

import com.fasterxml.jackson.annotation.JacksonInject;
import lombok.ToString;
import ru.icbcom.aistdapsdkjava.api.resource.VoidResource;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;

@ToString
public class DefaultVoidResource extends AbstractResource implements VoidResource {
    public DefaultVoidResource(@JacksonInject DataStore dataStore) {
        super(dataStore);
    }
}
