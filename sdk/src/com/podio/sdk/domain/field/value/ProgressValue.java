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

package com.podio.sdk.domain.field.value;

import java.util.HashMap;

/**
 * @author László Urszuly
 */
public final class ProgressValue extends AbstractValue {
    private final Integer value;

    public ProgressValue(int value) {
        this.value = Integer.valueOf(value);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ProgressValue) {
            ProgressValue other = (ProgressValue) o;

            if (other != null && other.value != null && this.value != null) {
                return other.value.intValue() == this.value.intValue();
            }
        }

        return false;
    }

    @Override
    public Object getPushData() {
        HashMap<String, Integer> data = null;

        if (value != null) {
            data = new HashMap<String, Integer>();
            data.put("value", value);
        }

        return data;
    }

    @Override
    public int hashCode() {
        return this.value != null ? this.value.intValue() : 0;
    }

    public int getValue() {
        return value != null ? value.intValue() : 0;
    }

}
