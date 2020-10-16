package com.sanvaad.Model.Firebase;

import android.app.Application;

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

    private void createUser(User user, String token){
        if (){
            com.sanvaad.Model.Entity.User roomUser=new com.sanvaad.Model.Entity.User(user.getName(),user.getEmail(),user.getAge(),user.getPhoneno(),user.getStatus());
            Dao.insertUser(roomUser);
            databaseUsers.child(String.valueOf(user.getUserID())).child(user);
        }
    }

    private void giveFeedback(Feedback feedback){
        com.sanvaad.Model.Entity.Feedback roomFeedback = new com.sanvaad.Model.Entity.Feedback(feedback.getFdate(),feedback.getUserID(),feedback.getComment());
        Dao.insertFeedback(roomFeedback);
        databaseFeedbacks.child(feedback.getFid().child(feedback));

    }

    private void createCommonmessage(CommonMessage commonMessage){
        CommonMessage roomCommonmessage = new CommonMessage(commonMessage.getUserID(),commonMessage.getConvoID(),commonMessage.getMessage(),commonMessage.getMdate());
        Dao.insertCommonmessage(roomCommonmessage);
        databaseCommonmessages.child(commonMessage.getMessageID()).child(commonMessage);
    }
    private void createContact(Contact contact){
        Contact roomContact = new Contact(contact.getName(),contact.getImglink());
        Dao.insertContact(roomContact);
    }
    private void addConversation(Conversation conversation){
        Conversation roomConversation = new Conversation(conversation.getCdate(),conversation.getConvcreatorID());
        Dao.insertConversation(roomConversation);
    }
}
