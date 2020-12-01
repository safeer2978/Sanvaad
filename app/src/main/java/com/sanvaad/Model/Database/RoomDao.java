package com.sanvaad.Model.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.sanvaad.Model.Constants;
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
    void insertCommonMessage(CommonMessage commonMessage);

    @Insert
    void insertContact(Contact contact);

    @Insert
    void insertConversation(Conversation conversation);

    @Insert
    void insertMessage(Message message);

    @Query("select * from user")
    List<User> getUser();

    @Query("select * from contact")
   List<Contact> getContact();

    @Query("select * from contact where contactID like '%' || :id || '%'")
    Contact getContact(long id);

    @Query("select * from commonMessage where userID like '%' || :userID || '%'")
    List<CommonMessage> getCommonMessageList(long userID);

    @Query("select * from conversation where convcreatorID like '%' || :userID || '%'")
    LiveData<List<Conversation>> getConversation(long userID);


    @Query("select * from commonMessage where userID like '%' || "+Constants.ADMIN_ID+"|| '%'")
    List<CommonMessage> getAdminCommonMessageList();

    @Query("delete from commonMessage where userID like '%' || "+Constants.ADMIN_ID+"|| '%' ")
    void deleteAllAdminMessages();

    @Query("select * from message where convID like '%' || :convID ||'%'")
    List<Message> getMessages(long convID);

    @Insert
    void insertAdminMessages(List<CommonMessage> adminMessages);

    @Update
    void updateUser(User user);

    @Update
    void updateContact(Contact contact);

    @Query("DELETE from user")
    void deleteAllUser();

    @Query("select * from contact where firebaseUserID like '%' || :userID ||'%'")
    LiveData<List<Contact>> getContactsLiveData(String userID);

    @Delete
    void deleteContact(Contact contact);

    @Query("select * from conversation")
    LiveData<List<Conversation>> getConversations();
}
