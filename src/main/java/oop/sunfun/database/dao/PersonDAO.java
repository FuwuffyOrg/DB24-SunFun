package oop.sunfun.database.dao;

import oop.sunfun.database.data.enums.ParentType;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class PersonDAO extends AbstractDAO {
    private static final Logger LOGGER = Logger.getLogger(PersonDAO.class.getName());

    private static final String CREATE_PARENTE = "INSERT INTO `parente`(`codice_fiscale`, `fk_account`, "
            + "`nome`, `cognome`, `cellulare`, `grado_di_parentela`) VALUES (?,?,?,?,?,?)";

    private PersonDAO() {
        super();
    }

    public static void createParent(final String codiceFiscale, final String accountEmail, final String name,
                                    final String surname, final String phoneNumber, final ParentType parentType) {
        try {
            DB_CONNECTION.openConnection();
            DB_CONNECTION.setQueryData(CREATE_PARENTE, codiceFiscale, accountEmail, name,
                    surname, phoneNumber, parentType.getTextValue());
        } catch (final SQLException err) {
            LOGGER.log(Level.SEVERE, "Couldn't create the new parent", err);
            DB_CONNECTION.closeConnection();
        }
    }
}
