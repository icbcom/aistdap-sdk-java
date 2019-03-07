package ru.icbcom.aistdapsdkjava.impl.datastore.resttemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import ru.icbcom.aistdapsdkjava.api.exception.AistDapSdkException;
import ru.icbcom.aistdapsdkjava.api.exception.BackendException;
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
        throw new BackendException(error);
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
