/*
 * Copyright © 2018-2019 Icbcom
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package ru.icbcom.aistdapsdkjava.integration;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.matchers.MatchType;
import org.mockserver.matchers.Times;
import org.mockserver.model.Not;
import org.mockserver.model.NottableString;
import ru.icbcom.aistdapsdkjava.api.client.Client;
import ru.icbcom.aistdapsdkjava.api.client.Clients;

import java.util.Map;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.JsonBody.json;
import static ru.icbcom.aistdapsdkjava.helper.ResourceHelper.loadResource;
import static ru.icbcom.aistdapsdkjava.helper.ResourceHelper.loadTemplatedResource;
import static ru.icbcom.aistdapsdkjava.integration.IntegrationTestConstants.MOCK_SERVER_PORT;

@Slf4j
public abstract class AbstractIntegrationTest {

    protected static ClientAndServer mockServer;

    protected Client client;

    @BeforeAll
    public static void startServer() {
        mockServer = startClientAndServer(MOCK_SERVER_PORT);
    }

    @AfterAll
    public static void stopServer() {
        mockServer.stop();
    }

    @BeforeEach
    void resetMockServerExpectations() {
        mockServer.reset();
    }

    @BeforeEach
    void initializeBasicMockServerExpectations() {
        // Запрос корневой страницы без заголовока "Authorization".
        mockServer.when(request()
                        .withMethod("GET")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withHeader(NottableString.not("Authorization"))
                        .withPath("/"),
                Times.once())
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/rootForAnonymousUserResponse.json", Map.of("serverPort", mockServer.getLocalPort()))));

        // Запрос входа пользователя в систему.
        mockServer.when(request()
                .withMethod("POST")
                .withPath("/auth/login")
                .withHeader("Accept", "application/json, application/problem+json")
                .withBody(json("{\"username\": \"SomeLogin\", \"password\": \"somePassword\"}", MatchType.STRICT)))
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadResource("integration/successfulLoginResponse.json")));

        // Запрос входа пользователя в систему в случае неверного ввода логина или пароля.
        mockServer.when(request()
                .withMethod("POST")
                .withPath("/auth/login")
                .withHeader("Accept", "application/json, application/problem+json")
                .withBody(Not.not(json("{\"username\": \"SomeLogin\", \"password\": \"somePassword\"}", MatchType.STRICT))))
                .respond(response()
                        .withHeader("Content-Type", "application/problem+json")
                        .withStatusCode(401)
                        .withBody("{\"title\": \"Unauthorized\", \"status\": 401, \"detail\": \"Bad credentials\"}"));

        // Запрос корневой страницы с заголовком "Authorization".
        mockServer.when(request()
                .withMethod("GET")
                .withHeader("Accept", "application/json, application/problem+json")
                .withPath("/"))
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/rootForAuthorizedUserResponse.json", Map.of("serverPort", mockServer.getLocalPort()))));
    }

    @BeforeEach
    void initializeClient() {
        client = Clients.builder()
                .setBaseUrl(String.format("http://127.0.0.1:%d/", mockServer.getLocalPort()))
                .setLogin("SomeLogin")
                .setPassword("somePassword")
                .build();
    }

}
