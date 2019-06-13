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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.Link;
import ru.icbcom.aistdapsdkjava.api.device.Devices;
import ru.icbcom.aistdapsdkjava.api.measureddata.MeasuredData;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.device.DefaultDeviceList;
import ru.icbcom.aistdapsdkjava.impl.resource.DefaultVoidResource;

import java.time.LocalDateTime;
import java.time.Month;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultMeasuredDataActionsTest {

    @Mock
    DataStore dataStore;

    private Link baseLink;
    private DefaultMeasuredDataActions measuredDataActions;

    @BeforeEach
    void setup() {
        baseLink = new Link("http://127.0.0.1/");
        measuredDataActions = new DefaultMeasuredDataActions(baseLink, dataStore);
    }

    @Test
    void insertShouldWorkProperly() {
        DefaultVoidResource rootVoidResource = new DefaultVoidResource(dataStore);
        rootVoidResource.add(new Link("http://127.0.0.1:8080/measuredData", "dap:measuredData"));
        doReturn(rootVoidResource).when(dataStore).getResource(baseLink, DefaultVoidResource.class);

        MeasuredData measuredData = new DefaultMeasuredData(dataStore)
                .setDataSourceId(1000L)
                .setDapObjectId(123L)
                .setDateTime(LocalDateTime.of(2019, Month.JUNE, 17, 15, 23, 55))
                .setValue(1234L)
                .setDevConst(100L);
        measuredDataActions.insert(measuredData);

        verify(dataStore).getResource(baseLink, DefaultVoidResource.class);
        verify(dataStore).callMethod(eq(new Link("http://127.0.0.1:8080/measuredData", "dap:measuredData")), eq(measuredData));
        verifyNoMoreInteractions(dataStore);
    }

}