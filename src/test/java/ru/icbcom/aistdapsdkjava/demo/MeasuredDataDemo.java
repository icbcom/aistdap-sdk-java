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
