package oop.sunfun.database.dao;

import oop.sunfun.database.data.admin.GroupData;
import oop.sunfun.database.data.admin.MembershipType;
import oop.sunfun.database.data.admin.PeriodData;
import oop.sunfun.database.data.person.ParticipantData;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class GroupDAO extends AbstractDAO {
    private static final Logger LOGGER = Logger.getLogger(GroupDAO.class.getName());

    private static final String GET_ALL_GROUPS = "SELECT * FROM `gruppo`";

    private static final String GET_PARTICIPANTS_IN_GROUP = "SELECT `a`.`email` FROM `partecipante` `p` WHERE "
            + "`p`.`fk_gruppo`=?";

    private static final String GET_PARTICIPANT_GROUP = "SELECT `fk_gruppo` FROM `partecipante` WHERE "
            + "`codice_fiscale`=?";

    private static final String GET_EDUCATOR_GROUP = "SELECT `fk_gruppo` FROM `educatore` WHERE `codice_fiscale`=?";

    private static final String CREATE_GROUP = "INSERT INTO `gruppo`(`nome`, `eta_min`, `eta_max`) VALUES (?,?,?)";

    private static final String ERASE_GROUP = "DELETE FROM `gruppo` WHERE `nome`=?";

    private static final String ERASE_PRESENCE = "DELETE FROM `presenza` WHERE `fk_partecipante`=? AND "
            + "`fk_giornata`=?";

    private static final String ADD_OR_UPDATE_PRESENCE = "INSERT INTO `presenza`(`entrata`, `uscita`, "
            + "`fk_partecipante`, `fk_giornata`) VALUES (?,?,?,?) ON DUPLICATE KEY UPDATE `entrata` = "
            + "VALUES(`entrata`), `uscita` = VALUES(`uscita`);";

    public static Set<GroupData> getAllGroups() {
        final Set<GroupData> categories = new HashSet<>();
        try {
            DB_CONNECTION.openConnection();
            final List<Map<String, Object>> queryData = DB_CONNECTION.getQueryData(GET_ALL_GROUPS);
            for (final Map<String, Object> category : queryData) {
                categories.add(new GroupData((String) category.get("nome"), (Integer) category.get("eta_min"),
                        (Integer) category.get("eta_max")));
            }
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't fetch the groups", err);
            DB_CONNECTION.closeConnection();
        }
        return categories;
    }

    public static Set<ParticipantData> getParticipantsFromGroup(final String groupName) {
        final Set<ParticipantData> participants = new HashSet<>();
        try {
            DB_CONNECTION.openConnection();
            final List<Map<String, Object>> queryData = DB_CONNECTION.getQueryData(GET_PARTICIPANTS_IN_GROUP,
                    groupName);
            for (final Map<String, Object> participant : queryData) {
                final String codiceFiscale = (String) participant.get("codice_fiscale");
                final String dieta = (String) participant.get("fk_dieta");
                final String gruppo = (String) participant.get("fk_gruppo");
                final String name = (String) participant.get("nome");
                final String surname = (String) participant.get("surname");
                final String email = (String) participant.get("fk_account");
                final Date dateOfBirth = (Date) participant.get("data_di_nascita");
                participants.add(new ParticipantData(codiceFiscale, email, Optional.ofNullable(dieta),
                        Optional.of(gruppo), name, surname, dateOfBirth));
            }
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't fetch the participants in group " + groupName, err);
            DB_CONNECTION.closeConnection();
        }
        return participants;
    }

    public static void createGroup(final GroupData groupData) {
        try {
            DB_CONNECTION.openConnection();
            DB_CONNECTION.setQueryData(CREATE_GROUP, groupData.name(), groupData.minAge(), groupData.maxAge());
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't create the new group " + groupData.name(), err);
            DB_CONNECTION.closeConnection();
        }
    }

    public static Optional<String> getParticipantGroup(final String participantCodFisc) {
        try {
            DB_CONNECTION.openConnection();
            final List<Map<String, Object>> queryData = DB_CONNECTION.getQueryData(GET_PARTICIPANT_GROUP,
                    participantCodFisc);
            if (queryData.size() > 1) {
                bracedLog(LOGGER, Level.WARNING, "There's two participants with the same codFisc");
            } else if (queryData.isEmpty()) {
                return Optional.empty();
            }
            return Optional.ofNullable((String) queryData.getFirst().get("fk_gruppo"));
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't get the group for " + participantCodFisc, err);
            DB_CONNECTION.closeConnection();
        }
        return Optional.empty();
    }

    public static Optional<String> getEducatorGroup(final String educatorCodFisc) {
        try {
            DB_CONNECTION.openConnection();
            final List<Map<String, Object>> queryData = DB_CONNECTION.getQueryData(GET_EDUCATOR_GROUP,
                    educatorCodFisc);
            if (queryData.size() > 1) {
                bracedLog(LOGGER, Level.WARNING, "There's two educators with the same codFisc");
            } else if (queryData.isEmpty()) {
                return Optional.empty();
            }
            return Optional.ofNullable((String) queryData.getFirst().get("fk_gruppo"));
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't get the group for " + educatorCodFisc, err);
            DB_CONNECTION.closeConnection();
        }
        return Optional.empty();
    }

    public static void deleteGroup(final String groupName) {
        try {
            DB_CONNECTION.openConnection();
            DB_CONNECTION.setQueryData(ERASE_GROUP, groupName);
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't erase the group " + groupName, err);
            DB_CONNECTION.closeConnection();
        }
    }

    public static void addOrUpdatePresence(final String participantCodFisc, final Date date,
                                           final boolean entry, final boolean exit) {
        try {
            DB_CONNECTION.openConnection();
            if (!entry && !exit) {
                DB_CONNECTION.setQueryData(ERASE_PRESENCE, participantCodFisc, date);
            } else {
                DB_CONNECTION.setQueryData(ADD_OR_UPDATE_PRESENCE, entry, exit, participantCodFisc, date);
            }
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't add or update the presence "
                    + participantCodFisc + " at " + date, err);
            DB_CONNECTION.closeConnection();
        }
    }
}
