package com.sanvaad.Model.Entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserWithFeedback {
    @Embedded User user;

    @Relation(
            parentColumn = "userID",
            entityColumn = "userID"
    )
    List<Feedback> feedbacks;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }
}
