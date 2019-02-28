package ru.icbcom.aistdapsdkjava.api.objecttype;

import ru.icbcom.aistdapsdkjava.api.resource.Resource;

/**
 * Элемент перечисления/множества.
 */
public interface EnumSetValue extends Resource {

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
