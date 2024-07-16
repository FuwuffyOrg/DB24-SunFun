package oop.sunfun.database.dao;

import oop.sunfun.database.data.person.EducatorData;
import oop.sunfun.database.data.person.VoluntaryData;

import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class EducatorDAO extends AbstractDAO {
    private static final Logger LOGGER = Logger.getLogger(EducatorDAO.class.getName());

    private static final String GET_ALL_EDUCATORS = "SELECT `e`.`codice_fiscale`, `e`.`nome`, `e`.`cognome`, "
            + "`e`.`cellulare`, `e`.`fk_account` FROM `educatore` `e`";

    private static final String GET_ALL_VOLUNTARY = "SELECT `v`.`codice_fiscale`, `v`.`nome`, `v`.`cognome`, "
            + "`v`.`fk_account` FROM `volontario` `v`";

    private static final String UPDATE_EDUCATOR_GROUP = "UPDATE `educatore` SET `fk_gruppo`=? WHERE "
            + "`educatore`.`codice_fiscale`=?";

    public static Set<EducatorData> getAllEducators() {
        final Set<EducatorData> educators = new HashSet<>();
        try {
            DB_CONNECTION.openConnection();
            final List<Map<String, Object>> queryData = DB_CONNECTION.getQueryData(GET_ALL_EDUCATORS);
            for (final Map<String, Object> allergen : queryData) {
                educators.add(new EducatorData((String) allergen.get("codice_fiscale"),
                        (String) allergen.get("nome"), (String) allergen.get("cognome"),
                        (String) allergen.get("fk_account"), (String) allergen.get("cellulare"),
                        Optional.ofNullable((String) allergen.get("fk_gruppo"))));
            }
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't fetch all the educators", err);
            DB_CONNECTION.closeConnection();
        }
        return educators;
    }

    public static Set<VoluntaryData> getAllVoluntary() {
        final Set<VoluntaryData> voluntaries = new HashSet<>();
        try {
            DB_CONNECTION.openConnection();
            final List<Map<String, Object>> queryData = DB_CONNECTION.getQueryData(GET_ALL_VOLUNTARY);
            for (final Map<String, Object> allergen : queryData) {
                voluntaries.add(new VoluntaryData((String) allergen.get("codice_fiscale"),
                        (String) allergen.get("fk_account"), (String) allergen.get("nome"),
                        (String) allergen.get("cognome")));
            }
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't fetch all the voluntaries", err);
            DB_CONNECTION.closeConnection();
        }
        return voluntaries;
    }

    public static void changeEducatorGroup(final EducatorData educatorData, final String groupName) {
        try {
            DB_CONNECTION.openConnection();
            DB_CONNECTION.setQueryData(UPDATE_EDUCATOR_GROUP, groupName, educatorData.codiceFiscale());
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't update the group of " + educatorData.name() + " "
                    + educatorData.surname(), err);
            DB_CONNECTION.closeConnection();
        }
    }

    public static void createEducator(final EducatorData educatorData) {

    }

    public static void createVoluntary(final VoluntaryData voluntaryData) {

    }
}
