package ru.icbcom.aistdapsdkjava.api.query;

/**
 * @param <T>
 */
public interface Criteria<T extends Criteria<T>> {

    /**
     *
     */
    T ascending();

    /**
     *
     */
    T descending();

    /**
     *
     */
    T pageSize(int pageSize);

    /**
     *
     */
    T pageNumber(int pageNumber);

    /**
     * Возвращает {@code true} если данный объект не содержит никаких условий или выражений orderBy, {@code false}
     * в противном случае.
     */
    boolean isEmpty();

}
