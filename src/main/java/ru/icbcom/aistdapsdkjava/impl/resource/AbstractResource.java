package ru.icbcom.aistdapsdkjava.impl.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.hal.Jackson2HalModule;
import ru.icbcom.aistdapsdkjava.api.resource.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

public abstract class AbstractResource implements Resource {

    @JsonProperty(value = "_links", access = WRITE_ONLY)
    @JsonDeserialize(using = Jackson2HalModule.HalLinkListDeserializer.class)
    private final List<Link> links;

    protected AbstractResource() {
        this.links = new ArrayList<>();
    }

    @Override
    public List<Link> getLinks() {
        return links;
    }

    @Override
    public boolean hasLinks() {
        return !this.links.isEmpty();
    }

    @Override
    public boolean hasLink(String rel) {
        return getLink(rel).isPresent();
    }

    @Override
    public Optional<Link> getLink(String rel) {
        for (Link link : links) {
            if (link.getRel().equals(rel)) {
                return Optional.of(link);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Link> getLinks(String rel) {
        List<Link> relatedLinks = new ArrayList<>();
        for (Link link : links) {
            if (link.getRel().equals(rel)) {
                relatedLinks.add(link);
            }
        }
        return relatedLinks;
    }

}
