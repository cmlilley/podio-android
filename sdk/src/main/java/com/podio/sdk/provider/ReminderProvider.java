package com.podio.sdk.provider;

import com.podio.sdk.Filter;
import com.podio.sdk.Provider;
import com.podio.sdk.Request;
import com.podio.sdk.domain.Reminder;

/**
 * Created by sai on 9/30/16.
 */

public class ReminderProvider extends Provider {

    public static class ReminderFilter extends Filter {
        private static String itemRefType = "item";
        private static String refType = "ref_type";
        private static String refId = "ref_id";

        public ReminderFilter() {
            super("reminder/");
        }

        public ReminderFilter item(long itemId) {
            this.addPathSegment(itemRefType);
            this.addPathSegment(String.valueOf(itemId));

            this.addQueryParameter(refType, itemRefType);
            this.addQueryParameter(refId, String.valueOf(itemId));

            return this;
        }

    }

    public Request<Reminder> getReminder(long itemId) {
        return get(new ReminderFilter().item(itemId), Reminder.class);
    }

    public Request<Void> deleteReminder(long itemId) {
        return delete(new ReminderFilter().item(itemId));
    }

    public Request<Reminder> createOrUpdateReminder(long itemId, Reminder.CreateData createData) {
        return put(new ReminderFilter().item(itemId), createData, Reminder.class);
    }

}
