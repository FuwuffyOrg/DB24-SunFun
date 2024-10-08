package oop.sunfun.database.dao;

import oop.sunfun.database.data.admin.MembershipType;
import oop.sunfun.database.data.admin.PeriodData;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class PeriodDAO extends AbstractDAO {
    /**
     * Logger to help diagnose sql and database errors.
     */
    private static final Logger LOGGER = Logger.getLogger(PeriodDAO.class.getName());

    /**
     * Query to fetch all the possible periods in the database.
     */
    private static final String GET_ALL_PERIODS = "SELECT * FROM `periodo` ORDER BY `data_inizio`;";

    /**
     * Query to create a period given the start and end date.
     */
    private static final String CREATE_PERIOD = "INSERT INTO `periodo`(`data_inizio`, `data_fine`) VALUES (?,?);";

    /**
     * Query to delete a period given the start and end date.
     */
    private static final String ERASE_PERIOD = "DELETE FROM `periodo` WHERE `data_inizio`=? AND `data_fine`=?;";

    /**
     * Query to get all the dates used by the database.
     */
    private static final String GET_ALL_DATES = "SELECT * FROM `giornata` ORDER BY `data`;";

    /**
     * Query to delete the membership of a participant.
     */
    private static final String DELETE_MEMBERSHIP = "DELETE FROM `modalita` WHERE `fk_data_inizio` = ? AND "
            + "`fk_data_fine` = ? AND `fk_partecipante` = ?;";

    /**
     * Query to add or update the membership of a participant.
     */
    private static final String ADD_MEMBERSHIP = "INSERT INTO `modalita`(`tempo_pieno`, `pasti`, `fk_data_inizio`, "
            + "`fk_data_fine`, `fk_partecipante`) VALUES (?,?,?,?,?) ON DUPLICATE KEY UPDATE `tempo_pieno` = "
            + "VALUES(`tempo_pieno`), `pasti` = VALUES(`pasti`);";

    /**
     * Query to create a date used by the database.
     */
    private static final String CREATE_DATE = "INSERT INTO `giornata`(`data`, `fk_periodo_inizio`, `fk_periodo_fine`) "
            + "VALUES (?,?,?);";

    /**
     * Query to erase all the dates between two other dates.
     */
    private static final String ERASE_DATES_BETWEEN = "DELETE FROM `giornata` WHERE `data`>=? AND `data`<=?;";

    /**
     * Fetch all the periods from the database.
     * @return All the periods within the database.
     */
    public static Set<PeriodData> getAllPeriods() {
        final Set<PeriodData> periods = new HashSet<>();
        try {
            DB_CONNECTION.openConnection();
            final List<Map<String, Object>> queryData = DB_CONNECTION.getQueryData(GET_ALL_PERIODS);
            for (final Map<String, Object> period : queryData) {
                periods.add(new PeriodData(
                        (Date) period.get("data_inizio"),
                        (Date) period.get("data_fine")
                ));
            }
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't fetch the periods", err);
            DB_CONNECTION.closeConnection();
        }
        return periods;
    }

    /**
     * Creates a period and adds it to the database.
     * @param periodData The period to add to the database.
     */
    public static void createPeriod(final PeriodData periodData) {
        try {
            DB_CONNECTION.openConnection();
            DB_CONNECTION.setQueryData(CREATE_PERIOD, periodData.startDate(), periodData.endDate());
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't create the new period", err);
            DB_CONNECTION.closeConnection();
        }
    }

    /**
     * Deletes a period from the database.
     * @param periodData The period to erase.
     */
    public static void deletePeriod(final PeriodData periodData) {
        try {
            DB_CONNECTION.openConnection();
            DB_CONNECTION.setQueryData(ERASE_PERIOD, periodData.startDate(), periodData.endDate());
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't erase the period " + periodData.startDate()
                    + " " + periodData.endDate(), err);
            DB_CONNECTION.closeConnection();
        }
    }

    /**
     * Fetches all the dates within the database.
     * @return A set containing all the dates used on the database.
     */
    public static Set<Date> getAllDates() {
        final Set<Date> dates = new HashSet<>();
        try {
            DB_CONNECTION.openConnection();
            final List<Map<String, Object>> queryData = DB_CONNECTION.getQueryData(GET_ALL_DATES);
            for (final Map<String, Object> date : queryData) {
                dates.add((Date) date.get("data"));
            }
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't fetch the dates", err);
            DB_CONNECTION.closeConnection();
        }
        return dates;
    }

    /**
     * Method to add a new date to the database.
     * @param date The date to add.
     * @param period The period that the date is int.
     */
    public static void createDate(final Date date, final PeriodData period) {
        try {
            DB_CONNECTION.openConnection();
            DB_CONNECTION.setQueryData(CREATE_DATE, date, period.startDate(), period.endDate());
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't create the new date " + date, err);
            DB_CONNECTION.closeConnection();
        }
    }

    /**
     * Method to add or update the membership of a participant.
     * @param participantCodFisc The participant's code.
     * @param food Checks if a participant will get food at the camp.
     * @param periodData The period that he's going to get enrolled to.
     * @param membership The amount of time he's going to spend there.
     */
    public static void addOrUpdateMembership(final String participantCodFisc, final boolean food,
                                             final PeriodData periodData, final MembershipType membership) {
        try {
            DB_CONNECTION.openConnection();
            if (membership == MembershipType.NONE) {
                DB_CONNECTION.setQueryData(DELETE_MEMBERSHIP, periodData.startDate(),
                        periodData.endDate(), participantCodFisc);
            } else {
                DB_CONNECTION.setQueryData(ADD_MEMBERSHIP, membership == MembershipType.FULL_TIME, food,
                        periodData.startDate(), periodData.endDate(), participantCodFisc);
            }
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't add or update the membership for "
                    + participantCodFisc, err);
            DB_CONNECTION.closeConnection();
        }
    }

    /**
     * Erases all the dates of the database between two other dates.
     * @param startDate The start date of the interval.
     * @param endDate The end date of the interval.
     */
    public static void deleteDatesBetween(final Date startDate, final Date endDate) {
        try {
            DB_CONNECTION.openConnection();
            DB_CONNECTION.setQueryData(ERASE_DATES_BETWEEN, startDate, endDate);
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't erase the dates between " + startDate + " " + endDate,
                    err);
            DB_CONNECTION.closeConnection();
        }
    }
}
