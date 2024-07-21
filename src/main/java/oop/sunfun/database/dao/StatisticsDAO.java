package oop.sunfun.database.dao;

import java.util.logging.Logger;

public class StatisticsDAO extends AbstractDAO {
    /**
     * Logger to help diagnose sql and database errors.
     */
    private static final Logger LOGGER = Logger.getLogger(StatisticsDAO.class.getName());

    /**
     * Query to fetch the three most popular forum posts (by count of responses).
     */
    private static final String GET_MOST_POPULAR_FORUM_POSTS = "SELECT `d`.`num_discussione`, `d`.`titolo`, "
            + "COUNT(`r`.`num_risposta`) AS `num_risposte` FROM `discussione` `d` JOIN `risposta` `r` ON "
            + "`d`.`num_discussione` = `r`.`fk_discussione` GROUP BY `d`.`num_discussione`, `d`.`titolo` ORDER "
            + "BY `num_risposta` DESC LIMIT 3;";

    /**
     * Query to fetch the top three highest intolerance/allergenic substances within the database.
     */
    private static final String GET_TOP_THREE_ALLERGENS_BY_ALLERGY_RATE = "SELECT `a`.`nome` AS `allergene`, "
            + "COUNT(`i`.`fk_allergene`) AS `intolleranze_count` FROM `intolleranza` `i` JOIN `allergene` `a` ON "
            + "`i`.`fk_allergene` = `a`.`nome` GROUP BY `a`.`nome` ORDER BY `intolleranze_count` DESC LIMIT 3;";

    /**
     * Query to fetch the diets in order by most followed to least followed.
     */
    private static final String GET_MOST_FOLLOWED_DIETS = "SELECT `d`.`nome` AS `dieta`, COUNT(`p`.`codice_fiscale`) "
            + "AS `numero_partecipanti` FROM `partecipante` `p` JOIN `dieta` `d` ON `p`.`fk_dieta` = `d`.`nome` GROUP "
            + "BY `d`.`nome` ORDER BY `numero_partecipanti` DESC;";

    /**
     * Query to fetch the activity with the longest duration within the database.
     */
    private static final String GET_LONGEST_ACTIVITY = "SELECT `a`.`nome` AS `attivita`, MAX(TIMESTAMPDIFF(MINUTE, "
            + "`s`.`ora_inizio`, `s`.`ora_fine`)) AS `durata_minuti` FROM `svolgimento` `s` JOIN `attivita` `a` "
            + "ON `s`.`fk_attivita` = `a`.`nome` GROUP BY `a`.`nome` ORDER BY `durata_minuti` DESC LIMIT 1;";
}
