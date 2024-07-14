package oop.sunfun.database.dao;

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
    private static final Logger LOGGER = Logger.getLogger(PeriodDAO.class.getName());

    private static final String GET_ALL_PERIODS = "SELECT * FROM `periodo` ORDER BY `data_inizio`";

    private static final String CREATE_PERIOD = "INSERT INTO `periodo`(`data_inizio`, `data_fine`) VALUES (?,?)";

    private static final String ERASE_PERIOD = "DELETE FROM `periodo` WHERE `data_inizio`=? AND `data_fine`=?";

    private PeriodDAO() {
        // Useless constructor
    }

    public static Set<PeriodData> getAllPeriods() {
        final Set<PeriodData> categories = new HashSet<>();
        try {
            DB_CONNECTION.openConnection();
            final List<Map<String, Object>> queryData = DB_CONNECTION.getQueryData(GET_ALL_PERIODS);
            for (final Map<String, Object> category : queryData) {
                categories.add(new PeriodData(
                        (Date) category.get("data_inizio"),
                        (Date) category.get("data_fine")
                ));
            }
        } catch (final SQLException err) {
            LOGGER.log(Level.SEVERE, "Couldn't fetch the periods", err);
            DB_CONNECTION.closeConnection();
        }
        return categories;
    }

    public static void createPeriod(final PeriodData periodData) {
        try {
            DB_CONNECTION.openConnection();
            DB_CONNECTION.setQueryData(CREATE_PERIOD, periodData.startDate(), periodData.endDate());
        } catch (final SQLException err) {
            LOGGER.log(Level.SEVERE, "Couldn't create the new period", err);
            DB_CONNECTION.closeConnection();
        }
    }

    public static void deletePeriod(final PeriodData periodData) {
        try {
            DB_CONNECTION.openConnection();
            DB_CONNECTION.setQueryData(ERASE_PERIOD, periodData.startDate(), periodData.endDate());
        } catch (final SQLException err) {
            LOGGER.log(Level.SEVERE, "Couldn't erase the period" + periodData.startDate() + " "
                    + periodData.endDate(), err);
            DB_CONNECTION.closeConnection();
        }
    }
}
