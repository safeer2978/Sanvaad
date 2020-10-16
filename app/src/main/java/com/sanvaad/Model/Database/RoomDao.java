package com.sanvaad.Model.Database;

import androidx.room.Dao;
import androidx.room.Insert;

import com.sanvaad.Model.Entity.CommonMessage;
import com.sanvaad.Model.Entity.Contact;
import com.sanvaad.Model.Entity.Conversation;
import com.sanvaad.Model.Entity.Feedback;
import com.sanvaad.Model.Entity.User;

@Dao
public interface RoomDao {

    @Insert
    void insertUser(User user);

    @Insert
    void insertFeedback(Feedback feedback);

    @Insert
    void insertCommonmessage(CommonMessage commonMessage);

    @Insert
    void insertContact(Contact contact);

    @Insert
    void insertConversation(Conversation conversation);

}
