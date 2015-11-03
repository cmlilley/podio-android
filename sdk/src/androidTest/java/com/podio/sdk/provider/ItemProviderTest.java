/*
 *  Copyright (C) 2014 Copyright Citrix Systems, Inc.
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of 
 *  this software and associated documentation files (the "Software"), to deal in 
 *  the Software without restriction, including without limitation the rights to 
 *  use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies 
 *  of the Software, and to permit persons to whom the Software is furnished to 
 *  do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all 
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
 *  SOFTWARE.
 */

package com.podio.sdk.provider;

import java.util.concurrent.ExecutionException;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import android.net.Uri;
import android.test.AndroidTestCase;

import com.podio.sdk.ResultListener;
import com.podio.sdk.domain.Item;
import com.podio.sdk.mock.MockRestClient;
import com.podio.sdk.provider.ItemProvider.ItemFilterProvider;

public class ItemProviderTest extends AndroidTestCase {

    @Mock
    ResultListener<Object> resultListener;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Verifies that the {@link ItemProvider} calls through to the (mock) rest
     * client with expected uri parameters when trying to create a new item.
     * 
     * <pre>
     * 
     * 1. Create a new ItemProvider.
     * 
     * 2. Try to create a new (mock) item.
     * 
     * 3. Verify that the designated rest client is called with a uri that
     *      contains the expected parameters.
     * 
     * </pre>
     */
    public void testCreateItem() {
        MockRestClient mockClient = new MockRestClient();
        ItemProvider provider = new ItemProvider();
        provider.setRestClient(mockClient);

        provider
                .create(2, new Item())
                .withResultListener(resultListener);

        Mockito.verify(resultListener, Mockito.timeout(100)).onRequestPerformed(null);
        Mockito.verifyNoMoreInteractions(resultListener);

        assertEquals(Uri.parse("test://podio.test/item/app/2"), mockClient.uri);
    }

    /**
     * Verifies that the {@link ItemProvider} throws a NullPointerException when
     * trying to create a new item with a null pointer.
     * 
     * <pre>
     * 
     * 1. Create a new ItemProvider.
     * 
     * 2. Try to create a new item with a null pointer data description.
     * 
     * 3. Verify that a NullPointerException is thrown.
     * 
     * </pre>
     */
    public void testCreateItemWithNullPointer() {
        MockRestClient mockClient = new MockRestClient();
        ItemProvider provider = new ItemProvider();
        provider.setRestClient(mockClient);

        try {
            provider.create(7, null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Verifies that the {@link ItemProvider} calls through to the (mock) rest
     * client with expected uri parameters when trying to get a filtered set of
     * items.
     * 
     * <pre>
     * 
     * 1. Create a new ItemProvider.
     * 
     * 2. Try to get a filtered set of items.
     * 
     * 3. Verify that the designated rest client is called with a uri that
     *      contains the expected parameters.
     * 
     * </pre>
     * 
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void testFilterItems() throws InterruptedException, ExecutionException {
        MockRestClient mockClient = new MockRestClient();
        ItemProvider provider = new ItemProvider();
        provider.setRestClient(mockClient);

        provider.filter()
                .onConstraint("test-key", "test-value")
                .onDoRemember(true)
                .onSortOrder("test-column", true)
                .onSpan(100, 1000)
                .get(4)
                .withResultListener(resultListener)
                .get();

        Item.FilterData f = (Item.FilterData) mockClient.data;
        assertTrue(f.hasConstraint("test-key"));
        assertEquals("test-value", f.getConstraint("test-key").toString());
        assertEquals(true, f.getDoRemember());
        assertEquals("test-column", f.getSortKey());
        assertEquals(true, f.getDoSortDescending());
        assertEquals(100, f.getLimit());
        assertEquals(1000, f.getOffset());

        assertEquals(Uri.parse("test://podio.test/item/app/4/filter"), mockClient.uri);

        Mockito.verify(resultListener, Mockito.timeout(100)).onRequestPerformed(null);
        Mockito.verifyNoMoreInteractions(resultListener);
    }

    /**
     * Verifies that the {@link ItemProvider} calls through to the (mock) rest
     * client with expected uri parameters when trying to get an existing item.
     * 
     * <pre>
     * 
     * 1. Create a new ItemProvider.
     * 
     * 2. Try to get an item.
     * 
     * 3. Verify that the designated rest client is called with a uri that
     *      contains the expected parameters.
     * 
     * </pre>
     */
    public void testGetItem() {
        MockRestClient mockClient = new MockRestClient();
        ItemProvider provider = new ItemProvider();
        provider.setRestClient(mockClient);

        provider.get(3).withResultListener(resultListener);

        Mockito.verify(resultListener, Mockito.timeout(100)).onRequestPerformed(null);
        Mockito.verifyNoMoreInteractions(resultListener);

        assertEquals(Uri.parse("test://podio.test/item/3"), mockClient.uri);
    }

    /**
     * Verifies that the {@link ItemProvider} will return a non-null-pointer
     * {@link ItemFilterProvider} when initiating a filtering of items.
     * 
     * <pre>
     * 
     * 1. Create a new ItemProvider.
     * 
     * 2. Try to start filtering items.
     * 
     * 3. Verify that a {@link ItemFilterProvider} is returned.
     * 
     * </pre>
     */
    public void testItemFilterProvider() {
        ItemProvider provider = new ItemProvider();
        provider.setRestClient(null);
        ItemFilterProvider p = provider.filter();

        assertTrue(p instanceof ItemFilterProvider);
    }

    /**
     * Verifies that the {@link ItemProvider} calls through to the (mock) rest
     * client with expected uri parameters when trying to update an existing
     * item.
     * 
     * <pre>
     * 
     * 1. Create a new ItemProvider.
     * 
     * 2. Try to update an item.
     * 
     * 3. Verify that the designated rest client is called with a uri that
     *      contains the expected parameters.
     * 
     * </pre>
     */
    public void testUpdateItem() {
        MockRestClient mockClient = new MockRestClient();
        ItemProvider provider = new ItemProvider();
        provider.setRestClient(mockClient);

        provider
                .update(5, new Item())
                .withResultListener(resultListener);

        Mockito.verify(resultListener, Mockito.timeout(100)).onRequestPerformed(null);
        Mockito.verifyNoMoreInteractions(resultListener);

        assertEquals(Uri.parse("test://podio.test/item/5"), mockClient.uri);
    }

    /**
     * Verifies that the {@link ItemProvider} throws a NullPointerException when
     * trying to update an item with a null pointer.
     * 
     * <pre>
     * 
     * 1. Create a new ItemProvider.
     * 
     * 2. Try to update an item with a null pointer data description.
     * 
     * 3. Verify that a NullPointerException is thrown.
     * 
     * </pre>
     */
    public void testUpdateItemWithNullPointer() {
        MockRestClient mockClient = new MockRestClient();
        ItemProvider provider = new ItemProvider();
        provider.setRestClient(mockClient);

        try {
            provider.update(7, null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException e) {
        }
    }
}
