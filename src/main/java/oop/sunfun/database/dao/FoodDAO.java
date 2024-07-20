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
    /**
     * Logger to help diagnose sql and database errors.
     */
    private static final Logger LOGGER = Logger.getLogger(FoodDAO.class.getName());

    /**
     * Query to get all the allergens in the database.
     */
    private static final String GET_ALL_ALLERGENS = "SELECT * FROM `allergene`";

    /**
     * Query to get all the allergens a participant is susceptible to.
     */
    private static final String GET_ALL_ALLERGENS_OF_PARTICIPANT = "SELECT * FROM allergene a JOIN intolleranza i ON "
            + "i.fk_allergene=a.nome WHERE i.fk_partecipante=?";

    /**
     * Query to get all the diets in the database.
     */
    private static final String GET_ALL_DIETS = "SELECT * FROM `dieta`";

    /**
     * Query to get all the diets a participant follows.
     */
    private static final String CREATE_INTOLLERANCE_FOR_PARTICIPANT = "INSERT INTO `intolleranza`(`fk_partecipante`, "
            + "`fk_allergene`) VALUES (?,?)";

    /**
     * Query to create and add a new allergen to the database.
     */
    private static final String CREATE_ALLERGEN = "INSERT INTO `allergene`(`nome`, `descrizione`) VALUES (?,?)";

    /**
     * Query to create and add a new diet to the database.
     */
    private static final String CREATE_DIET = "INSERT INTO `dieta`(`nome`, `descrizione`) VALUES (?,?)";

    /**
     * Query to create and delete an allergen from the database.
     */
    private static final String DELETE_ALLERGEN = "DELETE FROM `allergene` WHERE `nome`=?";

    /**
     * Query to remove a participant's allergic relationship to the allergen from the database.
     */
    private static final String DELETE_ALLERGEN_FROM_PARTICIPANT = "DELETE FROM `intolleranza` WHERE "
            + "`intolleranza`.`fk_partecipante`=? AND `intolleranza`.`fk_allergene`=?";

    /**
     * Query to create and delete a diet from the database.
     */
    private static final String DELETE_DIET = "DELETE FROM `dieta` WHERE `nome`=?";

    /**
     * Gets all the allergens from the database.
     * @return The allergens within the database
     */
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

    /**
     * Gets all the allergens of a participant from the database.
     * @param participantCodFisc The participant's code.
     * @return The allergens of a participant within the database.
     */
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

    /**
     * Gets all the diets from the database.
     * @return The diets within the database
     */
    public static Set<DietData> getAllDiets() {
        final Set<DietData> diets = new HashSet<>();
        try {
            DB_CONNECTION.openConnection();
            final List<Map<String, Object>> queryData = DB_CONNECTION.getQueryData(GET_ALL_DIETS);
            for (final Map<String, Object> diet : queryData) {
                diets.add(new DietData((String) diet.get("nome"), (String) diet.get("descrizione")));
            }
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't fetch all the diets", err);
            DB_CONNECTION.closeConnection();
        }
        return diets;
    }

    /**
     * Creates a new allergen in the database.
     * @param allergen The allergen to add to the database.
     */
    public static void createNewAllergen(final AllergenData allergen) {
        try {
            DB_CONNECTION.openConnection();
            DB_CONNECTION.setQueryData(CREATE_ALLERGEN, allergen.name(), allergen.description());
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't create a new allergen " + allergen.name(), err);
            DB_CONNECTION.closeConnection();
        }
    }

    /**
     * Adds an intollerance to the participant with the given allergen.
     * @param allergenName The allergen the participant is intollerant to.
     * @param participantCodFisc The participant's code.
     */
    public static void createIntolleranceForParticipant(final String allergenName, final String participantCodFisc) {
        try {
            DB_CONNECTION.openConnection();
            DB_CONNECTION.setQueryData(CREATE_INTOLLERANCE_FOR_PARTICIPANT, participantCodFisc, allergenName);
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't add the intollerance " + allergenName + " for "
                    + participantCodFisc, err);
            DB_CONNECTION.closeConnection();
        }
    }

    /**
     * Creates a new diet in the database.
     * @param dietData The diet to add to the database.
     */
    public static void createNewDiet(final DietData dietData) {
        try {
            DB_CONNECTION.openConnection();
            DB_CONNECTION.setQueryData(CREATE_DIET, dietData.name(), dietData.description());
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't create a new diet " + dietData.name(), err);
            DB_CONNECTION.closeConnection();
        }
    }

    /**
     * Deletes an allergen from the database.
     * @param allergen the allergen to delete.
     */
    public static void deleteAllergen(final AllergenData allergen) {
        try {
            DB_CONNECTION.openConnection();
            DB_CONNECTION.setQueryData(DELETE_ALLERGEN, allergen.name());
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't delete the allergen " + allergen.name(), err);
            DB_CONNECTION.closeConnection();
        }
    }

    /**
     * Removes an intollerance from the participant with the given allergen.
     * @param allergenName The allergen the participant is no longer intollerant to.
     * @param participantCodFisc The participant's code.
     */
    public static void deleteIntolleranceFromParticipant(final String allergenName, final String participantCodFisc) {
        try {
            DB_CONNECTION.openConnection();
            DB_CONNECTION.setQueryData(DELETE_ALLERGEN_FROM_PARTICIPANT, participantCodFisc, allergenName);
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't delete the allergen " + allergenName + " for "
                    + participantCodFisc, err);
            DB_CONNECTION.closeConnection();
        }
    }

    /**
     * Deletes a diet from the database.
     * @param dietData the diet to delete.
     */
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
