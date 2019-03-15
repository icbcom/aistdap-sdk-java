package ru.icbcom.aistdapsdkjava.impl.datasourcegroup;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.icbcom.aistdapsdkjava.api.datasourcegroup.DataSourceGroup;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(MockitoExtension.class)
class DefaultDataSourceGroupTest {

    @Mock
    DataStore dataStore;

    @Test
    void fieldsInitializationShouldWorkProperly() {
        DataSourceGroup dataSourceGroup = new DefaultDataSourceGroup(dataStore)
                .setDataSourceGroupId(1000L)
                .setObjectTypeId(100L)
                .setCaption("Название группы источников данных");

        assertThat(dataSourceGroup, allOf(
                hasProperty("dataSourceGroupId", is(1000L)),
                hasProperty("objectTypeId", is(100L)),
                hasProperty("caption", is("Название группы источников данных")),
                hasProperty("dataStore", is(sameInstance(dataStore)))
        ));
    }

}