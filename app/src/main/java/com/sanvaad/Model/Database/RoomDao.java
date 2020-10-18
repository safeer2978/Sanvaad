package com.sanvaad.Model.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.sanvaad.Model.Entity.CommonMessage;
import com.sanvaad.Model.Entity.Contact;
import com.sanvaad.Model.Entity.Conversation;
import com.sanvaad.Model.Entity.Feedback;
import com.sanvaad.Model.Entity.Message;
import com.sanvaad.Model.Entity.User;

import java.util.List;

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

    @Insert
    void insertMessage(Message message);

    @Query("select * from user")
    List<User> getUser();

    @Query("select * from contact")
    LiveData<List<Contact>> getContact();

    @Query("select * from commonmessage where userID like '%' || :userID || '%'")
    LiveData<List<CommonMessage>> getCommonmessage(long userID);

    @Query("select * from conversation where userID like '%' || :userID || '%'")
    LiveData<List<Conversation>> getConversation(long userID);

    @Query("delete * from commonmessage where userID like:"+ Constants.Admin_ID)
    LiveData<List<CommonMessage>> delAdminmessages(long userID);

    @Query("select * from message where ConID like '%' || :ConID ||'%'")
    LiveData<List<Message>> getMessages(long ConID);

    @Insert
    void insertAdminmessages(List<CommonMessage> adminmessages);

    @Update
    void updateUser(User user);

    @Update
    void updateContact(Contact contact);

}
