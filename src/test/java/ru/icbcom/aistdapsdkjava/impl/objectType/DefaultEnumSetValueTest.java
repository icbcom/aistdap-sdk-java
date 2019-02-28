package ru.icbcom.aistdapsdkjava.impl.objectType;

import org.junit.jupiter.api.Test;
import ru.icbcom.aistdapsdkjava.api.objecttype.EnumSetValue;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class DefaultEnumSetValueTest {

    @Test
    void fieldsInitializationShouldWorkProperly() {
        EnumSetValue enumSetValue = new DefaultEnumSetValue();
        enumSetValue
                .setNumber(10)
                .setCaption("Some Caption");

        assertThat(enumSetValue, allOf(
                hasProperty("number", is(10)),
                hasProperty("caption", is("Some Caption"))
        ));
    }

}