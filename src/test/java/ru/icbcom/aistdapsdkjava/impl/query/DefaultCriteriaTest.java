package ru.icbcom.aistdapsdkjava.impl.query;

import org.junit.jupiter.api.Test;
import ru.icbcom.aistdapsdkjava.api.query.Criteria;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class DefaultCriteriaTest {

    @Test
    void fieldsInitializationShouldWorkProperly() {
        TestCriteria criteria = new DefaultTestCriteria()
                .orderBy("someProperty").descending().pageSize(10).pageNumber(2);

        assertThat(criteria, allOf(
                hasProperty("order", allOf(
                        hasProperty("propertyName", is("someProperty")),
                        hasProperty("ascending", is(false))
                )),
                hasProperty("pageSize", is(10)),
                hasProperty("pageNumber", is(2)),
                hasProperty("empty", is(false))
        ));
    }

    @Test
    void ifOrderByWasNotCalledExceptionShouldBeThrown() {
        TestCriteria criteria = new DefaultTestCriteria();

        IllegalStateException illegalStateException = assertThrows(IllegalStateException.class, () -> criteria.ascending());
        assertEquals("There is no current orderBy clause to declare as ascending or descending!", illegalStateException.getMessage());
    }

    @Test
    void emptyCriteriaMethodsShouldWorkProperly() {
        TestCriteria criteria = new DefaultTestCriteria();
        assertTrue(criteria.isEmpty());
    }

    @Test
    void hasMethodsShouldWorkProperly() {
        TestCriteria criteria = new DefaultTestCriteria();

        assertFalse(((DefaultTestCriteria) criteria).hasOrder());
        assertFalse(((DefaultTestCriteria) criteria).hasPageSize());
        assertFalse(((DefaultTestCriteria) criteria).hasPageNumber());

        ((DefaultTestCriteria) criteria).orderBy("someProperty");
        assertTrue(((DefaultTestCriteria) criteria).hasOrder());
        assertFalse(((DefaultTestCriteria) criteria).hasPageSize());
        assertFalse(((DefaultTestCriteria) criteria).hasPageNumber());

        criteria.pageSize(123);
        assertTrue(((DefaultTestCriteria) criteria).hasOrder());
        assertTrue(((DefaultTestCriteria) criteria).hasPageSize());
        assertFalse(((DefaultTestCriteria) criteria).hasPageNumber());

        criteria.pageNumber(500);
        assertTrue(((DefaultTestCriteria) criteria).hasOrder());
        assertTrue(((DefaultTestCriteria) criteria).hasPageSize());
        assertTrue(((DefaultTestCriteria) criteria).hasPageNumber());
    }

    private interface TestCriteria extends Criteria<TestCriteria> {
    }

    private static class DefaultTestCriteria extends DefaultCriteria<TestCriteria> implements TestCriteria {
    }
}