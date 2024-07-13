package oop.sunfun.database.data.forum;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class CommentData {
    private static final String NUM_RESPONSE_SQL = "num_risposta";
    private static final String RESPONSE_SQL = "testo";
    private static final String NAME_SQL = "nome";
    private static final String SURNAME_SQL = "cognome";

    private static final Set<String> SQL_COLUMNS;

    static {
        SQL_COLUMNS = new HashSet<>();
        SQL_COLUMNS.add(NUM_RESPONSE_SQL);
        SQL_COLUMNS.add(RESPONSE_SQL);
        SQL_COLUMNS.add(NAME_SQL);
        SQL_COLUMNS.add(SURNAME_SQL);
    }

    private final int numResponse;
    private final String response;
    private final String name;
    private final String surname;

    public CommentData(final Map<String, Object> sqlData) {
        if (SQL_COLUMNS.stream().anyMatch(q -> !sqlData.containsKey(q))) {
            throw new IllegalStateException("The passed sql data is not made for CommentData!");
        }
        this.numResponse = (Integer) sqlData.get(NUM_RESPONSE_SQL);
        this.response = (String) sqlData.get(RESPONSE_SQL);
        this.name = (String) sqlData.get(NAME_SQL);
        this.surname = (String) sqlData.get(SURNAME_SQL);
    }

    public int getResponseNumber() {
        return this.numResponse;
    }

    public String getResponse() {
        return this.response;
    }

    public String getSurname() {
        return this.surname;
    }

    public String getName() {
        return this.name;
    }
}
