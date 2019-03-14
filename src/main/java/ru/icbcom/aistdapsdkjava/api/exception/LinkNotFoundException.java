package ru.icbcom.aistdapsdkjava.api.exception;

import lombok.Getter;
import org.springframework.hateoas.Link;
import ru.icbcom.aistdapsdkjava.api.exception.AistDapSdkException;

@Getter
public class LinkNotFoundException extends AistDapSdkException {

    private final String resourceHref;
    private final String rel;

    public LinkNotFoundException(String message, String resourceHref, String rel) {
        super(message);
        this.resourceHref = resourceHref;
        this.rel = rel;
    }

    public LinkNotFoundException(String rel) {
        this(String.format("Link with relation '%s' was not found", rel), null, rel);
    }

    public LinkNotFoundException(String resourceHref, String rel) {
        this(String.format("Link with relation '%s' was not found in resource with href '%s'", rel, resourceHref), resourceHref, rel);
    }

    public LinkNotFoundException(Link link, String rel) {
        this(link.getHref(), rel);
    }

}
