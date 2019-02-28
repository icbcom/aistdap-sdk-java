package ru.icbcom.aistdapsdkjava.impl.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import ru.icbcom.aistdapsdkjava.impl.objectmapper.ObjectMappers;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationRequestSerializationTest {

    private ObjectMapper objectMapper = ObjectMappers.create();

    @Test
    void serializationShouldWorkProperly() throws JsonProcessingException, JSONException {
        AuthenticationRequest authenticationRequest = new DefaultAuthenticationRequest("someUsername", "somePassword");

        String json = objectMapper.writeValueAsString(authenticationRequest);
        JSONAssert.assertEquals("{\"username\":\"someUsername\",\"password\":\"somePassword\"}", json, true);
    }

}