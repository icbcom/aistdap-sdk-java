package ru.icbcom.aistdapsdkjava.impl.datastore.linkexpander;

import org.springframework.hateoas.Link;
import ru.icbcom.aistdapsdkjava.impl.query.DefaultCriteria;

import java.util.HashMap;
import java.util.Map;

// TODO: Протестировать данный класс.

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
