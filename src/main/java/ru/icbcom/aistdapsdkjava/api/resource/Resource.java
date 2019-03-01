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
     */
    Optional<Link> getLink(String rel);

    /**
     * Возвращает список ссылок с заданным именем отношения.
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
