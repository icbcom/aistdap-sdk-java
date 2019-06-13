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

package ru.icbcom.aistdapsdkjava.impl.datastore.linkexpander;

import org.junit.jupiter.api.Test;
import org.springframework.hateoas.Link;
import ru.icbcom.aistdapsdkjava.api.query.Criteria;
import ru.icbcom.aistdapsdkjava.impl.query.DefaultCriteria;

import static org.junit.jupiter.api.Assertions.assertEquals;


class DefaultCriteriaLinkExpanderTest {

    @Test
    void expandShouldWorkProperly() {
        DefaultCriteriaLinkExpander defaultCriteriaLinkExpander = new DefaultCriteriaLinkExpander();

        TestCriteria testCriteria = new DefaultTestCriteria();
        testCriteria.pageNumber(2).pageSize(100).orderBySomeField().ascending();

        Link link = new Link("http://127.0.0.1/resource{?page,size,sort}", "someRel");

        String expandedHref = defaultCriteriaLinkExpander.expand(link, (DefaultCriteria) testCriteria);
        assertEquals("http://127.0.0.1/resource?page=2&size=100&sort=someField,asc", expandedHref);
    }

    @Test
    void nonTemplatedLinkShouldNotBeChangeAnyhowWhileExpanding() {
        DefaultCriteriaLinkExpander defaultCriteriaLinkExpander = new DefaultCriteriaLinkExpander();

        TestCriteria testCriteria = new DefaultTestCriteria();
        testCriteria.pageNumber(2).pageSize(100).orderBySomeField().ascending();

        Link link = new Link("http://127.0.0.1/resource", "someRel");

        String expandedHref = defaultCriteriaLinkExpander.expand(link, (DefaultCriteria) testCriteria);
        assertEquals("http://127.0.0.1/resource", expandedHref);
    }

    @Test
    void descOrderDirectionShouldWorkProperly() {
        DefaultCriteriaLinkExpander defaultCriteriaLinkExpander = new DefaultCriteriaLinkExpander();

        TestCriteria testCriteria = new DefaultTestCriteria();
        testCriteria.pageNumber(2).pageSize(100).orderBySomeField().descending();

        Link link = new Link("http://127.0.0.1/resource{?page,size,sort}", "someRel");

        String expandedHref = defaultCriteriaLinkExpander.expand(link, (DefaultCriteria) testCriteria);
        assertEquals("http://127.0.0.1/resource?page=2&size=100&sort=someField,desc", expandedHref);
    }

    private interface TestCriteria extends Criteria<TestCriteria> {
        TestCriteria orderBySomeField();
    }

    private static class DefaultTestCriteria extends DefaultCriteria<TestCriteria> implements TestCriteria {
        @Override
        public TestCriteria orderBySomeField() {
            return orderBy("someField");
        }
    }
}