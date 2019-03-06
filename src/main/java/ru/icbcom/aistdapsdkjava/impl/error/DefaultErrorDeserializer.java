package ru.icbcom.aistdapsdkjava.impl.error;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class DefaultErrorDeserializer extends StdDeserializer<DefaultError> {

    public DefaultErrorDeserializer() {
        this(null);
    }

    public DefaultErrorDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public DefaultError deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        Map<String, Object> document = p.getCodec().readValue(p, new TypeReference<HashMap<String, Object>>() {
        });

        DefaultError defaultError = new DefaultError();

        obtainRootLevelValue(document, "title", String.class).ifPresent(defaultError::setTitle);
        obtainRootLevelValue(document, "status", Integer.class).ifPresent(defaultError::setStatus);
        obtainRootLevelValue(document, "detail", String.class).ifPresent(defaultError::setDetail);

        defaultError.setMoreInfo(obtainMoreInfoMap(document));

        return defaultError;
    }

    private Map<String, Object> obtainMoreInfoMap(Map<String, Object> document) {
        return document.entrySet().stream()
                .filter(entry -> !entry.getKey().equals("title"))
                .filter(entry -> !entry.getKey().equals("status"))
                .filter(entry -> !entry.getKey().equals("detail"))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private <T> Optional<T> obtainRootLevelValue(Map<String, Object> document, String key, Class<T> clazz) {
        if (!document.containsKey(key)) {
            return Optional.empty();
        }
        Assert.isInstanceOf(clazz, document.get(key));
        return Optional.of(clazz.cast(document.get(key)));
    }

}
