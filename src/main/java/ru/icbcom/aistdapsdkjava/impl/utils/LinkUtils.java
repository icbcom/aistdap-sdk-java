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

import org.springframework.hateoas.Link;

public final class LinkUtils {

    public static Link appendLongIdToLink(Link link, Long longToAppend) {
        String href = link.expand().getHref();
        return new Link(href.endsWith("/") ? href + longToAppend : href + "/" + longToAppend, link.getRel());
    }

}
