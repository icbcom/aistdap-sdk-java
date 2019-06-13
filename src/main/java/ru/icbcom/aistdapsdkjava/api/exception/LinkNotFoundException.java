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

package ru.icbcom.aistdapsdkjava.api.exception;

import lombok.Getter;
import org.springframework.hateoas.Link;
import ru.icbcom.aistdapsdkjava.api.exception.AistDapSdkException;
import ru.icbcom.aistdapsdkjava.api.resource.Resource;

/**
 * Исключение, используемое при ошибках отсутствия необходимых ссылок {@link Link} внутри ресурсов {@link Resource}.
 */
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
