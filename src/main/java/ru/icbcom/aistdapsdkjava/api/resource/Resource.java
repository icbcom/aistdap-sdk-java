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

package ru.icbcom.aistdapsdkjava.api.resource;

import org.springframework.hateoas.Link;

import java.util.List;
import java.util.Optional;

/**
 * Ресурс.
 */
public interface Resource {

    /**
     * Вовзвращает список ссылок данного ресурса.
     *
     * @return список ссылок данного ресурса
     */
    List<Link> getLinks();

    /**
     * Проверяет, есть ли какие-либо ссылки у данного ресурса.
     *
     * @return true - если у данного ресурса есть какие-либо ссылки, false - если у данного ресурса нет ниикаких ссылок
     */
    boolean hasLinks();

    /**
     * Проверяет, есть ли ссылка с заданным именем отношения.
     *
     * @param rel имя отношения
     * @return true - если у данного ресурса существует ссылка с заданным именем отношеня, false - если такой ссылки нет
     */
    boolean hasLink(String rel);

    /**
     * Возвращает ссылку с заданным именем отношения.
     *
     * @param rel имя отношения
     * @return {@link Optional} содержащиий ссылку с заданным именем отношения если такая ссылка существует, либо
     * {@link Optional#empty()} если такой ссылки нет
     */
    Optional<Link> getLink(String rel);

    /**
     * Возвращает список ссылок с заданным именем отношения.
     *
     * @param rel имя отношения
     * @return список ссылок с заданным именем отношения
     */
    List<Link> getLinks(String rel);

    /**
     * Добавление ссылки {@link Link} к данному ресурсу.
     */
    void add(Link link);

    /**
     * Добавление нескольких ссылок {@link Link} к данному ресурсу.
     */
    void add(Iterable<Link> links);

    /**
     * Добавление нескольких ссылок {@link Link} к данному ресурсу.
     */
    void add(Link... links);

}
