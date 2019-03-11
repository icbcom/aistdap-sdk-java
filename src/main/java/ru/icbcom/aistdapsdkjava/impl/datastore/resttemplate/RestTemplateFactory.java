package ru.icbcom.aistdapsdkjava.impl.datastore.resttemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;

public interface RestTemplateFactory {
    RestTemplate create(ObjectMapper objectMapper);
}
