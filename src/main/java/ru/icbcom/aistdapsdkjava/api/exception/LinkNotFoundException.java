package ru.icbcom.aistdapsdkjava.api.exception;

import lombok.Getter;
import org.springframework.hateoas.Link;
import ru.icbcom.aistdapsdkjava.api.exception.AistDapSdkException;

@Getter
public class LinkNotFoundException extends AistDapSdkException {

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

    public LinkNotFoundException(Link link, String rel) {
        this(link.getHref(), rel);
    }

}
