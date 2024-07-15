package oop.sunfun.database.dao;

import oop.sunfun.database.data.food.DietData;
import oop.sunfun.database.data.food.AllergenData;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class FoodDAO extends AbstractDAO {

    private static final Logger LOGGER = Logger.getLogger(FoodDAO.class.getName());

    private static final String GET_ALL_ALLERGENS = "SELECT * FROM `allergene`";

    private static final String GET_ALL_ALLERGENS_OF_PARTICIPANT = "SELECT * FROM allergene a JOIN intolleranza i ON "
            + "i.fk_allergene=a.nome WHERE i.fk_partecipante=?";

    private static final String GET_ALL_DIETS = "SELECT * FROM `dieta`";

    private static final String CREATE_INTOLLERANCE_FOR_PARTICIPANT = "INSERT INTO `intolleranza`(`fk_partecipante`, "
            + "`fk_allergene`) VALUES (?,?)";

    private static final String CREATE_ALLERGEN = "INSERT INTO `allergene`(`nome`, `descrizione`) VALUES (?,?)";

    private static final String CREATE_DIET = "INSERT INTO `dieta`(`nome`, `descrizione`) VALUES (?,?)";

    private static final String DELETE_ALLERGEN = "DELETE FROM `allergene` WHERE `nome`=?";

    private static final String DELETE_ALLERGEN_FROM_PARTICIPANT = "DELETE FROM `intolleranza` WHERE "
            + "`intolleranza`.`fk_partecipante`=? AND `intolleranza`.`fk_allergene`=?";

    private static final String DELETE_DIET = "DELETE FROM `dieta` WHERE `nome`=?";

    public static Set<AllergenData> getAllAllergens() {
        final Set<AllergenData> allergens = new HashSet<>();
        try {
            DB_CONNECTION.openConnection();
            final List<Map<String, Object>> queryData = DB_CONNECTION.getQueryData(GET_ALL_ALLERGENS);
            for (final Map<String, Object> allergen : queryData) {
                allergens.add(new AllergenData((String) allergen.get("nome"), (String) allergen.get("descrizione")));
            }
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't fetch all the allergens", err);
            DB_CONNECTION.closeConnection();
        }
        return allergens;
    }

    public static Set<AllergenData> getAllAllergensOfParticipant(final String participantCodFisc) {
        final Set<AllergenData> allergens = new HashSet<>();
        try {
            DB_CONNECTION.openConnection();
            final List<Map<String, Object>> queryData = DB_CONNECTION.getQueryData(GET_ALL_ALLERGENS_OF_PARTICIPANT,
                    participantCodFisc);
            for (final Map<String, Object> allergen : queryData) {
                allergens.add(new AllergenData((String) allergen.get("nome"), (String) allergen.get("descrizione")));
            }
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't fetch all the allergens for " + participantCodFisc, err);
            DB_CONNECTION.closeConnection();
        }
        return allergens;
    }

    public static Set<DietData> getAllDiets() {
        final Set<DietData> diets = new HashSet<>();
        try {
            DB_CONNECTION.openConnection();
            final List<Map<String, Object>> queryData = DB_CONNECTION.getQueryData(GET_ALL_DIETS);
            for (final Map<String, Object> diet : queryData) {
                diets.add(new DietData((String) diet.get("nome"), (String) diet.get("descrizione")));
            }
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't fetch all the diet types", err);
            DB_CONNECTION.closeConnection();
        }
        return diets;
    }

    public static void createNewAllergen(final AllergenData allergen) {
        try {
            DB_CONNECTION.openConnection();
            DB_CONNECTION.setQueryData(CREATE_ALLERGEN, allergen.name(), allergen.description());
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't create a new allergen " + allergen.name(), err);
            DB_CONNECTION.closeConnection();
        }
    }

    public static void createAllergenForParticipant(final String allergenName, final String participantCodFisc) {
        try {
            DB_CONNECTION.openConnection();
            DB_CONNECTION.setQueryData(CREATE_INTOLLERANCE_FOR_PARTICIPANT, participantCodFisc, allergenName);
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't add the intollerance " + allergenName + " for "
                    + participantCodFisc, err);
            DB_CONNECTION.closeConnection();
        }
    }

    public static void createNewDiet(final DietData dietData) {
        try {
            DB_CONNECTION.openConnection();
            DB_CONNECTION.setQueryData(CREATE_DIET, dietData.name(), dietData.description());
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't create a new diet " + dietData.name(), err);
            DB_CONNECTION.closeConnection();
        }
    }

    public static void deleteAllergen(final AllergenData allergen) {
        try {
            DB_CONNECTION.openConnection();
            DB_CONNECTION.setQueryData(DELETE_ALLERGEN, allergen.name());
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't delete the allergen " + allergen.name(), err);
            DB_CONNECTION.closeConnection();
        }
    }

    public static void deleteAllergenFromParticipant(final String allergenName, final String participantCodFisc) {
        try {
            DB_CONNECTION.openConnection();
            DB_CONNECTION.setQueryData(DELETE_ALLERGEN_FROM_PARTICIPANT, participantCodFisc, allergenName);
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't delete the allergen " + allergenName + " for "
                    + participantCodFisc, err);
            DB_CONNECTION.closeConnection();
        }
    }

    public static void deleteDiet(final DietData dietData) {
        try {
            DB_CONNECTION.openConnection();
            DB_CONNECTION.setQueryData(DELETE_DIET, dietData.name());
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't delete the diet " + dietData.name(), err);
            DB_CONNECTION.closeConnection();
        }
    }
}
