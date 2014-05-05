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

package com.podio.sdk.client.delegate;

import com.podio.sdk.RestClientDelegate;

public abstract class JsonClientDelegate implements RestClientDelegate {
    private ItemParser<?> parser;

    public void setItemParser(ItemParser<?> parser) {
        this.parser = parser;
    }

    protected Object parseJson(String json) throws InvalidParserException {
        testParser();
        return parser.parseToItem(json);
    }

    protected String parseItem(Object item) throws InvalidParserException {
        testParser();
        return parser.parseToJson(item);
    }

    private void testParser() throws InvalidParserException {
        if (parser == null) {
            throw new InvalidParserException(
                    "Parser mustn't be null. Have you called the setItemParser method?");
        }
    }
}
