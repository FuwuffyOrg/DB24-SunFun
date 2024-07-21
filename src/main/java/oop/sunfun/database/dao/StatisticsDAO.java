package oop.sunfun.database.dao;

import oop.sunfun.database.data.food.AllergenData;
import oop.sunfun.database.data.food.DietData;
import oop.sunfun.ui.util.Pair;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StatisticsDAO extends AbstractDAO {
    /**
     * Logger to help diagnose sql and database errors.
     */
    private static final Logger LOGGER = Logger.getLogger(StatisticsDAO.class.getName());

    /**
     * Query to fetch the three most popular forum posts (by count of responses).
     */
    private static final String GET_MOST_POPULAR_FORUM_POSTS = "SELECT `d`.`titolo`, COUNT(`r`.`num_risposta`) AS "
            + "`num_risposte` FROM `discussione` `d` JOIN `risposta` `r` ON `d`.`num_discussione` = "
            + "`r`.`fk_discussione` GROUP BY `d`.`num_discussione`, `d`.`titolo` ORDER BY `num_risposta` DESC LIMIT 3;";

    /**
     * Query to fetch the top three highest intolerance/allergenic substances within the database.
     */
    private static final String GET_TOP_THREE_ALLERGENS_BY_ALLERGY_RATE = "SELECT `a`.`nome`, `a`.`descrizione`, "
            + "COUNT(`i`.`fk_allergene`) AS `intolleranze_count` FROM `intolleranza` `i` JOIN `allergene` `a` ON "
            + "`i`.`fk_allergene` = `a`.`nome` GROUP BY `a`.`nome` ORDER BY `intolleranze_count` DESC LIMIT 3;";

    /**
     * Query to fetch the diets in order by most followed to least followed.
     */
    private static final String GET_MOST_FOLLOWED_DIETS = "SELECT `d`.`nome`, `d`.`descrizione`, "
            + "COUNT(`p`.`codice_fiscale`) AS `numero_partecipanti` FROM `partecipante` `p` JOIN `dieta` `d` ON "
            + "`p`.`fk_dieta` = `d`.`nome` GROUP BY `d`.`nome` ORDER BY `numero_partecipanti` DESC;";

    /**
     * Query to fetch the activity with the longest duration within the database.
     */
    private static final String GET_LONGEST_ACTIVITY = "SELECT `a`.`nome`, MAX(TIMESTAMPDIFF(MINUTE, "
            + "`s`.`ora_inizio`, `s`.`ora_fine`)) AS `durata_minuti` FROM `svolgimento` `s` JOIN `attivita` `a` "
            + "ON `s`.`fk_attivita` = `a`.`nome` GROUP BY `a`.`nome` ORDER BY `durata_minuti` DESC LIMIT 1;";

    /**
     * Fetches the top three most popular posts.
     * @return A set containing a pair of type: Title, NumberOfResponses
     */
    public static Set<Pair<String, Integer>> getMostPopularForumPosts() {
        final Set<Pair<String, Integer>> discussions = new HashSet<>();
        try {
            DB_CONNECTION.openConnection();
            final List<Map<String, Object>> queryData = DB_CONNECTION.getQueryData(GET_MOST_POPULAR_FORUM_POSTS);
            for (final Map<String, Object> discussion : queryData) {
                discussions.add(new Pair<>((String) discussion.get("titolo"),
                        (Integer) discussion.get("num_risposte")));
            }
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't fetch the most popular discussions", err);
            DB_CONNECTION.closeConnection();
        }
        return discussions;
    }

    /**
     * Fetches the top three most allergenic allergens.
     * @return A set containing a pair of type: Allergen, NumberOfAffectedPeople
     */
    public static Set<Pair<AllergenData, Integer>> getMostAllergenicAllergens() {
        final Set<Pair<AllergenData, Integer>> allergens = new HashSet<>();
        try {
            DB_CONNECTION.openConnection();
            final List<Map<String, Object>> queryData =
                    DB_CONNECTION.getQueryData(GET_TOP_THREE_ALLERGENS_BY_ALLERGY_RATE);
            for (final Map<String, Object> allergen : queryData) {
                allergens.add(new Pair<>(
                        new AllergenData((String) allergen.get("nome"), (String) allergen.get("dscrizione")),
                        (Integer) allergen.get("intolleranze_count"))
                );
            }
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't fetch the most allergenic allergens", err);
            DB_CONNECTION.closeConnection();
        }
        return allergens;
    }

    /**
     * Fetches all the diets in order of most followed and least followed.
     * @return A set containing a pair of type: Diet, NumberOfUsers
     */
    public static Set<Pair<DietData, Integer>> getMostFollowedDiets() {
        final Set<Pair<DietData, Integer>> diets = new HashSet<>();
        try {
            DB_CONNECTION.openConnection();
            final List<Map<String, Object>> queryData = DB_CONNECTION.getQueryData(GET_MOST_FOLLOWED_DIETS);
            for (final Map<String, Object> diet : queryData) {
                diets.add(new Pair<>(
                        new DietData((String) diet.get("nome"), (String) diet.get("dscrizione")),
                        (Integer) diet.get("numero_partecipanti"))
                );
            }
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't fetch the most followed diets", err);
            DB_CONNECTION.closeConnection();
        }
        return diets;
    }

    /**
     * Fetches the activity with the longest duration within the database.
     * @return A valid optional if the longest activity was found, with a pair like: Name, Duration.
     */
    public static Optional<Pair<String, Long>> getLongestActivity() {
        try {
            DB_CONNECTION.openConnection();
            final List<Map<String, Object>> queryData = DB_CONNECTION.getQueryData(GET_LONGEST_ACTIVITY);
            if (queryData.isEmpty()) {
                bracedLog(LOGGER, Level.SEVERE, "Couldn't fetch the longest activity");
                return Optional.empty();
            }
            final Map<String, Object> activity = queryData.getFirst();
            return Optional.of(new Pair<>((String) activity.get("nome"), (Long) activity.get("durata_minuti")));
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't fetch the longest activity", err);
            DB_CONNECTION.closeConnection();
        }
        return Optional.empty();
    }
}
