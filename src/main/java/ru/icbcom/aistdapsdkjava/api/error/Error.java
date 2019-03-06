package ru.icbcom.aistdapsdkjava.api.error;

import java.util.Map;
import java.util.Optional;

/**
 * Ошибка.
 */
public interface Error {

    /**
     * Короткое, человеко-читаемое описание данной проблемы.
     */
    String getTitle();

    /**
     * Код статуса HTTP ответа полученный от сервера.
     */
    int getStatus();

    /**
     * Детальное описание возникшей проблемы.
     */
    String getDetail();

    /**
     * Дополнительная информация о проблеме в формате ключ - значение.
     */
    Map<String, Object> getMoreInfo();

    /**
     * Получение значения из таблицы дополнительной информации {@code moreInfo}, и его приведение (cast) к
     * заданному параметром {@code clazz} типу.
     */
    <T> Optional<T> getMoreInfoValue(String key, Class<T> clazz);

}
