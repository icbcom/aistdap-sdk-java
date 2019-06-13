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

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.icbcom.aistdapsdkjava.api.measureddata.MeasuredData;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.resource.AbstractInstanceResource;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@ToString
@EqualsAndHashCode(callSuper = false)
public class DefaultMeasuredData extends AbstractInstanceResource implements MeasuredData {

    private Long dataSourceId;
    private Long dapObjectId;
    private LocalDateTime dateTime;
    private Long value;
    private Long devConst;

    public DefaultMeasuredData(@JacksonInject DataStore dataStore) {
        super(dataStore);
    }

    @Override
    public Long getDataSourceId() {
        return dataSourceId;
    }

    @Override
    public MeasuredData setDataSourceId(Long dataSourceId) {
        this.dataSourceId = dataSourceId;
        return this;
    }

    @Override
    public Long getDapObjectId() {
        return dapObjectId;
    }

    @Override
    public MeasuredData setDapObjectId(Long dapObjectId) {
        this.dapObjectId = dapObjectId;
        return this;
    }

    @Override
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public MeasuredData setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    @Override
    public Long getValue() {
        return value;
    }

    @Override
    public MeasuredData setValue(Long value) {
        this.value = value;
        return this;
    }

    @Override
    public Long getDevConst() {
        return devConst;
    }

    @Override
    public MeasuredData setDevConst(Long devConst) {
        this.devConst = devConst;
        return this;
    }

    @Override
    @JsonIgnore
    public MeasuredData setBigDecimalValue(BigDecimal value) {
        int scale = value.scale();
        if (scale >= 0) {
            setValue(value.unscaledValue().longValue());
            setDevConst(BigInteger.valueOf(10).pow(value.scale()).longValue());
        } else {
            BigInteger val = value.unscaledValue().multiply(BigInteger.valueOf(10).pow(-value.scale()));
            setValue(val.longValue());
            setDevConst(1L);
        }
        return this;
    }

    @Override
    @JsonIgnore
    public MeasuredData setBigIntegerValue(BigInteger value) {
        setValue(value.longValue());
        setDevConst(1L);
        return this;
    }

    @Override
    @JsonIgnore
    public MeasuredData setDoubleValue(double value) {
        setBigDecimalValue(BigDecimal.valueOf(value));
        return this;
    }

    @Override
    @JsonIgnore
    public MeasuredData setLongValue(long value) {
        setValue(value);
        setDevConst(1L);
        return this;
    }

}
