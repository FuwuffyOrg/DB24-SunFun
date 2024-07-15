package oop.sunfun.database.dao;

import oop.sunfun.database.data.activity.ActivityData;
import oop.sunfun.database.data.activity.ReviewData;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public final class ActivityDAO extends AbstractDAO {
    private static final Logger LOGGER = Logger.getLogger(ActivityDAO.class.getName());

    private static final String GET_ALL_ACTIVITIES = "";

    private static final String CREATE_ACTIVITY = "";

    private static final String DELETE_ACTIVITY = "";

    private static final String GET_ACTIVITY_REVIEWS = "";

    private static final String CREATE_ACTIVITY_REVIEW = "";

    public static Set<ActivityData> getAllActivities() {
        final Set<ActivityData> activities = new HashSet<>();
        // TODO: Complete method and query
        return activities;
    }

    public static void createNewActivity(final ActivityData activityData) {
        // TODO: Complete method and query
    }

    public static void deleteActivity(final ActivityData activityData) {
        // TODO: Complete method and query
    }

    public static Set<ReviewData> getReviewsFromActivity() {
        final Set<ReviewData> reviews = new HashSet<>();
        // TODO: Complete method and query
        return reviews;
    }

    public static void createNewReviewForActivity(final ReviewData reviewData) {
        // TODO: Complete method and query
    }
}
