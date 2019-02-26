package ru.icbcom.aistdapsdkjava.api.query;

/**
 *
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

}
