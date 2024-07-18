package oop.sunfun.database.dao;

import oop.sunfun.database.data.admin.GroupData;
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

    private static final String CREATE_GROUP = "INSERT INTO `gruppo`(`nome`, `eta_min`, `eta_max`) VALUES (?,?,?)";

    private static final String ERASE_GROUP = "DELETE FROM `gruppo` WHERE `nome`=?";

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

    public static void deleteGroup(final String groupName) {
        try {
            DB_CONNECTION.openConnection();
            DB_CONNECTION.setQueryData(ERASE_GROUP, groupName);
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't erase the group " + groupName, err);
            DB_CONNECTION.closeConnection();
        }
    }
}
