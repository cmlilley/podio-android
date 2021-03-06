
package com.podio.sdk.domain.stream;

import com.podio.sdk.domain.Application;
import com.podio.sdk.domain.Byline;
import com.podio.sdk.domain.Comment;
import com.podio.sdk.domain.File;
import com.podio.sdk.domain.Organization;
import com.podio.sdk.domain.ReferenceType;
import com.podio.sdk.domain.Right;
import com.podio.sdk.domain.Space;
import com.podio.sdk.internal.Utils;

import java.util.Date;
import java.util.List;

/**
 * This class is the base class for all stream objects.
 * <p/>
 * In most cases all information we are interested in is provided by this class so even if you are
 * getting notifications of type {@link UnknownEventContext} there is still plenty of information
 * available in that one.
 *
 */
public abstract class EventContext {

    private final List<File> files = null;
    private final String type = null;
    private final Long id = null;
    private final List<Right> rights = null;
    private final String title = null;
    private final Application app = null;
    private final Boolean comments_allowed = null;
    private final Space space = null;
    private final Byline created_by = null;
    private final String created_on = null;

    private final List<Comment> comments = null;

    private final Organization org = null;

    private List<EventActivity> activity = null;

    public static class UserRatings {
        private Integer like = null;

        public Integer getLike() {
            return Utils.getNative(like, 0);
        }
    }

    private UserRatings user_ratings = null;

    public UserRatings getUserRatings() {
        return user_ratings;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public Space getSpace() {
        return space;
    }

    public Boolean isCommentsAllowed() {
        return Utils.getNative(comments_allowed, false);
    }

    public Application getApplication() {
        return app;
    }

    public String getTitle() {
        return title;
    }

    public List<File> getFiles() {
        return files;
    }

    public Date getCreatedOnDate() {
        return Utils.parseDateTimeUtc(created_on);
    }

    public String getCreatedOnString() {
        return created_on;
    }

    public Byline getCreatedBy() {
        return created_by;
    }

    public long getId() {
        return Utils.getNative(id, -1L);
    }

    public ReferenceType getType() {
        return ReferenceType.getType(type);
    }

    public List<EventActivity> getActivity() {
        return activity;
    }

    public Organization getOrg() {
        return org;
    }

    /**
     * Checks whether the list of rights the user has for this stream object contains <em>all</em>
     * the given permissions.
     *
     * @param permissions
     *         The list of permissions to check for.
     *
     * @return Boolean true if all given permissions are found or no permissions are given. Boolean
     * false otherwise.
     */
    public boolean hasRights(Right... permissions) {
        if (rights != null) {
            for (Right permission : permissions) {
                if (!rights.contains(permission)) {
                    return false;
                }
            }

            return true;
        }

        return false;
    }

}
