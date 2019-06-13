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
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class DefaultRestTemplateFactory implements RestTemplateFactory {

    @Override
    public RestTemplate create(ObjectMapper objectMapper) {
        RestTemplate restTemplate = new RestTemplate(createHttpRequestFactory());

        MappingJackson2HttpMessageConverter messageConverter = createObjectMapperMessageConverter(objectMapper);
        replaceMessageConverter(restTemplate, messageConverter);

        RestTemplateResponseErrorHandler errorHandler = createResponseErrorHandler(objectMapper);
        restTemplate.setErrorHandler(errorHandler);

        restTemplate.getInterceptors().add(new AcceptHeaderRequestInterceptor());

        return restTemplate;
    }

    private MappingJackson2HttpMessageConverter createObjectMapperMessageConverter(ObjectMapper objectMapper) {
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.setObjectMapper(objectMapper);
        return messageConverter;
    }

    private RestTemplateResponseErrorHandler createResponseErrorHandler(ObjectMapper objectMapper) {
        return new RestTemplateResponseErrorHandler(objectMapper);
    }

    private SimpleClientHttpRequestFactory createHttpRequestFactory() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setOutputStreaming(false);
        return requestFactory;
    }

    private void replaceMessageConverter(RestTemplate restTemplate, HttpMessageConverter messageConverter) {
        String messageConverterClassName = messageConverter.getClass().getName();
        restTemplate.getMessageConverters().removeIf(m -> m.getClass().getName().equals(messageConverterClassName));
        restTemplate.getMessageConverters().add(messageConverter);
    }

}
