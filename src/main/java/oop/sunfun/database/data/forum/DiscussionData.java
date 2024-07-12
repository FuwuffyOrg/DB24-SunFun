package oop.sunfun.database.data.forum;

import java.util.HashSet;
import java.util.Set;

public class DiscussionData implements IDiscussionData {
    private final int id;
    private final String name;
    private final String surname;
    private final String title;
    private final String description;
    private final Set<CommentData> comments;

    public DiscussionData(final int _id, final String _name, final String _surname, final String _title,
                          final String _description) {
        this.id = _id;
        this.name = _name;
        this.surname = _surname;
        this.title = _title;
        this.description = _description;
        this.comments = new HashSet<>();
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getSurname() {
        return this.surname;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public Set<CommentData> getComments() {
        return Set.copyOf(this.comments);
    }

    @Override
    public void setComments(final Set<CommentData> newComments) {
        this.comments.clear();
        this.comments.addAll(newComments);
    }
}
