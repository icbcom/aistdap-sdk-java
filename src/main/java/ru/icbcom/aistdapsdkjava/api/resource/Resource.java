package ru.icbcom.aistdapsdkjava.api.resource;

import org.springframework.hateoas.Link;

import java.util.List;

/**
 * Ресурс.
 */
public interface Resource {

    /**
     * Вовзвращает список ссылок данного ресурса.
     */
    List<Link> getLinks();

    /**
     * Проверяет, есть ли какие-либо ссылки у данного ресурса.
     */
    boolean hasLinks();

    /**
     * Проверяет, есть ли ссылка с заданным именем отношения.
     */
    boolean hasLink(String rel);

    /**
     * Возвращает ссылку с заданным именем отношения.
     * </p>
     * Если такой ссылки нет, то будет возвращен null.
     */
    Link getLink(String rel);

    /**
     * Возвращает список ссылок с заданным именем отношения.
     */
    List<Link> getLinks(String rel);

}
