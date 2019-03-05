package ru.icbcom.aistdapsdkjava.impl.exception;

import lombok.Getter;

@Getter
public class LinkNotFoundException extends RuntimeException {

    private final String resourceHref;
    private final String rel;

    public LinkNotFoundException(String rel) {
        super(String.format("Link with relation '%s' was not found", rel));
        this.resourceHref = null;
        this.rel = rel;
    }

    public LinkNotFoundException(String resourceHref, String rel) {
        super(String.format("Link with relation '%s' was not found in resource with href '%s'", rel, resourceHref));
        this.resourceHref = resourceHref;
        this.rel = rel;
    }

}
