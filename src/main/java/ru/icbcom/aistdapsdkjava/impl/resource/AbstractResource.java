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

package ru.icbcom.aistdapsdkjava.impl.resource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.util.Assert;
import ru.icbcom.aistdapsdkjava.api.resource.Resource;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;

import java.util.List;
import java.util.Optional;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

public abstract class AbstractResource implements Resource {

    @JsonIgnore
    private final DataStore dataStore;

    @JsonUnwrapped
    @JsonProperty(access = WRITE_ONLY)
    private ResourceSupport resourceSupport;

    protected AbstractResource(DataStore dataStore) {
        this.dataStore = dataStore;
        this.resourceSupport = new ResourceSupport();
    }

    @Override
    @JsonIgnore
    public List<Link> getLinks() {
        return resourceSupport.getLinks();
    }

    @Override
    public boolean hasLinks() {
        return !resourceSupport.getLinks().isEmpty();
    }

    @Override
    public boolean hasLink(String rel) {
        Assert.notNull(rel, "rel must not be null");
        return getLink(rel).isPresent();
    }

    @Override
    public Optional<Link> getLink(String rel) {
        Assert.notNull(rel, "rel must not be null");
        return Optional.ofNullable(resourceSupport.getLink(rel));
    }

    @Override
    public List<Link> getLinks(String rel) {
        Assert.notNull(rel, "rel must not be null");
        return resourceSupport.getLinks();
    }

    @Override
    public void add(Link link) {
        Assert.notNull(link, "Link must not be null");
        resourceSupport.add(link);
    }

    @Override
    public void add(Iterable<Link> links) {
        Assert.notNull(links, "Given links must not be null");
        resourceSupport.add(links);
    }

    @Override
    public void add(Link... links) {
        Assert.notNull(links, "Given links must not be null");
        resourceSupport.add(links);
    }

    public DataStore getDataStore() {
        return dataStore;
    }

}
