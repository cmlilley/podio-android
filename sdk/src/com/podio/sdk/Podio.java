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

package com.podio.sdk;

import javax.net.ssl.SSLSocketFactory;

import android.content.Context;

import com.podio.sdk.client.RequestFuture;
import com.podio.sdk.client.VolleyHttpClient;
import com.podio.sdk.client.VolleySessionClient;
import com.podio.sdk.domain.Session;
import com.podio.sdk.provider.ApplicationProvider;
import com.podio.sdk.provider.CalendarProvider;
import com.podio.sdk.provider.ItemProvider;
import com.podio.sdk.provider.OrganizationProvider;
import com.podio.sdk.provider.SessionProvider;
import com.podio.sdk.provider.UserProvider;
import com.podio.sdk.provider.ViewProvider;

/**
 * Enables easy access to the Podio API with a basic configuration which should
 * be suitable for most third party developers.
 * 
 * @author László Urszuly
 */
public class Podio {

    private static final String AUTHORITY = "api.podio.com";
    private static final String VERSION_NAME = "0.0.1";
    private static final int VERSION_CODE = 1;

    protected static VolleySessionClient sessionClient;
    protected static VolleyHttpClient restClient;

    /**
     * Enables means of easy operating on the {@link ApplicationProvider} API
     * end point.
     */
    public static final ApplicationProvider application = new ApplicationProvider();

    /**
     * Enables means of easy operating on the {@link CalendarProvider} API end
     * point.
     */
    public static final CalendarProvider calendar = new CalendarProvider();

    /**
     * Enables means of easy operating on the {@link ItemProvider} API end
     * point.
     */
    public static final ItemProvider item = new ItemProvider();

    /**
     * Enables means of easy operating on the {@link ViewProvider} API end
     * point.
     */
    public static final ViewProvider view = new ViewProvider();

    /**
     * Enables means of easy operating on the {@link OrganizationProvider} API
     * end point.
     */
    public static final OrganizationProvider organization = new OrganizationProvider();

    /**
     * Enables means of easy operating on the {@link SessionProvider} API end
     * point.
     */
    public static final SessionProvider client = new SessionProvider();

    /**
     * Enables means of easy operating on the {@link UserProvider} API end
     * point.
     */
    public static final UserProvider user = new UserProvider();

    /**
     * Registers a global error listener with the SDK. Any future, failing,
     * requests will try to report an error event to this listener as well. Note
     * that the error listener interface offers a mechanism to consume events,
     * which actually may prevent any events from reaching this callback.
     * 
     * @param errorListener
     *        The global error listener to register.
     */
    public static void addGlobalErrorListener(ErrorListener errorListener) {
        RequestFuture.addErrorListener(errorListener);
    }

    /**
     * Registers a global session listener with the SDK. Any future requests
     * will try to report any session changes to this listener as well. Note
     * that the session listener interface offers a mechanism to consume events,
     * which may prevent any events from reaching this callback.
     * 
     * @param sessionListener
     *        The global session listener to register.
     */
    public static void addGlobalSessionListener(SessionListener sessionListener) {
        RequestFuture.addSessionListener(sessionListener);
    }

    /**
     * Unregisters a previously registered global error listener. No error
     * events will be reported to the removed callback after this call.
     * 
     * @param errorListener
     *        The global error listener to remove.
     */
    public static void removeGlobalErrorListener(ErrorListener errorListener) {
        RequestFuture.removeErrorListener(errorListener);
    }

    /**
     * Unregisters a previously registered global session listener. No session
     * change events will be reported to the removed callback after this call.
     * 
     * @param sessionListener
     *        The global session listener to remove.
     */
    public static void removeGlobalSessionListener(SessionListener sessionListener) {
        RequestFuture.removeSessionListener(sessionListener);
    }

    /**
     * The same as {@link Podio#setup(Context, String, String, String)} with the
     * default authority.
     * 
     * @param context
     *        The context to initialize the cache database and network clients
     *        in.
     * @param clientId
     *        The pre-shared Podio client id.
     * @param clientSecret
     *        The corresponding Podio client secret.
     * @see Podio#setup(Context, String, String, String)
     */
    public static void setup(Context context, String clientId, String clientSecret) {
        setup(context, AUTHORITY, clientId, clientSecret, null);
    }

    /**
     * Initializes the Podio SDK with the given client credentials. This method
     * MUST be called before any other request is made. The caller can then
     * either choose to revoke a previously stored session (the SDK doesn't
     * store or cache the session for any long term reuse), or authenticate with
     * user or app credentials.
     * 
     * @param context
     *        The context to initialize the cache database and network clients
     *        in.
     * @param authority
     *        The host the SDK will target with its requests.
     * @param clientId
     *        The pre-shared Podio client id.
     * @param clientSecret
     *        The corresponding Podio client secret.
     * @param sslSocketFactory
     *        Optional custom SSL socket factory to use in the HTTP requests.
     */
    public static void setup(Context context, String authority, String clientId, String clientSecret, SSLSocketFactory sslSocketFactory) {
        sessionClient = new VolleySessionClient(context, authority, sslSocketFactory);
        sessionClient.setup(clientId, clientSecret);

        restClient = new VolleyHttpClient(context, authority, Integer.MAX_VALUE, sessionClient, sslSocketFactory);

        client.setRestClient(sessionClient);
        application.setRestClient(restClient);
        calendar.setRestClient(restClient);
        item.setRestClient(restClient);
        view.setRestClient(restClient);
        organization.setRestClient(restClient);
        user.setRestClient(restClient);
    }

    /**
     * Restores a previously created Podio session. Even though the access token
     * may have expired, the refresh token can be used to get a new access
     * token. The idea here is to enable the caller to persist the session and
     * avoid an unnecessary re-authentication. NOTE! The server may very well
     * invalidate both the access and refresh tokens, which would require a
     * re-authentication anyway.
     * 
     * @param session
     *        The previously stored session object.
     */
    public static void restoreSession(Session session) {
        sessionClient.setSession(session);
    }

    /**
     * Returns the version name of this Podio SDK instance.
     * 
     * @return A string representation of the Podio SDK version.
     */
    public static String sdkVersionName() {
        return VERSION_NAME;
    }

    /**
     * Returns the version code of this Podio SDK instance.
     * 
     * @return An integer representation of the Podio SDK version.
     */
    public static int sdkVersionCode() {
        return VERSION_CODE;
    }
}
