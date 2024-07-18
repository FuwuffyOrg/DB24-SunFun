package oop.sunfun.database.dao;

import oop.sunfun.database.data.activity.ActivityData;
import oop.sunfun.database.data.activity.ReviewData;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ActivityDAO extends AbstractDAO {
    private static final Logger LOGGER = Logger.getLogger(ActivityDAO.class.getName());

    private static final String GET_ALL_ACTIVITIES = "SELECT a.nome, a.descrizione, AVG(r.`voto`) AS media_voto FROM "
            + "`attivita` a LEFT JOIN `recensione` r ON r.`fk_attivita` = a.`nome` GROUP BY a.nome, a.descrizione";

    private static final String CREATE_ACTIVITY = "INSERT INTO `attivita`(`nome`, `descrizione`) VALUES (?,?)";

    private static final String DELETE_ACTIVITY = "DELETE FROM `attivita` WHERE `nome`=?";

    private static final String GET_ACTIVITY_REVIEWS = "SELECT r.voto, r.descrizione, a.nome, a.surname FROM "
            + "recensione r JOIN account_data a ON r.fk_account = a.email WHERE r.fk_attivita=?;";

    private static final String CREATE_ACTIVITY_REVIEW = "INSERT INTO `recensione`(`voto`, `descrizione`, "
            + "`fk_attivita`, `fk_account`) VALUES (?,?,?,?)";

    public static Set<ActivityData> getAllActivities() {
        final Set<ActivityData> activities = new HashSet<>();
        try {
            DB_CONNECTION.openConnection();
            final List<Map<String, Object>> queryData = DB_CONNECTION.getQueryData(GET_ALL_ACTIVITIES);
            for (final Map<String, Object> activity : queryData) {
                final BigDecimal avgGrade = (BigDecimal) activity.get("media_voto");
                activities.add(new ActivityData((String) activity.get("nome"), (String) activity.get("descrizione"),
                        avgGrade != null ? avgGrade.floatValue() : 0.0f));
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

    public static Set<ReviewData> getReviewsFromActivity(final String activityName) {
        final Set<ReviewData> reviews = new HashSet<>();
        try {
            DB_CONNECTION.openConnection();
            final List<Map<String, Object>> queryData = DB_CONNECTION.getQueryData(GET_ACTIVITY_REVIEWS, activityName);
            for (final Map<String, Object> review : queryData) {
                reviews.add(new ReviewData((int) review.get("voto"), (String) review.get("descrizione"),
                        (String) review.get("nome"), (String) review.get("surname")));
            }
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't fetch all the reviews for the activity " + activityName, err);
            DB_CONNECTION.closeConnection();
        }
        return reviews;
    }

    public static void createNewReviewForActivity(final int grade, final String description, final String activity,
                                                  final String accountEmail) {
        try {
            DB_CONNECTION.openConnection();
            DB_CONNECTION.setQueryData(CREATE_ACTIVITY_REVIEW, grade, description, activity, accountEmail);
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't create a the review " + grade + " " + description
                    + " for " + activity, err);
            DB_CONNECTION.closeConnection();
        }
    }
}
