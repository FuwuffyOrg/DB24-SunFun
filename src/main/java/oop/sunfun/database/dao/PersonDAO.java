package oop.sunfun.database.dao;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersonDAO extends AbstractDAO {
    private static final Logger LOGGER = Logger.getLogger(PersonDAO.class.getName());

    private static final String CREATE_PARENTE = "INSERT INTO `parente`(`codice_fiscale`, `fk_account`, "
            + "`nome`, `cognome`, `cellulare`, `grado_di_parentela`) VALUES (?,?,?,?,?,?)";

    private PersonDAO() {
        // Useless constructor
    }

    public static void createParent(final String codiceFiscale, final String accountEmail, final String name,
                                    final String surname, final String phoneNumber, final String parentType) {
        try {
            DB_CONNECTION.openConnection();
            DB_CONNECTION.setQueryData(CREATE_PARENTE, codiceFiscale, accountEmail, name,
                    surname, phoneNumber, parentType);
        } catch (final SQLException err) {
            LOGGER.log(Level.SEVERE, "Couldn't fetch the categories", err);
            DB_CONNECTION.closeConnection();
        }
    }
}
