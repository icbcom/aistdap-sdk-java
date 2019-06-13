/*
 * Copyright © 2018-2019 Icbcom
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

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
     * Установка номера данного перечисления/множества.
     */
    EnumSetValue setNumber(int number);

    /**
     * Возвращает заголовок данного элемента перечисления/множества.
     */
    String getCaption();

    /**
     * Установка заголовка данного элемента перечисления/множества.
     */
    EnumSetValue setCaption(String caption);

}
