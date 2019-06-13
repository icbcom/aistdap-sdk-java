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

package ru.icbcom.aistdapsdkjava.impl.error;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DefaultErrorTest {

    @Test
    void getMoreInfoValueMethodShouldWorkProperly() {
        DefaultError defaultError = new DefaultError();
        defaultError.setStatus(404);
        defaultError.setTitle("Object type not found");
        defaultError.setDetail("Could not find object type with objectTypeId '123123'");
        defaultError.setMoreInfo(
                Map.of("objectTypeId", 123,
                        "violations", List.of("violation1", "violation2", "violation3"))
        );

        Optional<Integer> objectTypeIdOptional = defaultError.getMoreInfoValue("objectTypeId", Integer.class);
        assertTrue(objectTypeIdOptional.isPresent());
        assertEquals(123, objectTypeIdOptional.get());

        Optional<List> violationsOptional = defaultError.getMoreInfoValue("violations", List.class);
        assertTrue(violationsOptional.isPresent());
        List<String> list = violationsOptional.get();
        assertThat(list, contains("violation1", "violation2", "violation3"));
    }

}