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

import java.util.concurrent.Future;

import com.podio.sdk.ErrorListener;
import com.podio.sdk.ResultListener;
import com.podio.sdk.SessionListener;
import com.podio.sdk.client.RestResult;
import com.podio.sdk.domain.User;
import com.podio.sdk.filter.UserFilter;

/**
 * Enables access to the User API end point.
 * 
 * @author László Urszuly
 */
public class UserProvider extends BasicPodioProvider {

    /**
     * Fetches the currently logged in user.
     * 
     * @return
     */
    public Future<RestResult<User>> get(ResultListener<? super User> resultListener, ErrorListener errorListener, SessionListener sessionListener) {
        UserFilter filter = new UserFilter();
        return get(filter, User.class, resultListener, errorListener, sessionListener);
    }

    /**
     * Fetches the currently logged in user profile.
     * 
     * @return
     */
    public Future<RestResult<User.Profile>> getProfile(ResultListener<? super User.Profile> resultListener, ErrorListener errorListener, SessionListener sessionListener) {
        UserFilter filter = new UserFilter().withProfile();
        return get(filter, User.Profile.class, resultListener, errorListener, sessionListener);
    }
}
