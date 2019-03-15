package ru.icbcom.aistdapsdkjava.impl.datasourcegroup;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.Link;
import ru.icbcom.aistdapsdkjava.api.datasource.DataSource;
import ru.icbcom.aistdapsdkjava.api.datasourcegroup.DataSourceGroup;
import ru.icbcom.aistdapsdkjava.impl.datasource.DefaultDataSource;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

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

    @Test
    void saveShouldWorkProperly() {
        DataSourceGroup dataSourceGroup = new DefaultDataSourceGroup(dataStore)
                .setDataSourceGroupId(1000L)
                .setObjectTypeId(100L)
                .setCaption("Название группы источников данных");
        dataSourceGroup.add(new Link("http://127.0.0.1:8080/objectTypes/1/dataSourceGroups/1000", "self"));

        dataSourceGroup.save();

        verify(dataStore).save(same(dataSourceGroup));
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void deleteShouldWorkProperly() {
        DataSourceGroup dataSourceGroup = new DefaultDataSourceGroup(dataStore)
                .setDataSourceGroupId(1000L)
                .setObjectTypeId(100L)
                .setCaption("Название группы источников данных");
        dataSourceGroup.add(new Link("http://127.0.0.1:8080/objectTypes/1/dataSourceGroups/1000", "self"));

        dataSourceGroup.delete();

        verify(dataStore).delete(same(dataSourceGroup));
        verifyNoMoreInteractions(dataStore);
    }

}