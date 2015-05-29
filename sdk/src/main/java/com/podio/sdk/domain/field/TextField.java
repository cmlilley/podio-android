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

package com.podio.sdk.domain.field;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.podio.sdk.internal.Utils;

/**
 * The Podio Text field domain object.
 *
 * @author László Urszuly
 */
public class TextField extends Field<TextField.Value> {
    /**
     * This class describes the particular settings of a Text field configuration.
     *
     * @author László Urszuly
     */
    private static class Settings {
        private final String size = null;
    }

    /**
     * This class describes the specific configuration of a Text field.
     *
     * @author László Urszuly
     */
    public static class Configuration extends Field.Configuration {
        private final Value default_value = null;
        private final Settings settings = null;

        public Value getDefaultValue() {
            return default_value;
        }

        public Size getSize() {
            try {
                return Size.valueOf(settings.size);
            } catch (NullPointerException e) {
                return Size.undefined;
            } catch (IllegalArgumentException e) {
                return Size.undefined;
            }
        }
    }

    /**
     * This class describes a Text field value.
     *
     * @author László Urszuly
     */
    public static class Value extends Field.Value {
        private final String value;

        public Value(String value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Value) {
                Value other = (Value) o;

                if (other.value != null) {
                    return other.value.equals(this.value);
                }
            }

            return false;
        }

        @Override
        public Map<String, Object> getCreateData() {
            HashMap<String, Object> data = null;

            if (Utils.notEmpty(value)) {
                data = new HashMap<String, Object>();
                data.put("value", value);
            }

            return data;
        }

        @Override
        public int hashCode() {
            return value != null ? value.hashCode() : 0;
        }

        public String getValue() {
            return value;
        }

    }

    /**
     * The values for the named sizes a text field can have.
     *
     * @author László Urszuly
     */
    public static enum Size {
        large, small, undefined
    }

    // Private fields.
    private final Configuration config = null;
    private final ArrayList<Value> values;

    public TextField(String externalId) {
        super(externalId);
        this.values = new ArrayList<Value>();
    }

    @Override
    public void addValue(Value value) {
            values.add(0,value);
    }

    @Override
    public Value getValue(int index) {
        return values != null ? values.get(index) : null;
    }

    @Override
    public List<Value> getValues() {
        return values;
    }

    @Override
    public void removeValue(Value value) {
        if (values != null && values.contains(value)) {
            values.remove(value);
        }
    }

    @Override
    public int valuesCount() {
        return values != null ? values.size() : 0;
    }

    public Configuration getConfiguration() {
        return config;
    }
}
