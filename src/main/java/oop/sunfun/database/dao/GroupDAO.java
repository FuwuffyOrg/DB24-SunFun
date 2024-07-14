package oop.sunfun.database.dao;

import oop.sunfun.database.data.admin.GroupData;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class GroupDAO extends AbstractDAO {
    private static final Logger LOGGER = Logger.getLogger(GroupDAO.class.getName());

    private static final String GET_ALL_GROUPS = "SELECT * FROM `gruppo`";

    private static final String CREATE_GROUP = "INSERT INTO `gruppo`(`nome`, `eta_min`, `eta_max`) VALUES (?,?,?)";

    private static final String ERASE_GROUP = "DELETE FROM `gruppo` WHERE `nome`=?";


    private GroupDAO() {
        // Useless constructor
    }

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
            LOGGER.log(Level.SEVERE, "Couldn't fetch the groups", err);
            DB_CONNECTION.closeConnection();
        }
        return categories;
    }

    public static void createGroup(final GroupData groupData) {
        try {
            DB_CONNECTION.openConnection();
            DB_CONNECTION.setQueryData(CREATE_GROUP, groupData.name(), groupData.minAge(), groupData.maxAge());
        } catch (final SQLException err) {
            LOGGER.log(Level.SEVERE, "Couldn't create the new group " + groupData.name(), err);
            DB_CONNECTION.closeConnection();
        }
    }

    public static void deleteGroup(final String groupName) {
        try {
            DB_CONNECTION.openConnection();
            DB_CONNECTION.setQueryData(ERASE_GROUP, groupName);
        } catch (final SQLException err) {
            LOGGER.log(Level.SEVERE, "Couldn't erase the group " + groupName, err);
            DB_CONNECTION.closeConnection();
        }
    }
}
