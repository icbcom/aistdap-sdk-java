package ru.icbcom.aistdapsdkjava.impl.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import ru.icbcom.aistdapsdkjava.impl.auth.request.DefaultRefreshTokenRequest;
import ru.icbcom.aistdapsdkjava.impl.auth.request.RefreshTokenRequest;
import ru.icbcom.aistdapsdkjava.impl.datastore.DummyDataStore;
import ru.icbcom.aistdapsdkjava.impl.mapper.ObjectMappers;

class DefaultRefreshTokenRequestSerializationTest {

    private ObjectMapper objectMapper = ObjectMappers.create(new DummyDataStore());

    @Test
    void serializationShouldWorkProperly() throws JsonProcessingException, JSONException {
        RefreshTokenRequest refreshTokenRequest = new DefaultRefreshTokenRequest("some-refresh-token");
        String json = objectMapper.writeValueAsString(refreshTokenRequest);
        JSONAssert.assertEquals("{\"refreshToken\":\"some-refresh-token\"}", json, true);
    }

}