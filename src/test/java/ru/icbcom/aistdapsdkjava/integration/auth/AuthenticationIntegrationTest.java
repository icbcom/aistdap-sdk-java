package ru.icbcom.aistdapsdkjava.integration.auth;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.matchers.MatchType;
import org.mockserver.verify.VerificationTimes;
import ru.icbcom.aistdapsdkjava.api.client.Client;
import ru.icbcom.aistdapsdkjava.api.client.Clients;
import ru.icbcom.aistdapsdkjava.api.exception.AistDapBackendException;
import ru.icbcom.aistdapsdkjava.integration.AbstractIntegrationTest;

import java.util.Map;

import static java.util.Collections.emptyMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.JsonBody.json;
import static ru.icbcom.aistdapsdkjava.helper.ResourceHelper.loadTemplatedResource;

@Slf4j
public class AuthenticationIntegrationTest extends AbstractIntegrationTest {

    @BeforeEach
    void setup() {
        mockServer.when(request()
                .withMethod("GET")
                .withHeader("Accept", "application/json, application/problem+json")
                .withPath("/objectTypes/1"))
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/objectType/objectTypeWithIdEqToOneResponse.json", Map.of("serverPort", mockServer.getLocalPort()))));
    }

    @Test
    void simpleLoginShouldWorkProperly() {
        Client client = Clients.builder()
                .setBaseUrl(String.format("http://127.0.0.1:%d/", mockServer.getLocalPort()))
                .setLogin("SomeLogin")
                .setPassword("somePassword")
                .build();
        client.objectTypes().getById(1L);

        mockServer.verify(request()
                        .withMethod("POST")
                        .withPath("/auth/login")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withBody(json("{\"username\": \"SomeLogin\", \"password\": \"somePassword\"}", MatchType.STRICT)),
                VerificationTimes.exactly(1));
        mockServer.verify(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes/1"),
                VerificationTimes.exactly(1));
    }

    @Test
    void simpleLoginShouldThrowAppropriateExceptionWhenCredentialAreIncorrect() {
        Client client = Clients.builder()
                .setBaseUrl(String.format("http://127.0.0.1:%d/", mockServer.getLocalPort()))
                .setLogin("SomeLogin")
                .setPassword("incorrectPassword")
                .build();
        AistDapBackendException exception = assertThrows(AistDapBackendException.class, () -> client.objectTypes().getById(1L));

        assertThat(exception, allOf(
                hasProperty("title", is("Unauthorized")),
                hasProperty("status", is(401)),
                hasProperty("detail", is("Bad credentials")),
                hasProperty("moreInfo", is(emptyMap()))
        ));
        mockServer.verify(request()
                        .withMethod("POST")
                        .withPath("/auth/login")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withBody(json("{\"username\": \"SomeLogin\", \"password\": \"incorrectPassword\"}", MatchType.STRICT)),
                VerificationTimes.exactly(1));
    }

    @Test
    void tokenRefreshShouldWorkProperly() throws InterruptedException {
        mockServer.clear(request().withMethod("POST").withPath("/auth/login"));
        mockServer.when(request()
                .withMethod("POST")
                .withPath("/auth/login")
                .withHeader("Accept", "application/json, application/problem+json")
                .withBody(json("{\"username\": \"SomeLogin\", \"password\": \"somePassword\"}", MatchType.STRICT)))
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody("{\"accessToken\": \"some-access-token\", \"expiresIn\": 62, \"refreshToken\": \"some-refresh-token\"}"));

        Client client = Clients.builder()
                .setBaseUrl(String.format("http://127.0.0.1:%d/", mockServer.getLocalPort()))
                .setLogin("SomeLogin")
                .setPassword("somePassword")
                .build();
        client.objectTypes().getById(1L);

        mockServer.verify(request()
                        .withMethod("POST")
                        .withPath("/auth/login")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withBody(json("{\"username\": \"SomeLogin\", \"password\": \"somePassword\"}", MatchType.STRICT)),
                VerificationTimes.exactly(1));
        mockServer.verify(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes/1"),
                VerificationTimes.exactly(1));

        Thread.sleep(3000);

