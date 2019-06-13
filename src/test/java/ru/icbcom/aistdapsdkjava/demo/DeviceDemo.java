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

package ru.icbcom.aistdapsdkjava.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ru.icbcom.aistdapsdkjava.api.client.Client;
import ru.icbcom.aistdapsdkjava.api.client.Clients;
import ru.icbcom.aistdapsdkjava.api.device.Device;
import ru.icbcom.aistdapsdkjava.api.device.DeviceCriteria;
import ru.icbcom.aistdapsdkjava.api.device.DeviceList;
import ru.icbcom.aistdapsdkjava.api.device.Devices;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectType;
import ru.icbcom.aistdapsdkjava.api.physicalstructure.PhysicalStructureObject;

import java.util.Optional;

@Slf4j
@Disabled
public class DeviceDemo {

    private Client client;

    @BeforeEach
    void setup() {
        client = Clients.builder()
                .setBaseUrl("http://127.0.0.1:8080/")
                .setLogin("Admin")
                .setPassword("Admin")
                .build();
    }

    @Test
    void listDeviceDemo() {
        DeviceCriteria criteria = Devices.criteria()
                .orderByName().descending()
                .pageSize(100);
        DeviceList deviceList = client.devices().getAll(criteria);
        for (Device device : deviceList) {
            log.info(device.toString());
        }
    }

    @Test
    void getById() {
        Device device = client.devices().getById(5L).orElseThrow();
        log.info(device.toString());
    }

    @Test
    void createDevice() {
        Device device = client.instantiate(Device.class)
                .setObjectTypeId(32L)
                .setName("Теплосчетчик")
                .setAttributeValue("Serial", "123");
        device = client.devices().create(device);
        log.info(device.toString());
    }

    @Test
    void save() {
        Device device = client.devices().getById(10019L).orElseThrow();
        device.setAttributeValue("Address", "100");
        device.save();
    }

    @Test
    void delete() {
        Device device = client.devices().getById(10026L).orElseThrow();
        device.delete();
    }

    @Test
    void deviceGetObjectType() {
        Device device = client.devices().getById(10019L).orElseThrow();
        log.info(device.toString());
        ObjectType objectType = device.getObjectType();
        log.info(objectType.toString());
    }

    @Test
    void getPhysicalStructureObjectDeviceAttachedTo() {
        Device device = client.devices().getById(10033L).orElseThrow();
        log.info(device.toString());
        Optional<PhysicalStructureObject> physicalStructureObjectDeviceAttachedTo = device.getPhysicalStructureObjectDeviceAttachedTo();
        log.info(physicalStructureObjectDeviceAttachedTo.toString());
    }

    @Test
    void detach() {
        Device device = client.devices().getById(10033L).orElseThrow();
        log.info(device.toString());
        device.detach();
    }

    @Test
    void attach() {
        Device device = client.devices().getById(10033L).orElseThrow();
        log.info(device.toString());
        device.attach(10032L);
    }

}
