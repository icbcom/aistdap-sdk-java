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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.icbcom.aistdapsdkjava.api.measureddata.MeasuredData;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.Month;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(MockitoExtension.class)
class DefaultMeasuredDataTest {
    @Mock
    DataStore dataStore;

    @Test
    void fieldsInitializationShouldWorkProperly() {
        MeasuredData measuredData = new DefaultMeasuredData(dataStore)
                .setDataSourceId(1000L)
                .setDapObjectId(123L)
                .setDateTime(LocalDateTime.of(2019, Month.JUNE, 17, 15, 23, 55))
                .setValue(1234L)
                .setDevConst(100L);

        assertThat(measuredData, allOf(
                hasProperty("dataSourceId", is(1000L)),
                hasProperty("dapObjectId", is(123L)),
                hasProperty("dateTime", is(LocalDateTime.of(2019, Month.JUNE, 17, 15, 23, 55))),
                hasProperty("value", is(1234L)),
                hasProperty("devConst", is(100L))
        ));
    }

    @Test
    void setBigDecimalValueShouldWorkProperly() {
        MeasuredData measuredData = new DefaultMeasuredData(dataStore).setBigDecimalValue(new BigDecimal("123.631"));
        assertThat(measuredData, allOf(
                hasProperty("value", is(123631L)),
                hasProperty("devConst", is(1000L))
        ));

        MeasuredData measuredData1 = new DefaultMeasuredData(dataStore).setBigDecimalValue(new BigDecimal("-123.631"));
        assertThat(measuredData1, allOf(
                hasProperty("value", is(-123631L)),
                hasProperty("devConst", is(1000L))
        ));
        MeasuredData measuredData2 = new DefaultMeasuredData(dataStore).setBigDecimalValue(new BigDecimal("0"));
        assertThat(measuredData2, allOf(
                hasProperty("value", is(0L)),
                hasProperty("devConst", anything())
        ));
    }

    @Test
    void setBigDecimalValueForSmallNumbersShouldWorkProperly() {
        MeasuredData measuredData = new DefaultMeasuredData(dataStore).setBigDecimalValue(new BigDecimal("0.0000123"));
        assertThat(measuredData, allOf(
                hasProperty("value", is(123L)),
                hasProperty("devConst", is(10000000L))
        ));
    }

    @Test
    void setBigDecimalValueWithNegativeScaleShouldWorkProperly() {
        MeasuredData measuredData = new DefaultMeasuredData(dataStore).setBigDecimalValue(new BigDecimal(BigInteger.valueOf(6), -2));
        assertThat(measuredData, allOf(
                hasProperty("value", is(600L)),
                hasProperty("devConst", is(1L))
        ));
    }

    @Test
    void setBigIntegerValueShouldWorkProperly() {
        MeasuredData measuredData = new DefaultMeasuredData(dataStore).setBigIntegerValue(new BigInteger("12345"));
        assertThat(measuredData, allOf(
                hasProperty("value", is(12345L)),
                hasProperty("devConst", is(1L))
        ));

        MeasuredData measuredData1 = new DefaultMeasuredData(dataStore).setBigIntegerValue(new BigInteger("0"));
        assertThat(measuredData1, allOf(
                hasProperty("value", is(0L)),
                hasProperty("devConst", anything())
        ));

        MeasuredData measuredData2 = new DefaultMeasuredData(dataStore).setBigIntegerValue(new BigInteger("-1678345"));
        assertThat(measuredData2, allOf(
                hasProperty("value", is(-1678345L)),
                hasProperty("devConst", is(1L))
        ));
    }

    @Test
    void setDoubleValueShouldWorkProperly() {
        MeasuredData measuredData = new DefaultMeasuredData(dataStore).setDoubleValue(123.631);
        assertThat(measuredData, allOf(
                hasProperty("value", is(123631L)),
                hasProperty("devConst", is(1000L))
        ));

        MeasuredData measuredData1 = new DefaultMeasuredData(dataStore).setDoubleValue(-123.631);
        assertThat(measuredData1, allOf(
                hasProperty("value", is(-123631L)),
                hasProperty("devConst", is(1000L))
        ));
        MeasuredData measuredData2 = new DefaultMeasuredData(dataStore).setDoubleValue(0);
        assertThat(measuredData2, allOf(
                hasProperty("value", is(0L)),
                hasProperty("devConst", anything())
        ));
    }

    @Test
    void setLongValueShouldWorkProperly() {
        MeasuredData measuredData = new DefaultMeasuredData(dataStore).setLongValue(12345);
        assertThat(measuredData, allOf(
                hasProperty("value", is(12345L)),
                hasProperty("devConst", is(1L))
        ));

        MeasuredData measuredData1 = new DefaultMeasuredData(dataStore).setLongValue(0);
        assertThat(measuredData1, allOf(
                hasProperty("value", is(0L)),
                hasProperty("devConst", anything())
        ));

        MeasuredData measuredData2 = new DefaultMeasuredData(dataStore).setLongValue(-1678345);
        assertThat(measuredData2, allOf(
                hasProperty("value", is(-1678345L)),
                hasProperty("devConst", is(1L))
        ));
    }

}
