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

package ru.icbcom.aistdapsdkjava.impl.measureddata;

import org.springframework.hateoas.Link;
import ru.icbcom.aistdapsdkjava.api.measureddata.MeasuredData;
import ru.icbcom.aistdapsdkjava.api.measureddata.MeasuredDataActions;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.resource.AbstractResourceActions;

public class DefaultMeasuredDataActions extends AbstractResourceActions implements MeasuredDataActions {
    private Link cachedMeasuredDataLink;

    public DefaultMeasuredDataActions(Link baseLink, DataStore dataStore) {
        super(baseLink, dataStore);
    }

    @Override
    public void insert(MeasuredData measuredData) {
        if (cachedMeasuredDataLink == null) {
            cachedMeasuredDataLink = getRootResourceLink("dap:measuredData");
        }
        dataStore.callMethod(cachedMeasuredDataLink, measuredData);
    }
}
