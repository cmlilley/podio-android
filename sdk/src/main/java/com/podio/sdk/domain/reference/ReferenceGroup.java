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
package com.podio.sdk.domain.reference;

import java.util.List;

/**
 * The base class of all reference groups when performing a reference search.
 *
 * @author Tobias Lindberg
 */
public abstract class ReferenceGroup {

    public enum ReferenceGroupName {
        space_contacts,
        spaces,
        app,
        profiles,
        space_members,
        tasks,
        apps,
        unknown;

        public static ReferenceGroupName getReferenceGroupName(String name) {
            try {
                return ReferenceGroupName.valueOf(name);
            } catch (NullPointerException e) {
                return ReferenceGroupName.unknown;
            } catch (IllegalArgumentException e) {
                return ReferenceGroupName.unknown;
            }
        }
    }

    /**
     * Default Reference target post object.
     */
    public static class ReferenceTarget {
        public enum Target {
            task_responsible,
            alert,
            conversation,
            grant,
            item_field; // TODO Add the missing targets.
        }

        private final Target target;
        private final String text;
        private final Integer limit;

        public ReferenceTarget(Target target, String text, int limit) {
            this.target = target;
            this.text = text;
            this.limit = limit;
        }
    }

    // TODO There are more reference targets with their own unique TargetParams.

    /**
     * Reference target post object when you are reference searching for a specific field.
     */
    public static class ItemFieldReferenceTarget extends ReferenceTarget {

        public static class TargetParams {
            private final Long field_id;
            private final List<Long> not_item_ids;

            public TargetParams(long field_id, List<Long> not_item_ids) {
                this.field_id = field_id;
                this.not_item_ids = not_item_ids;
            }
        }

        private final TargetParams target_params;

        ItemFieldReferenceTarget(String text, int limit, long field_id, List<Long> notItemIds) {
            super(Target.item_field, text, limit);
            target_params = new TargetParams(field_id, notItemIds);
        }
    }

    /**
     * Reference target post object when you are updating a task with the responsible person.
     */
    public static class TaskResponsibledReferenceTarget extends ReferenceTarget {

        public static class TargetParams {
            private final Long task_id;

            public TargetParams(long task_id) {
                this.task_id = task_id;
            }
        }

        private final TargetParams target_params;

        TaskResponsibledReferenceTarget(String text, int limit, long task_id) {
            super(Target.task_responsible, text, limit);
            target_params = new TargetParams(task_id);
        }
    }

    private final String name = null;

    public ReferenceGroupName getName() {
        return ReferenceGroupName.getReferenceGroupName(name);
    }
}
