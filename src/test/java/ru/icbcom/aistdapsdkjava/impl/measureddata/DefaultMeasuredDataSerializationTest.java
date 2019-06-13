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

package ru.icbcom.aistdapsdkjava.impl.measureddata;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import ru.icbcom.aistdapsdkjava.api.measureddata.MeasuredData;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.datastore.DummyDataStore;
import ru.icbcom.aistdapsdkjava.impl.datastore.objectmapper.DefaultObjectMapperFactory;

import java.time.LocalDateTime;
import java.time.Month;

class DefaultMeasuredDataSerializationTest {

    private DataStore dataStore;
    private ObjectMapper objectMapper;

    public DefaultMeasuredDataSerializationTest() {
        this.dataStore = new DummyDataStore();
        this.objectMapper = new DefaultObjectMapperFactory().create(dataStore);
    }

    @Test
    void serializationShouldWorkProperly() throws JsonProcessingException, JSONException {
        MeasuredData measuredData = new DefaultMeasuredData(dataStore)
                .setDataSourceId(1000L)
                .setDapObjectId(123L)
                .setDateTime(LocalDateTime.of(2019, Month.JUNE, 17, 15, 23, 55))
                .setValue(1234L)
                .setDevConst(100L);

        String expected = "{\n" +
                "\t\"dataSourceId\": 1000,\n" +
                "\t\"dapObjectId\": 123,\n" +
                "\t\"dateTime\": \"2019-06-17T15:23:55\",\n" +
                "\t\"value\": 1234,\n" +
                "\t\"devConst\": 100\n" +
                "}";
        JSONAssert.assertEquals(expected, objectMapper.writeValueAsString(measuredData), true);
    }

    @Test
    void serializationWithDateTimeWithoutSecsAndMinsShouldWorkProperly() throws JsonProcessingException, JSONException {
        MeasuredData measuredData = new DefaultMeasuredData(dataStore)
                .setDataSourceId(1000L)
                .setDapObjectId(123L)
                .setDateTime(LocalDateTime.of(2019, Month.JUNE, 17, 15, 00, 00))
                .setValue(1234L)
                .setDevConst(100L);

        String expected = "{\n" +
                "\t\"dataSourceId\": 1000,\n" +
                "\t\"dapObjectId\": 123,\n" +
                "\t\"dateTime\": \"2019-06-17T15:00:00\",\n" +
                "\t\"value\": 1234,\n" +
                "\t\"devConst\": 100\n" +
                "}";
        JSONAssert.assertEquals(expected, objectMapper.writeValueAsString(measuredData), true);
    }

}