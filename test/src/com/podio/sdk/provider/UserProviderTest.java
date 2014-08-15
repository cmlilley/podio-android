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

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import android.net.Uri;
import android.test.AndroidTestCase;

import com.podio.sdk.ResultListener;
import com.podio.sdk.mock.MockRestClient;

public class UserProviderTest extends AndroidTestCase {

    @Mock
    ResultListener<Object> resultListener;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Verifies that the {@link UserProvider} calls through to the (mock) rest
     * client with expected uri parameters when trying to get user data.
     * 
     * <pre>
     * 
     * 1. Create a new {@link UserProvider}.
     * 
     * 2. Make a request for the user data.
     * 
     * 3. Verify that the designated rest client is called with a uri that
     *      contains the expected parameters.
     * 
     * </pre>
     */
    public void testGetUserData() {
        MockRestClient mockClient = new MockRestClient();
        UserProvider provider = new UserProvider();
        provider.setRestClient(mockClient);

        provider
                .getData()
                .withResultListener(resultListener);

        Mockito.verify(resultListener, Mockito.timeout(100)).onRequestPerformed(null);
        Mockito.verifyNoMoreInteractions(resultListener);

        assertEquals(Uri.parse("test://podio.test/user"), mockClient.uri);
    }

    /**
     * Verifies that the {@link UserProvider} calls through to the (mock) rest
     * client with expected uri parameters when trying to get user profile data.
     * 
     * <pre>
     * 
     * 1. Create a new {@link UserProvider}.
     * 
     * 2. Make a request for the user profile data.
     * 
     * 3. Verify that the designated rest client is called with a uri that
     *      contains the expected parameters.
     * 
     * </pre>
     */
    public void testGetUserProfile() {
        MockRestClient mockClient = new MockRestClient();
        UserProvider provider = new UserProvider();
        provider.setRestClient(mockClient);

        provider
                .getProfile()
                .withResultListener(resultListener);

        Mockito.verify(resultListener, Mockito.timeout(100)).onRequestPerformed(null);
        Mockito.verifyNoMoreInteractions(resultListener);

        assertEquals(Uri.parse("test://podio.test/user/profile"), mockClient.uri);
    }
}
