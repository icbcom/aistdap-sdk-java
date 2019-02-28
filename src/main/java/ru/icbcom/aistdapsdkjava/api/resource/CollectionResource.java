package ru.icbcom.aistdapsdkjava.api.resource;

/**
 * Коллекция ресурсов.
 */
public interface CollectionResource<T extends Resource> extends Resource, Iterable<T> {

    /**
     * Возвращает количество элементов в текущей странцице.
     */
    int getSize();

    /**
     * Возвращает общее количество доступных элементов во всей коллекции.
     */
    long getTotalElements();

    /**
     * Возвращает общее количество доступных страниц для все коллекции.
     */
    long getTotalPages();

    /**
     * Возвращает номер текущей страницы.
     */
    int getNumber();

}
