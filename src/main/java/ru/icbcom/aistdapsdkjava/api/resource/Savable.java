package ru.icbcom.aistdapsdkjava.api.resource;

/**
 * Интерфейс, показывающий, что соответствующий ресурс может быть обнавлен.
 */
public interface Savable {

    /**
     * Сохранение изменений данного объекта в платформе Aist Dap.
     */
    void save();

}
