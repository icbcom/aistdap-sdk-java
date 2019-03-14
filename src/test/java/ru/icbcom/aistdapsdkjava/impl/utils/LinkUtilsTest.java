package ru.icbcom.aistdapsdkjava.impl.utils;

import org.junit.jupiter.api.Test;
import org.springframework.hateoas.Link;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class LinkUtilsTest {

    @Test
    void appendLongIdToLinkWhenEndsWithSlashShouldWorkProperly() {
        Link link = LinkUtils.appendLongIdToLink(new Link("http://api.icbcom.ru/resource/", "someResource"), 123L);
        assertThat(link, allOf(
                hasProperty("href", is("http://api.icbcom.ru/resource/123")),
                hasProperty("rel", is("someResource"))
        ));
    }

    @Test
    void appendLongIdToLinkShouldWorkProperly() {
        Link link = LinkUtils.appendLongIdToLink(new Link("http://api.icbcom.ru/resource", "someResource"), 123L);
        assertThat(link, allOf(
                hasProperty("href", is("http://api.icbcom.ru/resource/123")),
                hasProperty("rel", is("someResource"))
        ));
    }

    @Test
    void appendLongIdToLinkWhenOriginalLinkContainsTemplateVariablesShouldWorkProperly() {
        Link link = LinkUtils.appendLongIdToLink(new Link("http://api.icbcom.ru/resource{?page,size,sort}", "someResource"), 123L);
        assertThat(link, allOf(
                hasProperty("href", is("http://api.icbcom.ru/resource/123")),
                hasProperty("rel", is("someResource"))
        ));
    }

}
