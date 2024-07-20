package oop.sunfun.database.dao;

import oop.sunfun.database.data.person.EducatorData;
import oop.sunfun.database.data.person.VoluntaryData;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class EducatorDAO extends AbstractDAO {
    /**
     * Logger to help diagnose sql and database errors.
     */
    private static final Logger LOGGER = Logger.getLogger(EducatorDAO.class.getName());

    /**
     * Query to fetch all the educators in the database.
     */
    private static final String GET_ALL_EDUCATORS = "SELECT `e`.`codice_fiscale`, `e`.`nome`, `e`.`cognome`, "
            + "`e`.`cellulare`, `e`.`fk_account`, `e`.`fk_gruppo` FROM `educatore` `e`";

    /**
     * Query to fetch all the voluntaries in the database.
     */
    private static final String GET_ALL_VOLUNTARY = "SELECT `v`.`codice_fiscale`, `v`.`nome`, `v`.`cognome`, "
            + "`v`.`fk_account` FROM `volontario` `v`";

    /**
     * Query to change the group an educator educates.
     */
    private static final String UPDATE_EDUCATOR_GROUP = "UPDATE `educatore` SET `fk_gruppo`=? WHERE "
            + "`educatore`.`codice_fiscale`=?";

    /**
     * Query to create a new educator in the database.
     */
    private static final String CREATE_EDUCATOR = "INSERT INTO `educatore`(`codice_fiscale`, `nome`, `cognome`, "
            + "`cellulare`, `fk_account`) VALUES (?,?,?,?,?)";

    /**
     * Query to create a new voluntary in the database.
     */
    private static final String CREATE_VOLUNTARY = "INSERT INTO `volontario`(`codice_fiscale`, `fk_account`, `nome`, "
            + "`cognome`) VALUES (?,?,?,?)";

    /**
     * Fetches all the educators from the database.
     * @return All the educators within the database.
     */
    public static Set<EducatorData> getAllEducators() {
        final Set<EducatorData> educators = new HashSet<>();
        try {
            DB_CONNECTION.openConnection();
            final List<Map<String, Object>> queryData = DB_CONNECTION.getQueryData(GET_ALL_EDUCATORS);
            for (final Map<String, Object> educator : queryData) {
                educators.add(new EducatorData((String) educator.get("codice_fiscale"),
                        (String) educator.get("nome"), (String) educator.get("cognome"),
                        (String) educator.get("fk_account"), (String) educator.get("cellulare"),
                        Optional.ofNullable((String) educator.get("fk_gruppo"))));
            }
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't fetch all the educators", err);
            DB_CONNECTION.closeConnection();
        }
        return educators;
    }

    /**
     * Fetches all the voluntaries from the database.
     * @return All the voluntaries within the database.
     */
    public static Set<VoluntaryData> getAllVoluntary() {
        final Set<VoluntaryData> voluntaries = new HashSet<>();
        try {
            DB_CONNECTION.openConnection();
            final List<Map<String, Object>> queryData = DB_CONNECTION.getQueryData(GET_ALL_VOLUNTARY);
            for (final Map<String, Object> voluntary : queryData) {
                voluntaries.add(new VoluntaryData((String) voluntary.get("codice_fiscale"),
                        (String) voluntary.get("fk_account"), (String) voluntary.get("nome"),
                        (String) voluntary.get("cognome")));
            }
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't fetch all the voluntaries", err);
            DB_CONNECTION.closeConnection();
        }
        return voluntaries;
    }

    /**
     * Changes the group of an educator.
     * @param educatorData The educator to change the group of.
     * @param groupName The group to change it to.
     */
    public static void changeEducatorGroup(final EducatorData educatorData, final String groupName) {
        try {
            DB_CONNECTION.openConnection();
            DB_CONNECTION.setQueryData(UPDATE_EDUCATOR_GROUP, groupName, educatorData.codFisc());
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't update the group of " + educatorData.name() + " "
                    + educatorData.surname(), err);
            DB_CONNECTION.closeConnection();
        }
    }

    /**
     * Creates a new educator within the database.
     * @param educatorData The educator to add to the database.
     */
    public static void createEducator(final EducatorData educatorData) {
        try {
            DB_CONNECTION.openConnection();
            DB_CONNECTION.setQueryData(CREATE_EDUCATOR, educatorData.codFisc(), educatorData.name(),
                    educatorData.surname(), educatorData.phoneNumber(), educatorData.accountEmail());
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't create the educator" + educatorData.codFisc(), err);
            DB_CONNECTION.closeConnection();
        }
    }

    /**
     * Creates a new voluntary within the database.
     * @param voluntaryData The voluntary to add to the database.
     */
    public static void createVoluntary(final VoluntaryData voluntaryData) {
        try {
            DB_CONNECTION.openConnection();
            DB_CONNECTION.setQueryData(CREATE_VOLUNTARY, voluntaryData.codFisc(), voluntaryData.accountEmail(),
                    voluntaryData.name(), voluntaryData.surname());
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't create the voluntary" + voluntaryData.codFisc(), err);
            DB_CONNECTION.closeConnection();
        }
    }
}
