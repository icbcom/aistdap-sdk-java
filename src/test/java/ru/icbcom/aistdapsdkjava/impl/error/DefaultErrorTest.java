package ru.icbcom.aistdapsdkjava.impl.error;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DefaultErrorTest {

    @Test
    void getMoreInfoValueMethodShouldWorkProperly() {
        DefaultError defaultError = new DefaultError();
        defaultError.setStatus(404);
        defaultError.setTitle("Object type not found");
        defaultError.setDetail("Could not find object type with objectTypeId '123123'");
        defaultError.setMoreInfo(
                Map.of("objectTypeId", 123,
                        "violations", List.of("violation1", "violation2", "violation3"))
        );

        Optional<Integer> objectTypeIdOptional = defaultError.getMoreInfoValue("objectTypeId", Integer.class);
        assertTrue(objectTypeIdOptional.isPresent());
        assertEquals(123, objectTypeIdOptional.get());

        Optional<List> violationsOptional = defaultError.getMoreInfoValue("violations", List.class);
        assertTrue(violationsOptional.isPresent());
        List<String> list = violationsOptional.get();
        assertThat(list, contains("violation1", "violation2", "violation3"));
    }

}