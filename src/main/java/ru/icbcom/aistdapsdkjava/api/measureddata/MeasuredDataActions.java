package ru.icbcom.aistdapsdkjava.api.measureddata;

/**
 * Интерфейс {@link MeasuredDataActions} содержит все основные функции для работы с измерениями.
 */
public interface MeasuredDataActions {

    /**
     * Сохранение нового измерения в платформе Aist Dap.
     *
     * @param measuredData сохраняемое измерение {@link MeasuredData}
     */
    void insert(MeasuredData measuredData);

}
