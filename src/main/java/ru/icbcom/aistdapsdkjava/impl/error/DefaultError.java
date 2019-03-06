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
