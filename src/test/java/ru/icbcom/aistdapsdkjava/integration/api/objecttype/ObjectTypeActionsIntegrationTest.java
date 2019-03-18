package ru.icbcom.aistdapsdkjava.integration.api.objecttype;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.*;
import org.mockserver.integration.ClientAndServer;
import ru.icbcom.aistdapsdkjava.api.client.Client;
import ru.icbcom.aistdapsdkjava.api.client.Clients;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static ru.icbcom.aistdapsdkjava.integration.api.objecttype.IntegrationTestConstants.MOCK_SERVER_PORT;

class ObjectTypeActionsIntegrationTest {

    private ClientAndServer mockServer;

    @BeforeEach
    public void startServer() {
        mockServer = startClientAndServer(MOCK_SERVER_PORT);
    }

    @BeforeEach
    public void stopServer() {
        mockServer.stop();
    }

    @Test
    void test() {
        Client client = Clients.builder()
                .setBaseUrl(String.format("http://127.0.0.1:%d/", mockServer.getLocalPort()))
                .setLogin("SomeLogin")
                .setPassword("somePassword")
                .build();


//        client.objectTypes().getAll();

    }

}