package ru.icbcom.aistdapsdkjava.impl.query;

import org.springframework.util.Assert;

public class Order {

    private final String propertyName;
    private final boolean ascending;

    public Order(String propertyName, boolean ascending) {
        Assert.hasText(propertyName, "propertyName cannot be null or empty.");
        this.propertyName = propertyName;
        this.ascending = ascending;
    }

    public static Order asc(String propertyName) {
        return new Order(propertyName, true);
    }

    public static Order desc(String propertyName) {
        return new Order(propertyName, false);
    }

    public String getPropertyName() {
        return propertyName;
    }

    public boolean isAscending() {
        return ascending;
    }

    @Override
    public String toString() {
        return this.propertyName + ' ' + (this.isAscending() ? "asc" : "desc");
    }
}