package ru.icbcom.aistdapsdkjava.api.physicalstructure;

import ru.icbcom.aistdapsdkjava.impl.physicalstructure.DefaulPhysicalStructureObjectCriteria;

/**
 * Статический вспомогательный класс для работы с объектами связанными с {@link PhysicalStructureObject}. Например:
 * <pre>
 * <b>PhysicalStructureObjects.criteria()</b>
 *      .orderByName().ascending()
 *      .build();
 * </pre>
 */
public final class PhysicalStructureObjects {

    /**
     * Возвращает новый экземпляр {@link PhysicalStructureObjectCriteria} используемый для настройки параметров запроса коллекции объектов {@link PhysicalStructureObject}.
     *
     * @return новый экземпляр {@link PhysicalStructureObjectCriteria}
     */
    public static PhysicalStructureObjectCriteria criteria() {
        return new DefaulPhysicalStructureObjectCriteria();
    }

}
