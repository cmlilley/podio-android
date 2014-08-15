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

import com.podio.sdk.domain.Rating;
import com.podio.sdk.filter.RatingFilter;
import com.podio.sdk.client.RequestFuture;

/**
 * Enables access to the organization API end point.
 * 
 * @author Daniel Franch
 */
public class RatingProvider extends BasicPodioProvider {

    /**
     * Should give something about the ratings of an type. E.g. an Item or a Status
     * Heavily inspired by the OrganizationProvider.
     * 
     * @param resultListener
     *        The callback implementation called when the items are fetched.
     *        Null is valid, but doesn't make any sense.
     * @return A ticket which the caller can use to identify this request with.
     */
    public RequestFuture<Rating> get(String ref_type, long ref_id) {
        RatingFilter filter = new RatingFilter().withTypeAndId(ref_type, ref_id);

        return get(filter, Rating.class);
    }

}
