package ru.icbcom.aistdapsdkjava.api.device;

import ru.icbcom.aistdapsdkjava.impl.device.DefaultDeviceCriteria;

/**
 * Статический вспомогательный класс для работы с объектами связанными с {@link Device}. Например:
 * <pre>
 * <b>Devices.criteria()</b>
 *      .orderByName().ascending()
 *      .build();
 * </pre>
 */
public final class Devices {

    /**
     * Возвращает новый экземпляр {@link DeviceCriteria} используемый для настройки параметров запроса коллекции объектов {@link Device}.
     *
     * @return новый экземпляр {@link DeviceCriteria}
     */
    public static DeviceCriteria criteria() {
        return new DefaultDeviceCriteria();
    }

}
