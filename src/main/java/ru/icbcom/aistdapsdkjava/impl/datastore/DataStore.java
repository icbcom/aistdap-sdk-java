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

package ru.icbcom.aistdapsdkjava.impl.datastore;

import org.springframework.hateoas.Link;
import ru.icbcom.aistdapsdkjava.api.query.Criteria;
import ru.icbcom.aistdapsdkjava.api.resource.Deletable;
import ru.icbcom.aistdapsdkjava.api.resource.Resource;
import ru.icbcom.aistdapsdkjava.api.resource.Savable;

public interface DataStore {

    <T extends Resource> T getResource(Link link, Class<T> clazz);

    <T extends Resource, C extends Criteria> T getResource(Link link, Class<T> clazz, C criteria);

    <T extends Resource> T create(Link parentLink, T resource);

    <T extends Resource & Savable> T save(T resource);

    <T extends Resource & Deletable> void delete(T resource);

    void callMethod(Link link);

    <T extends Resource> void callMethod(Link link, T methodArgumentResource);

}
