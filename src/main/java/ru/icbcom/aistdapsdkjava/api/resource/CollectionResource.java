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
     * Возвращает общее количество доступных элементов.
     */
    long getTotalElements();

    /**
     * Возвращает общее количество доступных страниц.
     */
    long getTotalPages();

    /**
     * Возвращает номер текущей страницы.
     */
    int getNumber();

    /**
     * Доступна ли ссылка на первую страницу.
     */
    boolean hasFirst();

    /**
     * Доступна ли ссылка на предыдущую страницу.
     */
    boolean hasPrevious();

    /**
     * Доступна ли ссылка на следующую страницу.
     */
    boolean hasNext();

    /**
     * Доступна ли ссылка на последнюю страницу.
     */
    boolean hasLast();

    /**
     * Получение коллекции ресурсов соответствующих первой странице.
     */
    CollectionResource<T> first();

    /**
     * Получение коллекции ресурсов соответствующих предыдущей странице.
     */
    CollectionResource<T> previous();

    /**
     * Получение коллекции ресурсов соответствующих следующей странице.
     */
    CollectionResource<T> next();

    /**
     * Получение коллекции ресурсов соответствующих последней странице.
     */
    CollectionResource<T> last();

}
