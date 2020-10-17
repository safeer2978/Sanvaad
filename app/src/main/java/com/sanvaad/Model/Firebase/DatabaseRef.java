package com.sanvaad.Model.Firebase;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sanvaad.Model.Database.Database;
import com.sanvaad.Model.Database.RoomDao;
import com.sanvaad.Model.Entity.CommonMessage;
import com.sanvaad.Model.Entity.Contact;
import com.sanvaad.Model.Entity.Conversation;
import com.sanvaad.Model.Entity.Feedback;
import com.sanvaad.Model.Entity.User;

import java.util.List;


public class DatabaseRef {
    RoomDao Dao;
    DatabaseReference databaseUsers;
    DatabaseReference databaseFeedbacks;
    DatabaseReference databaseCommonmessages;

    public DatabaseRef(Application application) {
        databaseUsers = FirebaseDatabase.getInstance().getReference("Users");
        databaseFeedbacks = FirebaseDatabase.getInstance().getReference("Feedbacks");
        databaseCommonmessages = FirebaseDatabase.getInstance().getReference("Commonmessages");
        Dao = Database.getDatabase(application).dao();

    }

    public void createUser(User user, String token){
        if (){
            com.sanvaad.Model.Entity.User roomUser=new com.sanvaad.Model.Entity.User(user.getName(),user.getEmail(),user.getAge(),user.getPhoneno(),user.getStatus());
            Dao.insertUser(roomUser);
            databaseUsers.child(String.valueOf(user.getUserID())).child(user);
        }
    }

    public void giveFeedback(Feedback feedback){
        com.sanvaad.Model.Entity.Feedback roomFeedback = new com.sanvaad.Model.Entity.Feedback(feedback.getFdate(),feedback.getUserID(),feedback.getComment());
        Dao.insertFeedback(roomFeedback);
        databaseFeedbacks.child(feedback.getFid().child(feedback));

    }

    public void createCommonmessage(CommonMessage commonMessage){
        CommonMessage roomCommonmessage = new CommonMessage(commonMessage.getUserID(),commonMessage.getConvoID(),commonMessage.getMessage(),commonMessage.getMdate());
        Dao.insertCommonmessage(roomCommonmessage);
        databaseCommonmessages.child(commonMessage.getMessageID()).child(commonMessage);
    }
    public void createContact(Contact contact){
        Contact roomContact = new Contact(contact.getName(),contact.getImglink());
        Dao.insertContact(roomContact);
    }
    public void addConversation(Conversation conversation){
        Conversation roomConversation = new Conversation(conversation.getCdate(),conversation.getConvcreatorID());
        Dao.insertConversation(roomConversation);
    }

    User getUser(){

        return Dao.getUser().get(0);
    }

    LiveData<List<Contact>> getContact(){
        return Dao.getContact();
    }

    LiveData<List<CommonMessage>> getCommonmessage(){
        return Dao.getCommonmessage(long userID);
    }

    public void upContact(Contact contact){
        Dao.updateContact(contact);
    }
    public void updateUser(User user){
        Dao.updateUser(user);
    }
    public void updateAdminmessages(List<CommonMessage> adminmessage){
        Dao.delAdminmessages(long Admin_ID);
        Dao.insertAdminmessages(adminmessage);

    }
}
