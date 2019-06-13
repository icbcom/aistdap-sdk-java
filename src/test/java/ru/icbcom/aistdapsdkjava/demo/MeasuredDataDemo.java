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
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ru.icbcom.aistdapsdkjava.api.client.Client;
import ru.icbcom.aistdapsdkjava.api.client.Clients;
import ru.icbcom.aistdapsdkjava.api.measureddata.MeasuredData;
import ru.icbcom.aistdapsdkjava.impl.measureddata.DefaultMeasuredData;

import java.time.LocalDateTime;
import java.time.Month;

@Slf4j
@Disabled
public class MeasuredDataDemo {

    private Client client;

    @BeforeEach
    void setup() {
        client = Clients.builder()
                .setBaseUrl("http://127.0.0.1:8080/")
                .setLogin("Admin")
                .setPassword("newPassword")
                .build();
    }

    @Test
    void insert() {
        for (int i = 0; i < 5000; i++) {
            double nextDouble = RandomUtils.nextDouble(0.0, 10000.0);
            MeasuredData measuredData = client.instantiate(MeasuredData.class)
                    .setDataSourceId(1L)
                    .setDapObjectId(10033L)
                    .setDateTime(LocalDateTime.now())
                    .setDoubleValue(nextDouble);
            client.measuredData().insert(measuredData);
            log.info("nextDouble: " + nextDouble);
        }
    }

}
