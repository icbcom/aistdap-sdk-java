/*
 * Copyright © 2018-2019 Icbcom
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

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
