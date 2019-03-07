package ru.icbcom.aistdapsdkjava.impl.datastore.resttemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class RestTemplateFactory {

    public static RestTemplate create(ObjectMapper objectMapper) {
        RestTemplate restTemplate = new RestTemplate(createHttpRequestFactory());

        MappingJackson2HttpMessageConverter messageConverter = createObjectMapperMessageConverter(objectMapper);
        replaceMessageConverter(restTemplate, messageConverter);

        RestTemplateResponseErrorHandler errorHandler = createResponseErrorHandler(objectMapper);
        restTemplate.setErrorHandler(errorHandler);

        return restTemplate;
    }

    private static MappingJackson2HttpMessageConverter createObjectMapperMessageConverter(ObjectMapper objectMapper) {
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.setObjectMapper(objectMapper);
        return messageConverter;
    }

    private static RestTemplateResponseErrorHandler createResponseErrorHandler(ObjectMapper objectMapper) {
        return new RestTemplateResponseErrorHandler(objectMapper);
    }

    private static SimpleClientHttpRequestFactory createHttpRequestFactory() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setOutputStreaming(false);
        return requestFactory;
    }

    private static void replaceMessageConverter(RestTemplate restTemplate, HttpMessageConverter messageConverter) {
        String messageConverterClassName = messageConverter.getClass().getName();
        restTemplate.getMessageConverters().removeIf(m -> m.getClass().getName().equals(messageConverterClassName));
        restTemplate.getMessageConverters().add(messageConverter);
    }

}
