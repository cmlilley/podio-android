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

package com.podio.sdk.client;

import android.test.AndroidTestCase;

import com.podio.sdk.PodioFilter;
import com.podio.sdk.RestClient;
import com.podio.sdk.filter.BasicPodioFilter;

public class RestRequestTest extends AndroidTestCase {

    /**
     * Verifies that all setters of the {@link RestRequest} returns the
     * RestRequest itself (to enabled a chained design pattern).
     * 
     * <pre>
     * 
     * 1. Create a new RestRequest object.
     * 
     * 2. Verify that each setter returns that very same object.
     * 
     * </pre>
     */
    public void testRestRequestSettersReturnRestRequest() {
        RestRequest<Object> target = new RestRequest<Object>();

        assertEquals(target, target.setContent(null));
        assertEquals(target, target.setFilter(null));
        assertEquals(target, target.setOperation(null));
    }

    /**
     * Verifies that the assigned fields are kept intact until they are
     * requested again.
     * 
     * <pre>
     * 
     * 1. Create a new RestRequest object.
     * 
     * 2. Initialize the object with some known values.
     * 
     * 3. Call the corresponding getters and verify the results haven't
     *      changed.
     * 
     * </pre>
     */
    public void testRestRequestGettersReturnCorrectValues() {
        RestRequest<Object> target = new RestRequest<Object>();
        RestClient.Operation operation = RestClient.Operation.GET;
        Object item = new Object();

        PodioFilter filter = new BasicPodioFilter("test")
                .addQueryParameter("id", "1")
                .addQueryParameter("id", "2");

        target.setContent(item)
                .setFilter(filter)
                .setOperation(operation);

        assertEquals(item, target.getContent());
        assertEquals(filter, target.getFilter());
        assertEquals(operation, target.getOperation());
    }
}
