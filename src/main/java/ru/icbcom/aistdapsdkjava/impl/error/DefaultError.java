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

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import org.springframework.util.Assert;
import ru.icbcom.aistdapsdkjava.api.error.Error;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@JsonDeserialize(using = DefaultErrorDeserializer.class)
@Data
public class DefaultError implements Error {

    private String title;
    private int status;
    private String detail;
    private Map<String, Object> moreInfo = new HashMap<>();

    @Override
    public <T> Optional<T> getMoreInfoValue(String key, Class<T> clazz) {
        if (moreInfo.containsKey(key)) {
            Assert.isInstanceOf(clazz, moreInfo.get(key));
            return Optional.of(clazz.cast(moreInfo.get(key)));
        } else {
            return Optional.empty();
        }
    }

}
