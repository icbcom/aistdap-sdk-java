package ru.icbcom.aistdapsdkjava.impl.utils;

import org.springframework.hateoas.Link;

public final class LinkUtils {

    public static Link appendLongIdToLink(Link link, Long longToAppend) {
        String href = link.expand().getHref();
        return new Link(href.endsWith("/") ? href + longToAppend : href + "/" + longToAppend, link.getRel());
    }

}
