package oop.sunfun.database.dao;

import oop.sunfun.database.data.person.ParticipantData;
import oop.sunfun.ui.util.Pair;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ParticipantDAO extends AbstractDAO {
    /**
     * Logger to help diagnose sql and database errors.
     */
    private static final Logger LOGGER = Logger.getLogger(GroupDAO.class.getName());

    /**
     * Query to fetch all the participants.
     */
    private static final String GET_ALL_PARTICIPANTS = "SELECT * FROM `partecipante`;";

    /**
     * Query to update the participant's group.
     */
    private static final String UPDATE_PARTICIPANT_GROUP = "UPDATE `partecipante` SET `fk_gruppo`=? WHERE "
            + "`codice_fiscale`=?;";

    /**
     * Query to check whether a participant was present during a date.
     */
    private static final String CHECK_PRESENCE = "SELECT * FROM `presenza` `p` JOIN `partecipante` `pa` ON "
            + "`p`.`fk_partecipante`=? WHERE `p`.`fk_giornata`=?;";

    /**
     * Query to get all dates that a present is enrolled to.
     */
    private static final String GET_ALL_ENROLLED_DATES_FROM_PARTICIPANT = "SELECT * FROM `giornata` `g` JOIN "
            + "`modalita` `m` ON `g`.`fk_periodo_inizio` = `m`.`fk_data_inizio` AND `g`.`fk_periodo_fine` = "
            + "`m`.`fk_data_fine` WHERE `m`.`fk_partecipante`=?;";

    /**
     * Method to fetch all the participants within the database.
     * @return The participants of the database.
     */
    public static Set<ParticipantData> getAllParticipants() {
        final Set<ParticipantData> participants = new HashSet<>();
        try {
            DB_CONNECTION.openConnection();
            final List<Map<String, Object>> queryData = DB_CONNECTION.getQueryData(GET_ALL_PARTICIPANTS);
            for (final Map<String, Object> participant : queryData) {
                final String codiceFiscale = (String) participant.get("codice_fiscale");
                final String dieta = (String) participant.get("fk_dieta");
                final String gruppo = (String) participant.get("fk_gruppo");
                final String name = (String) participant.get("nome");
                final String surname = (String) participant.get("cognome");
                final String email = (String) participant.get("fk_account");
                final Date dateOfBirth = (Date) participant.get("data_di_nascita");
                participants.add(new ParticipantData(codiceFiscale, email, Optional.ofNullable(dieta),
                        Optional.ofNullable(gruppo), name, surname, dateOfBirth));
            }
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't fetch the participants", err);
            DB_CONNECTION.closeConnection();
        }
        return participants;
    }

    /**
     * Updates the group of a given participant.
     * @param participantCodFisc The code of the participant.
     * @param groupName The name of the group to set it to.
     */
    public static void updateParticipantGroup(final String participantCodFisc, final String groupName) {
        try {
            DB_CONNECTION.openConnection();
            DB_CONNECTION.setQueryData(UPDATE_PARTICIPANT_GROUP, groupName, participantCodFisc);
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't update the group for " + participantCodFisc, err);
            DB_CONNECTION.closeConnection();
        }
    }

    /**
     * Method to fetch all the dates a participant is enrolled to from the database.
     * @param participantCodFisc The code of the participant.
     * @return All the dates the participant is enrolled to.
     */
    public static Set<Date> getAllEnrolledDates(final String participantCodFisc) {
        final Set<Date> dates = new HashSet<>();
        try {
            DB_CONNECTION.openConnection();
            final List<Map<String, Object>> queryData = DB_CONNECTION.getQueryData(
                    GET_ALL_ENROLLED_DATES_FROM_PARTICIPANT, participantCodFisc);
            for (final Map<String, Object> date : queryData) {
                dates.add((Date) date.get("data"));
            }
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't fetch the enrolled dates of " + participantCodFisc, err);
            DB_CONNECTION.closeConnection();
        }
        return dates;
    }

    /**
     * Method to check if a participant was present at a given date from the database.
     * @param participantCodFisc The code of the participant.
     * @param date The date to check the presence at.
     * @return A valid optional if he either entered or left the camp that day.
     */
    public static Optional<Pair<Boolean, Boolean>> checkPresence(final String participantCodFisc, final Date date) {
        try {
            DB_CONNECTION.openConnection();
            final List<Map<String, Object>> queryData = DB_CONNECTION.getQueryData(
                    CHECK_PRESENCE, participantCodFisc, date);
            if (queryData.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(new Pair<>((Boolean) queryData.getFirst().get("entrata"),
                    (Boolean) queryData.getFirst().get("uscita")));
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't check the presence of " + participantCodFisc
                    + " during " + date, err);
            DB_CONNECTION.closeConnection();
        }
        return Optional.empty();
    }
}