        // На запрос обновления токена доступа с помощью refresh токена возвращается новый комплект токенов.
        mockServer.when(request()
                .withMethod("POST")
                .withPath("/auth/token")
                .withHeader("Accept", "application/json, application/problem+json")
                .withBody(json("{\"refreshToken\": \"some-refresh-token\"}", MatchType.STRICT)))
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody("{\"accessToken\": \"refreshed-access-token\", \"expiresIn\": 3600, \"refreshToken\": \"refreshed-refresh-access-token\"}"));

        client.objectTypes().getById(1L);

        mockServer.verify(request()
                        .withMethod("POST")
                        .withPath("/auth/token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withBody(json("{\"refreshToken\": \"some-refresh-token\"}", MatchType.STRICT)),
                VerificationTimes.exactly(1));
        mockServer.verify(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer refreshed-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes/1"),
                VerificationTimes.exactly(1));
    }

    @Test
    void ifRefreshTokenExpiredLoginShouldBeAttempted() throws InterruptedException {
        // При изначальном входе клиента в систему ему выдается токен с очень малым временем действия.
        mockServer.clear(request().withMethod("POST").withPath("/auth/login"));
        mockServer.when(request()
                .withMethod("POST")
                .withPath("/auth/login")
                .withHeader("Accept", "application/json, application/problem+json")
                .withBody(json("{\"username\": \"SomeLogin\", \"password\": \"somePassword\"}", MatchType.STRICT)))
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody("{\"accessToken\": \"some-access-token\", \"expiresIn\": 62, \"refreshToken\": \"some-refresh-token\"}"));

        Client client = Clients.builder()
                .setBaseUrl(String.format("http://127.0.0.1:%d/", mockServer.getLocalPort()))
                .setLogin("SomeLogin")
                .setPassword("somePassword")
                .build();
        client.objectTypes().getById(1L);

        mockServer.verify(request()
                        .withMethod("POST")
                        .withPath("/auth/login")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withBody(json("{\"username\": \"SomeLogin\", \"password\": \"somePassword\"}", MatchType.STRICT)),
                VerificationTimes.exactly(1));
        mockServer.verify(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes/1"),
                VerificationTimes.exactly(1));

        Thread.sleep(3000);

        // При попытке обновления токена с помощью refresh токена, будет возвращена ошибка "Refresh token expired".
        mockServer.when(request()
                .withMethod("POST")
                .withPath("/auth/token")
                .withHeader("Accept", "application/json, application/problem+json")
                .withBody(json("{\"refreshToken\": \"some-refresh-token\"}", MatchType.STRICT)))
                .respond(response()
                        .withHeader("Content-Type", "application/problem+json")
                        .withStatusCode(401)
                        .withBody("{ \"title\": \"Unauthorized\", \"status\": 401, \"detail\": \"Refresh token expired\" }"));

        // После получения ошибки обновления токена, клиент должен выполнить попытку входа по логину и паролю.
        mockServer.clear(request().withMethod("POST").withPath("/auth/login"));
        mockServer.when(request()
                .withMethod("POST")
                .withPath("/auth/login")
                .withHeader("Accept", "application/json, application/problem+json")
                .withBody(json("{\"username\": \"SomeLogin\", \"password\": \"somePassword\"}", MatchType.STRICT)))
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody("{\"accessToken\": \"second-login-attempt-access-token\", \"expiresIn\": 3600, \"refreshToken\": \"second-login-attempt-refresh-token\"}"));

        client.objectTypes().getById(1L);

        mockServer.verify(request()
                        .withMethod("POST")
                        .withPath("/auth/token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withBody(json("{\"refreshToken\": \"some-refresh-token\"}", MatchType.STRICT)),
                VerificationTimes.exactly(1));
        mockServer.verify(request()
                        .withMethod("POST")
                        .withPath("/auth/login")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withBody(json("{\"username\": \"SomeLogin\", \"password\": \"somePassword\"}", MatchType.STRICT)),
                VerificationTimes.exactly(1));
        mockServer.verify(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer second-login-attempt-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes/1"),
                VerificationTimes.exactly(1));
    }

}
