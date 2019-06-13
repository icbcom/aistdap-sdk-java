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

package ru.icbcom.aistdapsdkjava.integration.measureddata;

import org.junit.jupiter.api.Test;
import org.mockserver.verify.VerificationTimes;
import ru.icbcom.aistdapsdkjava.api.measureddata.MeasuredData;
import ru.icbcom.aistdapsdkjava.integration.AbstractIntegrationTest;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Map;

import static org.mockserver.matchers.MatchType.STRICT;
import static org.mockserver.matchers.Times.once;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.JsonBody.json;
import static ru.icbcom.aistdapsdkjava.helper.ResourceHelper.loadResource;
import static ru.icbcom.aistdapsdkjava.helper.ResourceHelper.loadTemplatedResource;

class MeasuredDataActionsIntegrationTest extends AbstractIntegrationTest {

    @Test
    void insertShouldWorkProperly() {
        mockServer.when(request()
                        .withMethod("POST")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/measuredData")
                        .withBody(json(loadResource("integration/measuredData/measuredDataInsertRequest.json"), STRICT)),
                once())
                .respond(response()
                        .withStatusCode(200));

        MeasuredData measuredData = client.instantiate(MeasuredData.class)
                .setDataSourceId(1000L)
                .setDapObjectId(123L)
                .setDateTime(LocalDateTime.of(2019, Month.JUNE, 17, 15, 23, 55))
                .setValue(1234L)
                .setDevConst(100L);
        client.measuredData().insert(measuredData);

        mockServer.verify(request()
                        .withMethod("POST")
                        .withHeader("Authorization", "Bearer some-access-token")
                        .withHeader("Accept", "application/json, application/problem+json")
                        .withPath("/measuredData")
                        .withBody(json(loadResource("integration/measuredData/measuredDataInsertRequest.json"), STRICT)),
                VerificationTimes.exactly(1));
    }

}