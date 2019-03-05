package ru.icbcom.aistdapsdkjava.impl.query;

import lombok.ToString;
import org.springframework.util.Assert;
import ru.icbcom.aistdapsdkjava.api.query.Criteria;

@ToString
public class DefaultCriteria<T extends Criteria<T>> implements Criteria<T> {

    private Order order;
    private Integer pageSize;
    private Integer pageNumber;

    protected T orderBy(String propertyName) {
        return setOrder(Order.asc(propertyName));
    }

    private T setOrder(Order order) {
        Assert.notNull(order, "order cannot be null.");
        this.order = order;
        return (T) this;
    }

    private T orderDirection(boolean ascending) {
        Assert.state(order != null, "There is no current orderBy clause to declare as ascending or descending!");
        String name = order.getPropertyName();
        Order newOrder = ascending ? Order.asc(name) : Order.desc(name);
        return setOrder(newOrder);
    }

    @Override
    public T ascending() {
        return orderDirection(true);
    }

    @Override
    public T descending() {
        return orderDirection(false);
    }

    @Override
    public T pageSize(int pageSize) {
        this.pageSize = sanitizePageSize(pageSize);
        return (T) this;
    }

    private int sanitizePageSize(int pageSize) {
        return Math.max(1, pageSize);
    }

    @Override
    public T pageNumber(int pageNumber) {
        this.pageNumber = sanitizePageNumber(pageNumber);
        return (T) this;
    }

    private int sanitizePageNumber(int pageNumber) {
        return Math.max(0, pageNumber);
    }

    @Override
    public boolean isEmpty() {
        return order == null && pageSize == null && pageNumber == null;
    }

    public Order getOrder() {
        return order;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public boolean hasOrder() {
        return order != null;
    }

    public boolean hasPageSize() {
        return pageSize != null;
    }

    public boolean hasPageNumber() {
        return pageNumber != null;
    }

}
