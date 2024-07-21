package oop.sunfun.database.dao;

import oop.sunfun.database.data.activity.ActivityData;
import oop.sunfun.database.data.activity.ReviewData;
import oop.sunfun.ui.util.Pair;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ActivityDAO extends AbstractDAO {
    /**
     * Logger to help diagnose sql and database errors.
     */
    private static final Logger LOGGER = Logger.getLogger(ActivityDAO.class.getName());

    /**
     * Query to fetch all the activities within the database.
     */
    private static final String GET_ALL_ACTIVITIES = "SELECT `a`.`nome`, `a`.`descrizione`, AVG(`r`.`voto`) AS "
            + "`media_voto` FROM `attivita` `a` LEFT JOIN `recensione` `r` ON `r`.`fk_attivita` = `a`.`nome` "
            + "GROUP BY `a`.`nome`, `a`.`descrizione`";

    /**
     * Query to create a new activity in the database.
     */
    private static final String CREATE_ACTIVITY = "INSERT INTO `attivita`(`nome`, `descrizione`) VALUES (?,?);";

    /**
     * Query to delete an activity within the database.
     */
    private static final String DELETE_ACTIVITY = "DELETE FROM `attivita` WHERE `nome`=?;";

    /**
     * Query to fetch all the reviews of an activity within the database.
     */
    private static final String GET_ACTIVITY_REVIEWS = "SELECT `r`.`voto`, `r`.`descrizione`, `a`.`nome`, "
            + "`a`.`cognome` FROM `recensione` `r` JOIN `account_data` `a` ON `r`.`fk_account` = "
            + "`a`.`email` WHERE `r`.`fk_attivita`=?;";

    /**
     * Query to create a new review for an activity.
     */
    private static final String CREATE_ACTIVITY_REVIEW = "INSERT INTO `recensione`(`voto`, `descrizione`, "
            + "`fk_attivita`, `fk_account`) VALUES (?,?,?,?);";

    /**
     * Query to fetch all the activities performed by a group in a specific day within the database.
     */
    private static final String GET_ACTIVITIES_GROUP_DATE = "SELECT *, AVG(r.`voto`) AS media_voto FROM `svolgimento` "
            + "`s` JOIN `attivita` `a` ON `s`.`fk_attivita` = `a`.`nome` LEFT JOIN `recensione` `r` ON "
            + "`r`.`fk_attivita` = `a`.`nome` WHERE `s`.`fk_gruppo`=? AND `s`.`fk_giornata`=? GROUP BY "
            + "`s`.`ora_inizio`, `s`.`ora_fine`;";

    /**
     * Query to add an activity to a group at a date.
     */
    private static final String ADD_ACTIVITY_TO_GROUP = "INSERT INTO `svolgimento`(`ora_inizio`, `ora_fine`, "
            + "`fk_attivita`, `fk_gruppo`, `fk_giornata`) VALUES (?,?,?,?,?);";

    /**
     * Method to fetch all the activities of the database.
     * @return All the activities in the database.
     */
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

    /**
     * Creates a new activity in the database.
     * @param activityData The data of the activity to add.
     */
    public static void createNewActivity(final ActivityData activityData) {
        try {
            DB_CONNECTION.openConnection();
            DB_CONNECTION.setQueryData(CREATE_ACTIVITY, activityData.name(), activityData.description());
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't create a new activity " + activityData.name(), err);
            DB_CONNECTION.closeConnection();
        }
    }

    /**
     * Deletes a given activity from the database.
     * @param activityData The activity to erase.
     */
    public static void deleteActivity(final ActivityData activityData) {
        try {
            DB_CONNECTION.openConnection();
            DB_CONNECTION.setQueryData(DELETE_ACTIVITY, activityData.name());
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't delete the activity " + activityData.name(), err);
            DB_CONNECTION.closeConnection();
        }
    }

    /**
     * Gets all the reviews of a given activity.
     * @param activityName The activity to get the reviews from.
     * @return All the reviews of the given activity.
     */
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

    /**
     * Creates a new review in the database.
     * @param grade The grade of the review.
     * @param description The description of the review.
     * @param activity The activity that the review is for.
     * @param accountEmail The email of the author of the review.
     */
    public static void createNewReviewForActivity(final int grade, final String description, final String activity,
                                                  final String accountEmail) {
        try {
            DB_CONNECTION.openConnection();
            DB_CONNECTION.setQueryData(CREATE_ACTIVITY_REVIEW, grade, description, activity, accountEmail);
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't create the review " + grade + " " + description
                    + " for " + activity, err);
            DB_CONNECTION.closeConnection();
        }
    }

    /**
     * Get all the activities that a group does during a date.
     * @param groupName The group to check the reviews of.
     * @param date The date to check the reviews during.
     * @return A map containing the activities, start and end time.
     */
    public static Map<ActivityData, Pair<Time, Time>> getActivitiesDoneByGroupDuring(final String groupName,
                                                                                     final Date date) {
        final Map<ActivityData, Pair<Time, Time>> activities = new LinkedHashMap<>();
        try {
            DB_CONNECTION.openConnection();
            final List<Map<String, Object>> queryData = DB_CONNECTION.getQueryData(GET_ACTIVITIES_GROUP_DATE,
                    groupName, date);
            for (final Map<String, Object> activity : queryData) {
                final BigDecimal avgGrade = (BigDecimal) activity.get("media_voto");
                activities.put(new ActivityData((String) activity.get("nome"), (String) activity.get("descrizione"),
                                avgGrade != null ? avgGrade.floatValue() : 0.0f), new Pair<>(
                                        (Time) activity.get("ora_inizio"), (Time) activity.get("ora_fine")));
            }
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't fetch all activities of " + groupName + " at "
                    + date, err);
            DB_CONNECTION.closeConnection();
        }
        return activities;
    }

    /**
     * Adds an activity to the group.
     * @param groupName The name of the group to add the activity to.
     * @param activityName The name of the activity to add.
     * @param day The day during which it is done.
     * @param timeStart The start time of the activity.
     * @param timeEnd The end time of the activity.
     */
    public static void addActivityToGroup(final String groupName, final String activityName, final Date day,
                                          final Time timeStart, final Time timeEnd) {
        try {
            DB_CONNECTION.openConnection();
            DB_CONNECTION.setQueryData(ADD_ACTIVITY_TO_GROUP, timeStart, timeEnd, activityName, groupName, day);
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't add the activity " + activityName
                    + " to the group " + groupName, err);
            DB_CONNECTION.closeConnection();
        }
    }
}
