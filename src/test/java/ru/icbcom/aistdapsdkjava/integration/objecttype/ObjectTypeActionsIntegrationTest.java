package ru.icbcom.aistdapsdkjava.integration.objecttype;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.verify.VerificationTimes;
import ru.icbcom.aistdapsdkjava.api.client.Client;
import ru.icbcom.aistdapsdkjava.api.client.Clients;
import ru.icbcom.aistdapsdkjava.integrationtest.objecttype.ObjectType;
import ru.icbcom.aistdapsdkjava.integrationtest.objecttype.ObjectTypeList;

import java.util.Iterator;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.matchers.Times.once;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.Parameter.param;
import static ru.icbcom.aistdapsdkjava.helper.ResourceHelper.loadResource;
import static ru.icbcom.aistdapsdkjava.helper.ResourceHelper.loadTemplatedResource;
import static ru.icbcom.aistdapsdkjava.integration.IntegrationTestConstants.MOCK_SERVER_PORT;

class ObjectTypeActionsIntegrationTest {

    private static ClientAndServer mockServer;

    @BeforeAll
    public static void startServer() {
        mockServer = startClientAndServer(MOCK_SERVER_PORT);
    }

    @AfterAll
    public static void stopServer() {
        mockServer.stop();
    }

    @BeforeEach
    void setup() {
        mockServer.reset();

        // Запрос корневой страницы с заголовком "Authorization".
        mockServer.when(request()
                .withMethod("GET")
                .withHeader("Authorization", "Bearer some-access-token")
                .withHeader("Accept", "application/json, application/*+json")
                .withPath("/"))
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/rootForAuthorizedUserResponse.json", Map.of("serverPort", mockServer.getLocalPort()))));
        // Запрос корневой страницы без заголовока "Authorization".
        mockServer.when(request()
                .withMethod("GET")
                .withHeader("Accept", "application/json, application/*+json")
                .withPath("/"))
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/rootForAnonymousUserResponse.json", Map.of("serverPort", mockServer.getLocalPort()))));
        // Запрос входа пользователя в систему.
        mockServer.when(request()
                .withMethod("POST")
                .withPath("/auth/login")
                .withHeader("Accept", "application/json, application/*+json"))
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadResource("integration/successfulLoginResponse.json")));
    }

    @Test
    void getAllShouldWorkProperly() {
        // Запрос получение типов объектов постранично.
        mockServer.when(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/*+json")
                        .withPath("/objectTypes"),
                once())
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/objectTypesFirstPageResponse.json", Map.of("serverPort", mockServer.getLocalPort()))));
        mockServer.when(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/*+json")
                        .withPath("/objectTypes")
                        .withQueryStringParameters(param("page", "1"), param("size", "2")),
                once())
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/objectTypesSecondPageResponse.json", Map.of("serverPort", mockServer.getLocalPort()))));

        Client client = Clients.builder()
                .setBaseUrl(String.format("http://127.0.0.1:%d/", mockServer.getLocalPort()))
                .setLogin("SomeLogin")
                .setPassword("somePassword")
                .build();
        ObjectTypeList objectTypeList = client.objectTypes().getAll();

        assertThat(objectTypeList, allOf(
                hasProperty("size", is(2L)),
                hasProperty("totalElements", is(4L)),
                hasProperty("totalPages", is(2L)),
                hasProperty("number", is(0L))
        ));
        Iterator<ObjectType> objectTypeListIterator = objectTypeList.iterator();

        assertTrue(objectTypeListIterator.hasNext());
        assertThat(objectTypeListIterator.next(), allOf(
                hasProperty("id", is(1L)),
                hasProperty("name", is("Mercury230/233")),
                hasProperty("caption", is("Счетчик э/э Меркурий 230/233")),
                hasProperty("device", is(true)),
                hasProperty("enabled", is(true))
        ));
        assertTrue(objectTypeListIterator.hasNext());
        assertThat(objectTypeListIterator.next(), allOf(
                hasProperty("id", is(3L)),
                hasProperty("name", is("Puma")),
                hasProperty("caption", is("УСПД \"Пума\"")),
                hasProperty("device", is(false)),
                hasProperty("enabled", is(true))
        ));
        assertTrue(objectTypeListIterator.hasNext());
        assertThat(objectTypeListIterator.next(), allOf(
                hasProperty("id", is(4L)),
                hasProperty("name", is("Puma-Can")),
                hasProperty("caption", is("CAN")),
                hasProperty("device", is(false)),
                hasProperty("enabled", is(true))
        ));
        assertTrue(objectTypeListIterator.hasNext());
        assertThat(objectTypeListIterator.next(), allOf(
                hasProperty("id", is(5L)),
                hasProperty("name", is("Puma-485")),
                hasProperty("caption", is("RS485")),
                hasProperty("device", is(false)),
                hasProperty("enabled", is(true))
        ));
        assertFalse(objectTypeListIterator.hasNext());

        mockServer.verify(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/*+json")
                        .withPath("/objectTypes"),
                VerificationTimes.exactly(2)
        );
        mockServer.verify(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/*+json")
                        .withPath("/objectTypes")
                        .withQueryStringParameters(param("page", "1"), param("size", "2")),
                VerificationTimes.exactly(1)
        );
    }

    // Проверка, что запрос с критериями успешно работает.


}