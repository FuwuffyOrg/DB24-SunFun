package oop.sunfun.database.data.forum;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class DiscussionData {
    private static final String NUM_DISCUSSION_SQL = "num_discussione";
    private static final String NAME_SQL = "nome";
    private static final String SURNAME_SQL = "cognome";
    private static final String TITLE_SQL = "titolo";
    private static final String DESCRIPTION_SQL = "descrizione";

    private static final Set<String> SQL_COLUMNS;

    static {
        SQL_COLUMNS = new HashSet<>();
        SQL_COLUMNS.add(NUM_DISCUSSION_SQL);
        SQL_COLUMNS.add(NAME_SQL);
        SQL_COLUMNS.add(SURNAME_SQL);
        SQL_COLUMNS.add(TITLE_SQL);
        SQL_COLUMNS.add(DESCRIPTION_SQL);
    }

    private final int numDiscussion;
    private final String name;
    private final String surname;
    private final String title;
    private final String description;
    private final Set<CommentData> comments;

    public DiscussionData(final Map<String, Object> sqlData) {
        if (SQL_COLUMNS.stream().anyMatch(q -> !sqlData.containsKey(q))) {
            throw new IllegalStateException("The passed sql data is not made for DiscussionData!");
        }
        this.numDiscussion = (Integer) sqlData.get(NUM_DISCUSSION_SQL);
        this.name = (String) sqlData.get(NAME_SQL);
        this.surname = (String) sqlData.get(SURNAME_SQL);
        this.title = (String) sqlData.get(TITLE_SQL);
        this.description = (String) sqlData.get(DESCRIPTION_SQL);
        this.comments = new HashSet<>();
    }

    public int getDiscussionNumber() {
        return this.numDiscussion;
    }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public Set<CommentData> getComments() {
        return Set.copyOf(this.comments);
    }

    public void setComments(final Set<CommentData> newComments) {
        this.comments.clear();
        this.comments.addAll(newComments);
    }
}
