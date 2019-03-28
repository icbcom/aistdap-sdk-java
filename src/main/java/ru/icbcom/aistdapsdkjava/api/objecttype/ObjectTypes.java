package ru.icbcom.aistdapsdkjava.api.objecttype;

import ru.icbcom.aistdapsdkjava.impl.objectType.DefaultObjectTypeCriteria;

/**
 * Статический вспомогательный класс для работы с объектами связанными с {@link ObjectType}. Например:
 * <pre>
 * <b>ObjectTypes.criteria()</b>
 *      .orderByName().ascending()
 *      .build();
 * </pre>
 */
public final class ObjectTypes {

    /**
     * Возвращает новый экземпляр {@link ObjectTypeCriteria} используемый для настройки параметров запроса коллекции объектов {@link ObjectType}.
     *
     * @return новый экземпляр {@link ObjectTypeCriteria}
     */
    public static ObjectTypeCriteria criteria() {
        return new DefaultObjectTypeCriteria();
    }

}
