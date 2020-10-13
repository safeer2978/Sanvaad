package com.sanvaad.Model.Relation;

import androidx.room.Embedded;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import com.sanvaad.Model.Entity.Conversation;
import com.sanvaad.Model.Entity.User;

import java.util.List;

public class UserChatBubble {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private long date_time;

    private String message;
    @Embedded
    User user;

    @Relation(
            parentColumn = "userID",
            entityColumn = "convcreatorID"

    )
    List<Conversation> conversations;

    public UserChatBubble(long date_time, String message) {
        this.date_time = date_time;
        this.message = message;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public long getDate_time() {
        return date_time;
    }


    public String getMessage() {
        return message;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Conversation> getConversations() {
        return conversations;
    }

    public void setConversations(List<Conversation> conversations) {
        this.conversations = conversations;
    }
}
