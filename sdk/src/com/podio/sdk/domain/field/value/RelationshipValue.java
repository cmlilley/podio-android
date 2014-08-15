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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.podio.sdk.domain.Application;
import com.podio.sdk.domain.Client;
import com.podio.sdk.domain.File;
import com.podio.sdk.domain.Space;
import com.podio.sdk.domain.User;
import com.podio.sdk.internal.Utils;

/**
 * @author László Urszuly
 */
public final class RelationshipValue extends AbstractValue {

    public static final class Data {
        private final Application app = null;
        private final Client created_via = null;
        private final Long app_item_id = null;
        private final Long item_id;
        private final Long revision = null;
        private final List<File> files = null;
        private final Space space = null;
        private final String created_on = null;
        private final String link = null;
        private final String title = null;
        private final User created_by = null;

        public Data(Long itemId) {
            this.item_id = itemId;
        }

        public Application getApplication() {
            return app;
        }

        public Client getCreatingClient() {
            return created_via;
        }

        public long getAppItemId() {
            return Utils.getNative(app_item_id, -1L);
        }

        public long getItemId() {
            return Utils.getNative(item_id, -1L);
        }

        public long getRevisionId() {
            return Utils.getNative(revision, -1L);
        }

        public List<File> getFiles() {
            return new ArrayList<File>(files);
        }

        public Space getSpace() {
            return space;
        }

        public Date getCreationDate() {
            return Utils.parseDateTime(created_on);
        }

        public String getLink() {
            return link;
        }

        public String getTitle() {
            return title;
        }

        public User getCreatingUser() {
            return created_by;
        }
    }

    private final Data value;

    public RelationshipValue(Data data) {
        this.value = data;
    }

    public RelationshipValue(long itemId) {
        this.value = new Data(itemId);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof RelationshipValue) {
            RelationshipValue other = (RelationshipValue) o;

            if (other.value != null && other.value.app_item_id != null && this.value != null && this.value.app_item_id != null) {
                return other.value.app_item_id.intValue() == this.value.app_item_id.intValue();
            }
        }

        return false;
    }

    @Override
    public Object getPushData() {
        HashMap<String, Long> data = null;

        if (value != null && value.app_item_id != null) {
            data = new HashMap<String, Long>();
            data.put("value", value.app_item_id);
        }

        return data;
    }

    @Override
    public int hashCode() {
        return value != null && value.app_item_id != null ? value.app_item_id.hashCode() : 0;
    }

    public Application getApplication() {
        return value != null ? value.getApplication() : null;
    }

    public Client getCreatingClient() {
        return value != null ? value.getCreatingClient() : null;
    }

    public long getAppItemId() {
        return value != null ? value.getAppItemId() : -1L;
    }

    public long getItemId() {
        return value != null ? value.getItemId() : -1L;
    }

    public long getRevisionId() {
        return value != null ? value.getRevisionId() : -1L;
    }

    public List<File> getFiles() {
        return value != null ? value.getFiles() : null;
    }

    public Space getSpace() {
        return value != null ? value.getSpace() : null;
    }

    public Date getCreationDate() {
        return value != null ? value.getCreationDate() : null;
    }

    public String getLink() {
        return value != null ? value.getLink() : null;
    }

    public String getTitle() {
        return value != null ? value.getTitle() : null;
    }

    public User getCreatingUser() {
        return value != null ? value.getCreatingUser() : null;
    }

}
