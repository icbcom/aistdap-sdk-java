package ru.icbcom.aistdapsdkjava.integration.device;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.verify.VerificationTimes;
import ru.icbcom.aistdapsdkjava.api.device.Device;
import ru.icbcom.aistdapsdkjava.integration.AbstractIntegrationTest;

import java.util.Map;

import static org.mockserver.matchers.MatchType.STRICT;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.JsonBody.json;
import static ru.icbcom.aistdapsdkjava.helper.ResourceHelper.loadTemplatedResource;

class DeviceIntegrationTest extends AbstractIntegrationTest {

    @BeforeEach
    void setup() {
        mockServer.when(request()
                .withMethod("GET")
                .withHeader("Authorization", "Bearer some-access-token")
                .withHeader("Accept", "application/json, application/problem+json")
                .withPath("/devices/1"))
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/device/deviceWithIdEqualOneResponse.json", Map.of("serverPort", mockServer.getLocalPort()))));
    }

    @Test
    void saveShouldWorkProperly() {
        mockServer.when(request()
                .withMethod("PUT")
                .withHeader("Authorization", "Bearer some-access-token")
                .withHeader("Accept", "application/json, application/problem+json")
                .withPath("/devices/1")
                .withBody(json(loadTemplatedResource("integration/device/deviceWithIdEqualOneUpdateRequest.json", Map.of("serverPort", mockServer.getLocalPort())), STRICT)))
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/device/deviceWithIdEqualOneUpdateResponse.json", Map.of("serverPort", mockServer.getLocalPort()))));

        Device device = client.devices().getById(1L).orElseThrow();
        device.setName("Измененное название устройства")
                .setAttributeValue("Serial", "123456789012");
        device.save();

        mockServer.verify(request()
                        .withMethod("PUT")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/devices/1")
                        .withBody(json(loadTemplatedResource("integration/device/deviceWithIdEqualOneUpdateRequest.json", Map.of("serverPort", mockServer.getLocalPort())), STRICT)),
                VerificationTimes.exactly(1));
    }

    @Test
    void deleteShouldWorkProperly() {
        mockServer.when(request()
                .withMethod("DELETE")
                .withHeader("Authorization", "Bearer some-access-token")
                .withHeader("Accept", "application/json, application/problem+json")
                .withPath("/devices/1"))
                .respond(response().withStatusCode(204));

        Device device = client.devices().getById(1L).orElseThrow();
        device.delete();

        mockServer.verify(request()
                        .withMethod("DELETE")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/devices/1"),
                VerificationTimes.exactly(1));
    }

}