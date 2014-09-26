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

package com.podio.sdk.filter;

public final class ItemFilter extends BasicPodioFilter {

    public ItemFilter() {
        super("item");
    }

    public ItemFilter withApplicationId(long applicationId) {
        addPathSegment("app");
        addPathSegment(Long.toString(applicationId, 10));
        return this;
    }

    public ItemFilter withApplicationIdFilter(long applicationId) {
        addPathSegment("app");
        addPathSegment(Long.toString(applicationId, 10));
        addPathSegment("filter");
        return this;
    }

    public ItemFilter withApplicationAndViewIdFilter(long applicationId, long viewId) {
        addPathSegment("app");
        addPathSegment(Long.toString(applicationId, 10));
        addPathSegment("filter");
        addPathSegment(Long.toString(viewId, 10));
        return this;
    }

    public ItemFilter withItemId(long itemId) {
        addPathSegment(Long.toString(itemId, 10));
        return this;
    }
}
