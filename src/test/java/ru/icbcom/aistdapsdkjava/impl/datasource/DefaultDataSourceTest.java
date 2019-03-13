package ru.icbcom.aistdapsdkjava.impl.datasource;

import org.junit.jupiter.api.Test;
import ru.icbcom.aistdapsdkjava.api.datasource.DataSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class DefaultDataSourceTest {

    @Test
    void fieldsInitializationShouldWorkProperly() {
        DataSource dataSource = new DefaultDataSource(null)
                .setDataSourceId(100L)
                .setObjectTypeId(1L)
                .setCaption("Название источника данных")
                .setMeasureItem("Единица измерения")
                .setDataSourceGroupId(1000L);

        assertThat(dataSource, allOf(
                hasProperty("dataSourceId", is(100L)),
                hasProperty("objectTypeId", is(1L)),
                hasProperty("caption", is("Название источника данных")),
                hasProperty("measureItem", is("Единица измерения")),
                hasProperty("dataSourceGroupId", is(1000L))
        ));
    }

}