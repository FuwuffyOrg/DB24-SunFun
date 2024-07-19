package oop.sunfun.database.dao;

import oop.sunfun.database.data.person.ParticipantData;
import oop.sunfun.ui.util.Pair;

import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ParticipantDAO extends AbstractDAO {
    private static final Logger LOGGER = Logger.getLogger(GroupDAO.class.getName());

    private static final String GET_ALL_PARTICIPANTS = "SELECT * FROM `partecipante`";

    private static final String UPDATE_PARTICIPANT_GROUP = "UPDATE `partecipante` SET `fk_gruppo`=? WHERE "
            + "`codice_fiscale`=?";

    private static final String CHECK_PRESENCE = "SELECT * FROM `presenza` `p` JOIN `partecipante` `pa` ON "
            + "`p`.`fk_partecipante`=? WHERE `p`.`fk_giornata`=?";

    private static final String GET_ALL_ENROLLED_DATES_FROM_PARTICIPANT = "SELECT * FROM `giornata` `g` JOIN `modalita` "
            + "`m` ON `g`.`fk_periodo_inizio` = `m`.`fk_data_inizio` AND `g`.`fk_periodo_fine` = `m`.`fk_data_fine` "
            + "WHERE `m`.`fk_partecipante`=?";

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

    public static void updateParticipantGroup(final String participantCodFisc, final String groupName) {
        try {
            DB_CONNECTION.openConnection();
            DB_CONNECTION.setQueryData(UPDATE_PARTICIPANT_GROUP, groupName, participantCodFisc);
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't update the group for " + participantCodFisc, err);
            DB_CONNECTION.closeConnection();
        }
    }

    public static Set<Date> getAllEnrolledDates(final String participantCodFisc) {
        final Set<Date> participants = new HashSet<>();
        try {
            DB_CONNECTION.openConnection();
            final List<Map<String, Object>> queryData = DB_CONNECTION.getQueryData(
                    GET_ALL_ENROLLED_DATES_FROM_PARTICIPANT, participantCodFisc);
            for (final Map<String, Object> date : queryData) {
                participants.add((Date) date.get("data"));
            }
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't fetch the participants", err);
            DB_CONNECTION.closeConnection();
        }
        return participants;
    }

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
            bracedLog(LOGGER, Level.SEVERE, "Couldn't fetch the participants", err);
            DB_CONNECTION.closeConnection();
        }
        return Optional.empty();
    }
}
