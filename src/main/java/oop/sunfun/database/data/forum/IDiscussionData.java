package oop.sunfun.database.data.forum;

import java.util.Set;

public interface IDiscussionData {
    int getId();
    String getName();
    String getSurname();
    String getTitle();
    String getDescription();
    Set<CommentData> getComments();
    void setComments(Set<CommentData> newComments);
}
