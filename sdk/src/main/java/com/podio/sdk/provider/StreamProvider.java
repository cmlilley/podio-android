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

import com.podio.sdk.Filter;
import com.podio.sdk.Provider;
import com.podio.sdk.Request;
import com.podio.sdk.domain.stream.EventContext;

/**
 * Enables access to the stream API end point.
 *
 * @author Tobias Lindberg
 */
public class StreamProvider extends Provider {

    static class StreamFilter extends Filter {

        protected StreamFilter() {
            super("stream");
        }

        public StreamFilter withLimit(int limit) {
            this.addQueryParameter("limit", Integer.toString(limit, 10));
            return this;
        }

        public StreamFilter withOffset(int offset) {
            this.addQueryParameter("offset", Integer.toString(offset, 10));
            return this;
        }
    }

    /**
     * Fetches the global stream.
     *
     * @return A ticket which the caller can use to identify this request with.
     */
    public Request<EventContext[]> getGlobalStream(int limit, int offset) {
        StreamFilter filter = new StreamFilter();
        filter.withLimit(limit);
        filter.withOffset(offset);
        return get(filter, EventContext[].class);
    }
}
