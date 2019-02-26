package ru.icbcom.aistdapsdkjava.impl.query;

import lombok.ToString;
import org.springframework.util.Assert;
import ru.icbcom.aistdapsdkjava.api.query.Criteria;

@ToString
public class DefaultCriteria<T extends Criteria<T>> implements Criteria<T> {

    private Order order;

    public T orderBy(String propertyName) {
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
        return null;
    }

    @Override
    public T pageNumber(int pageNumber) {
        return null;
    }
}
