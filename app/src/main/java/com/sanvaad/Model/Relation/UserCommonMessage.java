package com.sanvaad.Model.Relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.sanvaad.Model.Entity.CommonMessage;
import com.sanvaad.Model.Entity.User;

import java.util.List;

public class UserCommonMessage {
    @Embedded
    User user;

    @Relation(
            parentColumn = "userID",
            entityColumn = "userID"
    )
    List<CommonMessage> commonmessages;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CommonMessage> getCommonmessages() {
        return commonmessages;
    }

    public void setCommonmessages(List<CommonMessage> commonmessages) {
        this.commonmessages = commonmessages;
    }
}
