package ru.icbcom.aistdapsdkjava.api.exception;

import org.springframework.util.Assert;
import ru.icbcom.aistdapsdkjava.api.error.Error;

import java.util.Map;
import java.util.Optional;

/**
 * Исключение, используемое при получении каких-либо ошибок полученных от сервера платформы AistDap.
 */
public class BackendException extends AistDapSdkException implements Error {

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

    public BackendException(Error error) {
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
