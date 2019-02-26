package ru.icbcom.aistdapsdkjava.api.objecttype;

/**
 * Элемент перечисления/множества.
 */
public interface EnumSetValue {

    /**
     * Возвращает номер данного перечисления/множества.
     */
    int getNumber();

    /**
     * Возвращает заголовок данного элемента перечисления/множества.
     */
    String getCaption();

    /**
     * Установка номера данного перечисления/множества.
     */
    EnumSetValue setNumber(int number);

    /**
     * Установка заголовка данного элемента перечисления/множества.
     */
    EnumSetValue setCaption(String caption);

}
