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

package ru.icbcom.aistdapsdkjava.impl.datastore.resttemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import ru.icbcom.aistdapsdkjava.api.exception.AistDapSdkException;
import ru.icbcom.aistdapsdkjava.api.exception.AistDapBackendException;
import ru.icbcom.aistdapsdkjava.impl.error.DefaultError;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

// TODO: Протестировать данный класс.

/**
 * Обработчик ошибок полученных от платформы AistDap.
 */
@RequiredArgsConstructor
public class RestTemplateResponseErrorHandler extends DefaultResponseErrorHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handleError(ClientHttpResponse response) {
        String errorResponseBody = readResponseBody(response);
        DefaultError error = deserializeError(errorResponseBody);
        throw new AistDapBackendException(error);
    }

    private DefaultError deserializeError(String errorResponseBody) {
        if (errorResponseBody == null || errorResponseBody.isBlank()) {
            throw new AistDapSdkException("Error response body received from backend is blank");
        }

        DefaultError defaultError;
        try {
            defaultError = objectMapper.readValue(errorResponseBody, DefaultError.class);
        } catch (IOException e) {
            throw new AistDapSdkException("Failed to deserialize DefaultError object: " + e.getMessage(), e);
        }
        return defaultError;
    }

    private String readResponseBody(ClientHttpResponse response) {
        try {
            return IOUtils.toString(response.getBody(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new AistDapSdkException("Failed to read error response body: " + e.getMessage(), e);
        }
    }

}
