package ru.icbcom.aistdapsdkjava.api.client;

import ru.icbcom.aistdapsdkjava.api.device.Device;
import ru.icbcom.aistdapsdkjava.api.device.DeviceActions;
import ru.icbcom.aistdapsdkjava.api.measureddata.MeasuredData;
import ru.icbcom.aistdapsdkjava.api.measureddata.MeasuredDataActions;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectType;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectTypeActions;
import ru.icbcom.aistdapsdkjava.api.physicalstructure.PhysicalStructureObject;
import ru.icbcom.aistdapsdkjava.api.physicalstructure.PhysicalStructureObjectActions;
import ru.icbcom.aistdapsdkjava.api.resource.Resource;

/**
 * Интерфейс {@link Client} является начальной точкой взаимодействия с платформой Aist Dap через данный SDK.
 * JVM проект, взаимодействующий с платформой Aist Dap первым делом должен получить соответствующий экземпляр {@link Client}.
 * После получения экземпляра {@link Client}, REST API платформы Aist Dap может быть использован с помощью обычных Java
 * вызовов сделанных на объектах полученных от {@link Client}.
 * <p></p>
 * Например:
 * <pre>
 *      Client client = Clients.builder()
 *                          .setBaseUrl("http://api.aistdap.ru:8080/")
 *                          .setLogin("Admin")
 *                          .setPassword("password")
 *                          .build();
 *
 *      DeviceList deviceList = client.devices().getAll();
 *      for (Device device : deviceList) {
 *          System.out.println(device);
 *      }
 * </pre>
 */
public interface Client {

    /**
     * Создает и возвращает новый экземпляр ресурса соответствующего типа.
     *
     * @param clazz класс создаваемого ресурса
     * @param <T> подтип ресурса
     * @return вновь созданный экземпляр соответствующего ресурса
     */
    <T extends Resource> T instantiate(Class<T> clazz);

    /**
     * Возвращает объект для выполнения основных операций над типами объектов {@link ObjectType}.
     *
     * @return объект для выполнения основных операций над типами объектов
     */
    ObjectTypeActions objectTypes();

    /**
     * Возвращает объект для выполнения основных операций над устройствами {@link Device}.
     *
     * @return объект для выполнения основных операций над устройствами
     */
    DeviceActions devices();

    /**
     * Возвращает объект для выполнения основных операций над объектами физической структуры {@link PhysicalStructureObject}.
     *
     * @return объект для выполнения основных операций над объектами физической структуры
     */
    PhysicalStructureObjectActions physicalStructure();

    /**
     * Возвращает объект для выполнения основных операций с измерениями {@link MeasuredData}.
     *
     * @return объект для выполнения основных операций с измерениями
     */
    MeasuredDataActions measuredData();

}
