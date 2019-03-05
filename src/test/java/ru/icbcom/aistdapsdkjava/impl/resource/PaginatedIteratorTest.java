package ru.icbcom.aistdapsdkjava.impl.resource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaginatedIteratorTest {

    @Mock
    DataStore dataStore;

    @Test
    void iterationInsideSinglePageShouldWorkProperly() {
        TestResource testResource1 = new TestResource(dataStore);
        TestResource testResource2 = new TestResource(dataStore);
        TestResource testResource3 = new TestResource(dataStore);

        PagedResources.PageMetadata pageMetadata = new PagedResources.PageMetadata(3, 0, 3, 1);
        PagedResources<TestResource> pagedResources = new PagedResources<>(List.of(testResource1, testResource2, testResource3), pageMetadata);

        TestCollectionResource testResources = new TestCollectionResource(dataStore);
        testResources.setPagedResources(pagedResources);

        PaginatedIterator<TestResource> iterator = new PaginatedIterator<>(testResources);

        assertTrue(iterator.hasNext());
        assertSame(testResource1, iterator.next());
        assertTrue(iterator.hasNext());
        assertSame(testResource2, iterator.next());
        assertTrue(iterator.hasNext());
        assertSame(testResource3, iterator.next());
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    void iterationThroughSeveralPagesShouldWorkProperly() {
        TestResource testResource1 = new TestResource(dataStore);
        TestResource testResource2 = new TestResource(dataStore);
        TestResource testResource3 = new TestResource(dataStore);

        PagedResources.PageMetadata pageMetadata1 = new PagedResources.PageMetadata(3, 0, 6, 2);
        PagedResources<TestResource> pagedResources1 = new PagedResources<>(List.of(testResource1, testResource2, testResource3), pageMetadata1);

        TestCollectionResource testResources1 = new TestCollectionResource(dataStore);
        testResources1.setPagedResources(pagedResources1);
        testResources1.add(new Link("http://next-page-link", "next"));

        TestResource testResource4 = new TestResource(dataStore);
        TestResource testResource5 = new TestResource(dataStore);
        TestResource testResource6 = new TestResource(dataStore);

        PagedResources.PageMetadata pageMetadata2 = new PagedResources.PageMetadata(3, 1, 6, 2);
        PagedResources<TestResource> pagedResources2 = new PagedResources<>(List.of(testResource4, testResource5, testResource6), pageMetadata2);

        TestCollectionResource testResources2 = new TestCollectionResource(dataStore);
        testResources2.setPagedResources(pagedResources2);

        when(dataStore.getResource(new Link("http://next-page-link", "next"), TestCollectionResource.class)).thenReturn(testResources2);

        PaginatedIterator<TestResource> iterator = new PaginatedIterator<>(testResources1);

        assertTrue(iterator.hasNext());
        assertSame(testResource1, iterator.next());
        assertTrue(iterator.hasNext());
        assertSame(testResource2, iterator.next());
        assertTrue(iterator.hasNext());
        assertSame(testResource3, iterator.next());
        assertTrue(iterator.hasNext());
        assertSame(testResource4, iterator.next());
        assertTrue(iterator.hasNext());
        assertSame(testResource5, iterator.next());
        assertTrue(iterator.hasNext());
        assertSame(testResource6, iterator.next());
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);

        verify(dataStore).getResource(new Link("http://next-page-link", "next"), TestCollectionResource.class);
        verifyNoMoreInteractions(dataStore);
    }

    private static class TestResource extends AbstractResource {
        protected TestResource(DataStore dataStore) {
            super(dataStore);
        }
    }

    private static class TestCollectionResource extends AbstractCollectionResource<TestResource> {
        public TestCollectionResource(DataStore dataStore) {
            super(dataStore);
        }
    }

}