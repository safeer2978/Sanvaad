package com.sanvaad.Model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.sanvaad.Model.Database.Database;
import com.sanvaad.Model.Database.RoomDao;
import com.sanvaad.Model.Entity.CommonMessage;
import com.sanvaad.Model.Entity.Contact;
import com.sanvaad.Model.Entity.Conversation;
import com.sanvaad.Model.Entity.Feedback;
import com.sanvaad.Model.Entity.Message;
import com.sanvaad.Model.Entity.User;

import java.util.ArrayList;
import java.util.List;


public class UserDataStore {
    RoomDao Dao;
    DatabaseReference databaseUsers;
    DatabaseReference databaseFeedbacks;
    DatabaseReference databaseCommonMessages;

    User user;

    public UserDataStore(Application application) {
        databaseUsers = FirebaseDatabase.getInstance().getReference("Users");
        databaseFeedbacks = FirebaseDatabase.getInstance().getReference("Feedbacks");
        databaseCommonMessages = FirebaseDatabase.getInstance().getReference("CommonMessages");
        Dao = Database.getDatabase(application).dao();

        user = getUser();

    }


    public void createUser(User user, String token){
        //TODO: Logic for user creation.
        //TODO: Also add User as a contact
            com.sanvaad.Model.Entity.User roomUser=new com.sanvaad.Model.Entity.User(user.getName(),user.getEmail(),user.getAge(),user.getPhoneNo(),user.getStatus());
            Dao.insertUser(roomUser);
            databaseUsers.child(String.valueOf(user.getUserID())).setValue(user);

    }

    public void createFeedback(Feedback feedback){
        //com.sanvaad.Model.Entity.Feedback roomFeedback = new com.sanvaad.Model.Entity.Feedback(feedback.getFdate(),feedback.getUserID(),feedback.getComment());
        Dao.insertFeedback(feedback);
        databaseFeedbacks.child(String.valueOf(user.getUserID())).child(String.valueOf(feedback.getFid())).setValue(feedback);       //.child(feedback));

    }

    public void createCommonMessage(CommonMessage commonMessage){
        Dao.insertCommonMessage(commonMessage);
        databaseCommonMessages.child(String.valueOf(user.getUserID())).child(String.valueOf(commonMessage.getMessageID())).setValue(commonMessage);
    }

    public void createContact(Contact contact){
        Dao.insertContact(contact);
    }
    public void createConversation(Conversation conversation){
        Dao.insertConversation(conversation);
    }
    public void createMessage(Message message){
        Dao.insertMessage(message);
    }

    public User getUser(){

        return Dao.getUser().get(0);
    }

    public LiveData<List<Contact>> getContact(){

        return Dao.getContact();
    }

    public LiveData<List<CommonMessage>> getCommonMessagesOfUser(long userID){
        return Dao.getCommonMessageList(userID);
    }

    public LiveData<List<Message>> getMessages(long convID){
        return Dao.getMessages(convID);
    }

    public void updateContact(Contact contact){
        Dao.updateContact(contact);
    }
    public void updateUser(User user){
        Dao.updateUser(user);
    }

    public void updateAdminMessages(){
        final List<CommonMessage>[] list = new List[]{new ArrayList<>()};
        DatabaseReference reference = databaseCommonMessages;
        reference.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                GenericTypeIndicator<ArrayList<CommonMessage>> genericTypeIndicator = new GenericTypeIndicator<ArrayList<CommonMessage>>() {
                                                };
                                                list[0] = snapshot.child("1").getValue(genericTypeIndicator);
                                                System.out.println("DATA:"+list[0]);
                                                Dao.deleteAllAdminMessages();
                                                for(CommonMessage message:list[0]){
                                                    Dao.insertCommonMessage(message);
                                                    System.out.println("DATA:"+message.getMessage());
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        }
        );



       // Dao.insertAdminMessages(list[0]);

    }

    public LiveData<List<CommonMessage>> getAdminCommonMessage(){
        return Dao.getAdminCommonMessageList();
    }

    public void saveMessages(List<Message> messages) {
        for(Message message: messages)
            Dao.insertMessage(message);
    }
}
