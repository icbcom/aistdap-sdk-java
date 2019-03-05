package ru.icbcom.aistdapsdkjava.impl.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import ru.icbcom.aistdapsdkjava.impl.auth.response.AuthenticationResponse;
import ru.icbcom.aistdapsdkjava.impl.auth.response.DefaultAuthenticationResponse;
import ru.icbcom.aistdapsdkjava.impl.datastore.DummyDataStore;
import ru.icbcom.aistdapsdkjava.impl.mapper.ObjectMappers;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


class AuthenticationResponseDeserializationTest {

    private ObjectMapper objectMapper = ObjectMappers.create(new DummyDataStore());

    @Test
    void deserializationShouldWorkProperly() throws IOException {
        String json =
                "{\n" +
                        "    \"accessToken\": \"eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJhaXN0ZGFwLmljYmNvbS5ydSIsInN1YiI6IkFkbWluIiwic2NvcGUiOiJBRE1JTiIsInVzZXJJZCI6MywidHlwZSI6IkFDQ0VTUyIsImlhdCI6MTU1MTI3MjkzNCwiZXhwIjoxNTUxMjc2NTM0fQ.sm84zuWpwXvwmoMw7xkFpqcGrxjDQHOg1HOAaL-KzX8grsW_KzBTB_1cCDhsyMlxhguP2oV_6RPIDtrqoG7gOg\",\n" +
                        "    \"expiresIn\": 3600,\n" +
                        "    \"refreshToken\": \"eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJhaXN0ZGFwLmljYmNvbS5ydSIsInN1YiI6IkFkbWluIiwic2NvcGUiOiJBRE1JTiIsInVzZXJJZCI6MywidHlwZSI6IlJFRlJFU0giLCJoYXNoIjoiNzMwZjJhNmVlYWZmZDUwMzU1MDYzYTQ4OTBlM2Q3ZDQwMGI1OGQ1MjA5ZDY2YzI4OGI5OTUyZTE4MTcxNTkxMSIsImlhdCI6MTU1MTI3MjkzNCwiZXhwIjoxNTUxMjg3MzM0fQ.rK7AzAB4VMfVj9a6ZVT4CZ5CC97MOhn1BZ3szY0uEoyCr5XIdRQEqf3_JN7cJLzO_-YrLmWFJO0_W9F1bOYzzw\"\n" +
                        "}";
        AuthenticationResponse authenticationResponse = objectMapper.readValue(json, DefaultAuthenticationResponse.class);
        assertThat(authenticationResponse, allOf(
                hasProperty("accessToken", is("eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJhaXN0ZGFwLmljYmNvbS5ydSIsInN1YiI6IkFkbWluIiwic2NvcGUiOiJBRE1JTiIsInVzZXJJZCI6MywidHlwZSI6IkFDQ0VTUyIsImlhdCI6MTU1MTI3MjkzNCwiZXhwIjoxNTUxMjc2NTM0fQ.sm84zuWpwXvwmoMw7xkFpqcGrxjDQHOg1HOAaL-KzX8grsW_KzBTB_1cCDhsyMlxhguP2oV_6RPIDtrqoG7gOg")),
                hasProperty("expiresIn", is(3600)),
                hasProperty("refreshToken", is("eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJhaXN0ZGFwLmljYmNvbS5ydSIsInN1YiI6IkFkbWluIiwic2NvcGUiOiJBRE1JTiIsInVzZXJJZCI6MywidHlwZSI6IlJFRlJFU0giLCJoYXNoIjoiNzMwZjJhNmVlYWZmZDUwMzU1MDYzYTQ4OTBlM2Q3ZDQwMGI1OGQ1MjA5ZDY2YzI4OGI5OTUyZTE4MTcxNTkxMSIsImlhdCI6MTU1MTI3MjkzNCwiZXhwIjoxNTUxMjg3MzM0fQ.rK7AzAB4VMfVj9a6ZVT4CZ5CC97MOhn1BZ3szY0uEoyCr5XIdRQEqf3_JN7cJLzO_-YrLmWFJO0_W9F1bOYzzw"))
        ));
    }

}