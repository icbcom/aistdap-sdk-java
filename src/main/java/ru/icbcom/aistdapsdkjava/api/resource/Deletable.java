package ru.icbcom.aistdapsdkjava.api.resource;

/**
 * Интерфейс, показывающий, что соответствующий ресурс может быть удален.
 */
public interface Deletable {

    /**
     * Удаление данного ресурса из платформы Aist Dap.
     */
    void delete();

}
