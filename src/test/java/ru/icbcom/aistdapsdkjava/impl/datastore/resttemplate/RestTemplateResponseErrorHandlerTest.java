package ru.icbcom.aistdapsdkjava.impl.datastore.resttemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.client.ClientHttpResponse;
import ru.icbcom.aistdapsdkjava.api.exception.AistDapBackendException;
import ru.icbcom.aistdapsdkjava.impl.error.DefaultError;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static java.util.Collections.emptyMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestTemplateResponseErrorHandlerTest {

    @Mock
    ObjectMapper objectMapper;

    @Mock
    ClientHttpResponse response;

    private RestTemplateResponseErrorHandler restTemplateResponseErrorHandler;

    @BeforeEach
    void setup() {
        restTemplateResponseErrorHandler = new RestTemplateResponseErrorHandler(objectMapper);
    }

    @Test
    void handleErrorShouldWorkProperly() throws IOException {
        String errorBody =
                "{\n" +
                        "    \"title\": \"Unauthorized\",\n" +
                        "    \"status\": 401,\n" +
                        "    \"detail\": \"Refresh token expired\"\n" +
                        "}";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(errorBody.getBytes(StandardCharsets.UTF_8));
        DefaultError defaultError = new DefaultError();
        defaultError.setTitle("Unauthorized");
        defaultError.setStatus(401);
        defaultError.setDetail("Refresh token expired");

        when(response.getBody()).thenReturn(inputStream);
        when(objectMapper.readValue(any(String.class), eq(DefaultError.class))).thenReturn(defaultError);

        AistDapBackendException exception = assertThrows(AistDapBackendException.class, () -> restTemplateResponseErrorHandler.handleError(response));
        assertThat(exception, allOf(
            hasProperty("title", is("Unauthorized")),
            hasProperty("status", is(401)),
            hasProperty("detail", is("Refresh token expired")),
            hasProperty("moreInfo", is(emptyMap()))
        ));

        InOrder inOrder = inOrder(objectMapper, response);
        inOrder.verify(response).getBody();

        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
        inOrder.verify(objectMapper).readValue(stringArgumentCaptor.capture(), eq(DefaultError.class));
        assertEquals(errorBody, stringArgumentCaptor.getValue());

        inOrder.verifyNoMoreInteractions();
    }

}