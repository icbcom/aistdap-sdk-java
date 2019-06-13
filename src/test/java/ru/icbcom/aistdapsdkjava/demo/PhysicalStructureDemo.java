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
import ru.icbcom.aistdapsdkjava.api.device.DeviceList;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectType;
import ru.icbcom.aistdapsdkjava.api.physicalstructure.PhysicalStructureObject;
import ru.icbcom.aistdapsdkjava.api.physicalstructure.PhysicalStructureObjectList;
import ru.icbcom.aistdapsdkjava.api.physicalstructure.PhysicalStructureObjects;

import java.util.Optional;

@Slf4j
@Disabled
public class PhysicalStructureDemo {

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
    void getAllInRoot() {
        PhysicalStructureObjectList allInRoot = client.physicalStructure().getAllInRoot();
        for (PhysicalStructureObject physicalStructureObject : allInRoot) {
            log.info(physicalStructureObject.toString());
        }
    }

    @Test
    void getAllInRootWithCriteria() {
        PhysicalStructureObjectList allInRoot = client.physicalStructure()
                .getAllInRoot(PhysicalStructureObjects.criteria()
                        .orderByName().ascending()
                        .pageSize(100));
        for (PhysicalStructureObject physicalStructureObject : allInRoot) {
            log.info(physicalStructureObject.toString());
        }
    }

    @Test
    void getById() {
        PhysicalStructureObject physicalStructureObject = client.physicalStructure().getById(10007L).orElseThrow();
        log.info(physicalStructureObject.toString());
    }

    @Test
    void createInRoot() {
        PhysicalStructureObject physicalStructureObject = client.instantiate(PhysicalStructureObject.class)
                .setObjectTypeId(34L)
                .setName("Новой объект физической структуры")
                .setAttributeValue("Period", "1000")
                .setAttributeValue("DefaultReadTimeout", "1000")
                .setAttributeValue("RepeatEnabled", "false")
                .setAttributeValue("DelayBetweenPolls", "1000")
                .setAttributeValue("IpAddress", "1000")
                .setAttributeValue("Port", "1000");
        physicalStructureObject = client.physicalStructure().createInRoot(physicalStructureObject);
        log.info(physicalStructureObject.toString());
    }

    @Test
    void save() {
        PhysicalStructureObject physicalStructureObject = client.physicalStructure().getById(10032L).orElseThrow();
        physicalStructureObject.setName("Новое название");
        physicalStructureObject.setAttributeValue("Port", "31337");
        physicalStructureObject.save();
    }

    @Test
    void delete() {
        PhysicalStructureObject physicalStructureObject = client.physicalStructure().getById(10033L).orElseThrow();
        physicalStructureObject.delete();
    }

    @Test
    void getObjectType() {
        PhysicalStructureObject physicalStructureObject = client.physicalStructure().getById(10007L).orElseThrow();
        log.info(physicalStructureObject.toString());
        ObjectType objectType = physicalStructureObject.getObjectType();
        log.info(objectType.toString());
    }

    @Test
    void getDescendants() {
        PhysicalStructureObject physicalStructureObject = client.physicalStructure().getById(10007L).orElseThrow();
        PhysicalStructureObjectList descendants = physicalStructureObject.getDescendants();
        for (PhysicalStructureObject currentPhysicalStructureObject : descendants) {
            log.info(currentPhysicalStructureObject.toString());
        }
    }

    @Test
    void getAttachedDevices() {
        PhysicalStructureObject physicalStructureObject = client.physicalStructure().getById(10008L).orElseThrow();
        DeviceList attachedDevices = physicalStructureObject.getAttachedDevices();
        for (Device attachedDevice : attachedDevices) {
            log.info(attachedDevice.toString());
        }
    }

    @Test
    void createDescendant() {
        PhysicalStructureObject physicalStructureObject = client.physicalStructure().getById(10007L).orElseThrow();

        PhysicalStructureObject newPhysicalStructureObject = client.instantiate(PhysicalStructureObject.class)
                .setObjectTypeId(42L)
                .setName("Новой объект физической структуры")
                .setAttributeValue("Bsid", "1")
                .setAttributeValue("DeviceNumber", "1")
                .setAttributeValue("ProfSerNum", "123456789");
        newPhysicalStructureObject = physicalStructureObject.createDescendant(newPhysicalStructureObject);
        log.info(newPhysicalStructureObject.toString());
    }

    @Test
    void getParent() {
        PhysicalStructureObject physicalStructureObject = client.physicalStructure().getById(10035L).orElseThrow();
        Optional<PhysicalStructureObject> parentOptional = physicalStructureObject.getParent();
        log.info(parentOptional.toString());
    }

}
