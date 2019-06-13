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

package ru.icbcom.aistdapsdkjava.impl.datastore.linkexpander;

import org.springframework.hateoas.Link;
import ru.icbcom.aistdapsdkjava.impl.query.DefaultCriteria;

import java.util.HashMap;
import java.util.Map;

public class DefaultCriteriaLinkExpander {
    public String expand(Link link, DefaultCriteria criteria) {
        Map<String, String> arguments = prepareCriteriaArguments(criteria);
        return link.expand(arguments).getHref();
    }

    private Map<String, String> prepareCriteriaArguments(DefaultCriteria defaultCriteria) {
        Map<String, String> arguments = new HashMap<>();
        if (defaultCriteria.hasPageNumber()) {
            arguments.put("page", defaultCriteria.getPageNumber().toString());
        }
        if (defaultCriteria.hasPageSize()) {
            arguments.put("size", defaultCriteria.getPageSize().toString());
        }
        if (defaultCriteria.hasOrder()) {
            String propertyName = defaultCriteria.getOrder().getPropertyName();
            String order = defaultCriteria.getOrder().isAscending() ? "asc" : "desc";
            arguments.put("sort", propertyName + "," + order);
        }
        return arguments;
    }
}
