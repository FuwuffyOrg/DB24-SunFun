package oop.sunfun.database.dao;

import oop.sunfun.database.data.activity.ActivityData;
import oop.sunfun.database.data.activity.ReviewData;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ActivityDAO extends AbstractDAO {
    private static final Logger LOGGER = Logger.getLogger(ActivityDAO.class.getName());

    private static final String GET_ALL_ACTIVITIES = "SELECT * FROM `attivita`";

    private static final String CREATE_ACTIVITY = "INSERT INTO `attivita`(`nome`, `descrizione`) VALUES (?,?)";

    private static final String DELETE_ACTIVITY = "DELETE FROM `attivita` WHERE `nome`=?";

    private static final String GET_ACTIVITY_REVIEWS = "";

    private static final String CREATE_ACTIVITY_REVIEW = "";

    public static Set<ActivityData> getAllActivities() {
        final Set<ActivityData> activities = new HashSet<>();
        try {
            DB_CONNECTION.openConnection();
            final List<Map<String, Object>> queryData = DB_CONNECTION.getQueryData(GET_ALL_ACTIVITIES);
            for (final Map<String, Object> activity : queryData) {
                activities.add(new ActivityData((String) activity.get("nome"), (String) activity.get("descrizione")));
            }
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't fetch all the activities", err);
            DB_CONNECTION.closeConnection();
        }
        return activities;
    }

    public static void createNewActivity(final ActivityData activityData) {
        try {
            DB_CONNECTION.openConnection();
            DB_CONNECTION.setQueryData(CREATE_ACTIVITY, activityData.name(), activityData.description());
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't create a new activity " + activityData.name(), err);
            DB_CONNECTION.closeConnection();
        }
    }

    public static void deleteActivity(final ActivityData activityData) {
        try {
            DB_CONNECTION.openConnection();
            DB_CONNECTION.setQueryData(DELETE_ACTIVITY, activityData.name());
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't delete the activity " + activityData.name(), err);
            DB_CONNECTION.closeConnection();
        }
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
