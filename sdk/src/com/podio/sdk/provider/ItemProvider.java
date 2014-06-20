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
import com.podio.sdk.RestClient;
import com.podio.sdk.ResultListener;
import com.podio.sdk.SessionListener;
import com.podio.sdk.client.RestResult;
import com.podio.sdk.domain.Item;
import com.podio.sdk.domain.ItemRequest;
import com.podio.sdk.filter.ItemFilter;

/**
 * Enables access to the item API end point.
 * 
 * @author László Urszuly
 */
public class ItemProvider extends BasicPodioProvider {

    public ItemProvider(RestClient client) {
        super(client);
    }

    /**
     * Requests the API to create a new item
     * 
     * @param applicationId
     *        The id of the application to which this item is to be added.
     * @param data
     *        The data describing the new item to create.
     * @param resultListener
     *        The callback implementation called when the items are fetched.
     *        Null is valid, but doesn't make any sense.
     * @return A ticket which the caller can use to identify this request with.
     */
    public Future<RestResult<Item.PushResult>> create(long applicationId, Item.PushData data, ResultListener<? super Item.PushResult> resultListener, ErrorListener errorListener, SessionListener sessionListener) {
        ItemFilter filter = new ItemFilter().withApplicationId(applicationId);

        return post(filter, data, Item.PushResult.class, resultListener, errorListener, sessionListener);
    }

    /**
     * Fetches the single item with the given id.
     * 
     * @param itemId
     *        The id of the item to fetch.
     * @param resultListener
     *        The callback implementation called when the items are fetched.
     *        Null is valid, but doesn't make any sense.
     * @return A ticket which the caller can use to identify this request with.
     */
    public Future<RestResult<Item>> get(long itemId, ResultListener<? super Item> resultListener, ErrorListener errorListener, SessionListener sessionListener) {
        ItemFilter filter = new ItemFilter().withItemId(itemId);

        return get(filter, Item.class, resultListener, errorListener, sessionListener);
    }

    /**
     * Fetches a default set of filtered items for the application with the
     * given id.
     * 
     * @param applicationId
     *        The id of the parent application.
     * @param resultListener
     *        The callback implementation called when the items are fetched.
     *        Null is valid, but doesn't make any sense.
     * @return A ticket which the caller can use to identify this request with.
     */
    public Future<RestResult<ItemRequest.Result>> getFiltered(long applicationId, ResultListener<? super ItemRequest.Result> resultListener, ErrorListener errorListener, SessionListener sessionListener) {
        ItemFilter filter = new ItemFilter().withApplicationIdFilter(applicationId);
        ItemRequest filterRequest = new ItemRequest(null, null, null, null, null, null);

        return post(filter, filterRequest, ItemRequest.Result.class, resultListener, errorListener, sessionListener);
    }

    /**
     * Requests the API to update an item with new values.
     * 
     * @param itemId
     *        The id of the item to update.
     * @param data
     *        The changed data bundle.
     * @param resultListener
     *        The callback implementation called when the items are fetched.
     *        Null is valid, but doesn't make any sense.
     * @return A ticket which the caller can use to identify this request with.
     */
    public Future<RestResult<Item.PushResult>> update(long itemId, Item.PushData data, ResultListener<? super Item.PushResult> resultListener, ErrorListener errorListener, SessionListener sessionListener) {
        ItemFilter filter = new ItemFilter().withItemId(itemId);

        return put(filter, data, Item.PushResult.class, resultListener, errorListener, sessionListener);
    }

}
