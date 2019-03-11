package ru.icbcom.aistdapsdkjava.impl.datastore.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import ru.icbcom.aistdapsdkjava.impl.datastore.auth.request.AuthenticationRequest;
import ru.icbcom.aistdapsdkjava.impl.datastore.auth.request.DefaultAuthenticationRequest;
import ru.icbcom.aistdapsdkjava.impl.datastore.DummyDataStore;
import ru.icbcom.aistdapsdkjava.impl.datastore.objectmapper.DefaultObjectMapperFactory;

class AuthenticationRequestSerializationTest {

    private ObjectMapper objectMapper = new DefaultObjectMapperFactory().create(new DummyDataStore());

    @Test
    void serializationShouldWorkProperly() throws JsonProcessingException, JSONException {
        AuthenticationRequest authenticationRequest = new DefaultAuthenticationRequest("someUsername", "somePassword");
        String json = objectMapper.writeValueAsString(authenticationRequest);
        JSONAssert.assertEquals("{\"username\":\"someUsername\",\"password\":\"somePassword\"}", json, true);
    }

}