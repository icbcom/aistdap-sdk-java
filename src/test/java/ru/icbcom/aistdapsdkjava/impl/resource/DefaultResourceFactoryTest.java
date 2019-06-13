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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.icbcom.aistdapsdkjava.api.datasource.DataSource;
import ru.icbcom.aistdapsdkjava.api.exception.UnknownClassException;
import ru.icbcom.aistdapsdkjava.api.objecttype.Attribute;
import ru.icbcom.aistdapsdkjava.api.objecttype.EnumSetValue;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectType;
import ru.icbcom.aistdapsdkjava.api.objecttype.Section;
import ru.icbcom.aistdapsdkjava.api.resource.Resource;
import ru.icbcom.aistdapsdkjava.api.resource.VoidResource;
import ru.icbcom.aistdapsdkjava.impl.datasource.DefaultDataSource;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.datastore.DummyDataStore;
import ru.icbcom.aistdapsdkjava.impl.objectType.DefaultAttribute;
import ru.icbcom.aistdapsdkjava.impl.objectType.DefaultEnumSetValue;
import ru.icbcom.aistdapsdkjava.impl.objectType.DefaultObjectType;
import ru.icbcom.aistdapsdkjava.impl.objectType.DefaultSection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.jupiter.api.Assertions.*;

class DefaultResourceFactoryTest {

    private ResourceFactory resourceFactory;
    private DataStore dataStore;

    @BeforeEach
    void setup() {
        dataStore = new DummyDataStore();
        resourceFactory = new DefaultResourceFactory(dataStore);
    }

    @Test
    void enumSetValueInstantiationShouldWorkProperly() {
        EnumSetValue object = resourceFactory.instantiate(EnumSetValue.class);
        assertEquals(DefaultEnumSetValue.class, object.getClass());
        assertThat(object, hasProperty("dataStore", sameInstance(dataStore)));
    }

    @Test
    void sectionInstantiationShouldWorkProperly() {
        Section object = resourceFactory.instantiate(Section.class);
        assertEquals(DefaultSection.class, object.getClass());
        assertThat(object, hasProperty("dataStore", sameInstance(dataStore)));
    }

    @Test
    void objectTypeInstantiationShouldWorkProperly() {
        ObjectType object = resourceFactory.instantiate(ObjectType.class);
        assertEquals(DefaultObjectType.class, object.getClass());
        assertThat(object, hasProperty("dataStore", sameInstance(dataStore)));
    }

    @Test
    void voidResourceInstantiationShouldWorkProperly() {
        VoidResource object = resourceFactory.instantiate(VoidResource.class);
        assertEquals(DefaultVoidResource.class, object.getClass());
        assertThat(object, hasProperty("dataStore", sameInstance(dataStore)));
    }

    @Test
    void attributeInstantiationShouldWorkProperly() {
        Attribute object = resourceFactory.instantiate(Attribute.class);
        assertEquals(DefaultAttribute.class, object.getClass());
        assertThat(object, hasProperty("dataStore", sameInstance(dataStore)));
    }

    @Test
    void instantiationShouldFailIfClassIsUnknown() {
        UnknownClassException exception = assertThrows(UnknownClassException.class, () -> resourceFactory.instantiate(UnknownResource.class));
        assertEquals("Implementation class not found for: " + UnknownResource.class.getName(), exception.getMessage());
    }

    @Test
    void instantiationShouldWorkWhenConcreteClassPassedAsArgument() {
        assertEquals(DefaultObjectType.class, resourceFactory.instantiate(DefaultObjectType.class).getClass());
    }

    @Test
    void dataSourceInstantiationShouldWorkProperly() {
        DataSource object = resourceFactory.instantiate(DataSource.class);
        assertEquals(DefaultDataSource.class, object.getClass());
        assertThat(object, hasProperty("dataStore", sameInstance(dataStore)));
    }

    private interface UnknownResource extends Resource {
    }

}