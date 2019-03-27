package ru.icbcom.aistdapsdkjava.integration.device;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.verify.VerificationTimes;
import ru.icbcom.aistdapsdkjava.api.device.Device;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectType;
import ru.icbcom.aistdapsdkjava.api.physicalstructure.PhysicalStructureObject;
import ru.icbcom.aistdapsdkjava.integration.AbstractIntegrationTest;

import java.util.Map;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
        mockServer.when(request()
                .withMethod("GET")
                .withHeader("Authorization", "Bearer some-access-token")
                .withHeader("Accept", "application/json, application/problem+json")
                .withPath("/devices/10036"))
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/device/deviceAttached.json", Map.of("serverPort", mockServer.getLocalPort()))));
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

    @Test
    void getObjectTypeShouldWorkProperly() {
        mockServer.when(request()
                .withMethod("GET")
                .withHeader("Authorization", "Bearer some-access-token")
                .withHeader("Accept", "application/json, application/problem+json")
                .withPath("/objectTypes/22"))
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/device/objectTypeWithIdEqual22.json", Map.of("serverPort", mockServer.getLocalPort()))));

        Device device = client.devices().getById(1L).orElseThrow();
        ObjectType objectType = device.getObjectType();

        assertThat(objectType, allOf(
                hasProperty("id", is(22L)),
                hasProperty("name", is("AistOnePhaseElectricalMeter")),
                hasProperty("caption", is("Счетчик э/э Аист A100")),
                hasProperty("device", is(true)),
                hasProperty("enabled", is(true))
        ));
        mockServer.verify(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/objectTypes/22"),
                VerificationTimes.exactly(1));
    }

    @Test
    void getPhysicalStructureObjectDeviceAttachedToShouldWorkProperly() {
        mockServer.when(request()
                .withMethod("GET")
                .withHeader("Authorization", "Bearer some-access-token")
                .withHeader("Accept", "application/json, application/problem+json")
                .withPath("/physicalStructure/10032"))
                .respond(response()
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(loadTemplatedResource("integration/device/physicalStructureDeviceAttachedTo.json", Map.of("serverPort", mockServer.getLocalPort()))));

        Device device = client.devices().getById(10036L).orElseThrow();
        Optional<PhysicalStructureObject> physicalStructureObjectOptional = device.getPhysicalStructureObjectDeviceAttachedTo();
        assertTrue(physicalStructureObjectOptional.isPresent());
        assertThat(physicalStructureObjectOptional.get(), allOf(
                hasProperty("id", is(10032L)),
                hasProperty("objectTypeId", is(34L)),
                hasProperty("name", is("UDP транспорт")),
                hasProperty("attributeValues", allOf(
                        hasEntry("Period", "1000"),
                        hasEntry("DefaultReadTimeout", "1000"),
                        hasEntry("RepeatEnabled", "False"),
                        hasEntry("DelayBetweenPolls", "1000"),
                        hasEntry("IpAddress", "1000"),
                        hasEntry("Port", "31337")
                )),
                hasProperty("descendantsCount", is(0L)),
                hasProperty("devicesCount", is(1L))
        ));
        mockServer.verify(request()
                        .withMethod("GET")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/physicalStructure/10032"),
                VerificationTimes.exactly(1));
    }

    @Test
    void detachShouldWorkProperly() {
        mockServer.when(request()
                .withMethod("POST")
                .withHeader("Authorization", "Bearer some-access-token")
                .withHeader("Accept", "application/json, application/problem+json")
                .withPath("/devices/10036/detach"))
                .respond(response()
                        .withStatusCode(200)
                        .withHeader("Content-Type", "application/json;charset=UTF-8"));

        Device device = client.devices().getById(10036L).orElseThrow();
        device.detach();

        mockServer.verify(request()
                        .withMethod("POST")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/devices/10036/detach"),
                VerificationTimes.exactly(1));
    }

    @Test
    void attachShouldWorkProperly() {
        mockServer.when(request()
                .withMethod("POST")
                .withHeader("Authorization", "Bearer some-access-token")
                .withHeader("Accept", "application/json, application/problem+json")
                .withPath("/devices/1/attach")
                .withBody(json("{\"physicalStructureObjectId\": 12345}", STRICT)))
                .respond(response()
                        .withStatusCode(200)
                        .withHeader("Content-Type", "application/json;charset=UTF-8"));

        Device device = client.devices().getById(1L).orElseThrow();
        device.attach(12345L);

        mockServer.verify(request()
                        .withMethod("POST")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/devices/1/attach")
                        .withBody(json("{\"physicalStructureObjectId\": 12345}", STRICT)),
                VerificationTimes.exactly(1));
    }

}