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

package ru.icbcom.aistdapsdkjava.api.exception;

import org.springframework.util.Assert;
import ru.icbcom.aistdapsdkjava.api.error.Error;

import java.util.Map;
import java.util.Optional;

/**
 * Исключение, используемое при получении каких-либо ошибок полученных от сервера платформы AistDap.
 */
public class AistDapBackendException extends AistDapSdkException implements Error {

    private final Error error;

    private static String buildExceptionMessage(Error error) {
        Assert.notNull(error, "Error argument cannot be null");
        StringBuilder sb = new StringBuilder();
        sb.append("HTTP: ").append(error.getStatus())
                .append(", Title: '").append(error.getTitle()).append("'")
                .append(", Detail: '").append(error.getDetail()).append("'")
                .append(", More info: '").append(error.getMoreInfo()).append("'");
        return sb.toString();
    }

    public AistDapBackendException(Error error) {
        super(buildExceptionMessage(error));
        this.error = error;
    }

    @Override
    public String getTitle() {
        return error.getTitle();
    }

    @Override
    public int getStatus() {
        return error.getStatus();
    }

    @Override
    public String getDetail() {
        return error.getDetail();
    }

    @Override
    public Map<String, Object> getMoreInfo() {
        return error.getMoreInfo();
    }

    @Override
    public <T> Optional<T> getMoreInfoValue(String key, Class<T> clazz) {
        return error.getMoreInfoValue(key, clazz);
    }

}
