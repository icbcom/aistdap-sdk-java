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

package ru.icbcom.aistdapsdkjava.api.measureddata;

import ru.icbcom.aistdapsdkjava.api.resource.Resource;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

/**
 * Измерение.
 */
public interface MeasuredData extends Resource {

    /**
     * Возвращает идентификатор источника данных, для которого получено данное измерение.
     *
     * @return идентификатор источника данных, для которого получено данное измерение
     */
    Long getDataSourceId();

    /**
     * Устанавливает идентификатор источника данных, для которого получено данное измерение.
     *
     * @param dataSourceId идентификатор источника данных, для которого получено данное измерение
     * @return данный объект {@code this} для объединения вызовов в цепочку
     */
    MeasuredData setDataSourceId(Long dataSourceId);

    /**
     * Возвращает идентификатор объекта (устройства или объекта физической структуры) для которого получено данное измерение.
     *
     * @return идентификатор объекта для которого получено данное измерение
     */
    Long getDapObjectId();

    /**
     * Устанавливает идентификатор объекта (устройства или объекта физической структуры) для которого получено данное измерение.
     *
     * @param dapObjectId идентификатор объекта для которого получено данное измерение
     * @return данный объект {@code this} для объединения вызовов в цепочку
     */
    MeasuredData setDapObjectId(Long dapObjectId);

    /**
     * Возвращает дату и время получения данного измерения.
     *
     * @return дата и время получения данного измерения
     */
    LocalDateTime getDateTime();

    /**
     * Установка даты и времени получения данного измерения.
     *
     * @param dateTime дата и время получения данного измерения
     * @return данный объект {@code this} для объединения вызовов в цепочку
     */
    MeasuredData setDateTime(LocalDateTime dateTime);

    /**
     * Возвращает числитель значения данного измерения.
     *
     * <p>Измеренные значения в рамках класса {@link MeasuredData} представляются в виде рациональных чисел, т.е.
     * чисел вида {@code M/Q (где M и Q целые числа, Q != 0)}. Свойство {@link MeasuredData#getValue()} представляет
     * числитель измеренного значения (число M), а свойство {@link MeasuredData#getDevConst()} знаменатель (число Q).</p>
     *
     * @return числитель значения данного измерения
     */
    Long getValue();

    /**
     * Установка числителя значения данного измерения.
     *
     * @param value числитель значения данного измерения
     * @return данный объект {@code this} для объединения вызовов в цепочку
     */
    MeasuredData setValue(Long value);

    /**
     * Возвращает знаменатель значения данного измерения.
     *
     * <p>Измеренные значения в рамках класса {@link MeasuredData} представляются в виде рациональных чисел, т.е.
     * чисел вида {@code M/Q (где M и Q целые числа, Q != 0)}. Свойство {@link MeasuredData#getValue()} представляет
     * числитель измеренного значения (число M), а свойство {@link MeasuredData#getDevConst()} знаменатель (число Q).</p>
     *
     * @return знаменатель значения данного измерения
     */
    Long getDevConst();

    /**
     * Устанавливает знаменатель значения данного измерения.
     *
     * @param devConst знаменатель значения данного измерения
     * @return данный объект {@code this} для объединения вызовов в цепочку
     */
    MeasuredData setDevConst(Long devConst);

    /**
     * Установка числителя и знаменателя измерения по данным из значения {@link BigDecimal}.
     *
     * <p>Данный метод устанавливает одновременно оба свойства: {@link MeasuredData#getValue()} и {@link MeasuredData#getDevConst()}.</p>
     *
     * @param value значение измерения в виде {@link BigDecimal}
     * @return данный объект {@code this} для объединения вызовов в цепочку
     */
    MeasuredData setBigDecimalValue(BigDecimal value);

    /**
     * Установка числителя и знаменателя измерения по данным из значения {@link BigInteger}.
     *
     * <p>Данный метод устанавливает одновременно оба свойства: {@link MeasuredData#getValue()} и {@link MeasuredData#getDevConst()}.</p>
     *
     * @param value значение измерения в виде {@link BigInteger}
     * @return данный объект {@code this} для объединения вызовов в цепочку
     */
    MeasuredData setBigIntegerValue(BigInteger value);

    /**
     * Установка числителя и знаменателя измерения по данным из значения {@code double}.
     *
     * <p>Данный метод устанавливает одновременно оба свойства: {@link MeasuredData#getValue()} и {@link MeasuredData#getDevConst()}.</p>
     *
     * @param value значение измерения в виде {@code double}
     * @return данный объект {@code this} для объединения вызовов в цепочку
     */
    MeasuredData setDoubleValue(double value);

    /**
     * Установка числителя и знаменателя измерения по данным из значения {@code long}.
     *
     * <p>Данный метод устанавливает одновременно оба свойства: {@link MeasuredData#getValue()} и {@link MeasuredData#getDevConst()}.</p>
     *
     * @param value значение измерения в виде {@code long}
     * @return данный объект {@code this} для объединения вызовов в цепочку
     */
    MeasuredData setLongValue(long value);

}