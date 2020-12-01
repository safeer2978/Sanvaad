package com.sanvaad.Model;

import android.app.Application;
import android.util.Log;

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
import java.util.HashMap;
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

        updateAdminMessages();
    }


    public void createUser(User user){
        databaseUsers.child(String.valueOf(user.getFirebaseId())).setValue(user);
        setCurrentUser(user);
    }

    boolean isUserSet=false;

    public void setCurrentUser(User user){
        isUserSet=true;
        Dao.deleteAllUser();
        Dao.insertUser(user);
        this.user = user;
        listener.registered();
    }

    RepositoryListener listener;

    public void setListener(RepositoryListener repositoryListener){
        listener = repositoryListener;
    }

    public void userExists(String uid){
        isUserSet=false;
        databaseUsers.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user != null)
                    setCurrentUser(user);
                else
                    listener.notRegistered();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
        List<User> users = Dao.getUser();
        if(users.size()==0)
            return null;
        return user==null?Dao.getUser().get(0):user;
    }

    public List<Contact> getContactList(){
        return Dao.getContact();
    }

    public Contact getContactFromID(long ID){
        return Dao.getContact(ID);
    }


    public List<CommonMessage> getCommonMessagesOfUser(long userID){
        return Dao.getCommonMessageList(userID);
    }

    public List<Message> getMessages(long convID){
        return Dao.getMessages(convID);
    }

    public void updateContact(Contact contact){
        Dao.updateContact(contact);
    }
    public void updateUser(User user){
        Dao.updateUser(user);
    }

    private void updateAdminMessages(){
        databaseCommonMessages
                .child("ADMIN")
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<CommonMessage> commonMessages = new ArrayList<>();
                for(DataSnapshot ds: snapshot.getChildren()){
                    CommonMessage commonMessage = new CommonMessage(ds.child("message").getValue(String.class),-1);
                    commonMessages.add(commonMessage);
                    Log.w("UserDataStore", "Admin Message Recievec:"+commonMessage.getMessage());
                }
                Dao.deleteAllAdminMessages();
                Dao.insertAdminMessages(commonMessages);
                Log.w("UserDataStore", "Admin messages Updated");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        /*DatabaseReference reference = databaseCommonMessages;
        reference.addValueEventListener(
                new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        GenericTypeIndicator<ArrayList<CommonMessage>> genericTypeIndicator = new GenericTypeIndicator<ArrayList<CommonMessage>>();
                                                List<CommonMessage> commonMessages = snapshot.child("ADMIN").getValue(genericTypeIndicator);
                                                System.out.println("DATA:"+list[0]);
                                                Dao.deleteAllAdminMessages();
                                                for(CommonMessage message:commonMessages){
                                                    Dao.insertCommonMessage(message);
                                                    System.out.println("DATA:"+message.getMessage());
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        }
        );*/

    }

    public List<CommonMessage> getAdminCommonMessage(){
        return Dao.getAdminCommonMessageList();
    }

    public void saveMessages(List<Message> messages) {
        for(Message message: messages)
            Dao.insertMessage(message);
    }

    public LiveData<List<Contact>> getContactsLiveData(String userID) {
        return Dao.getContactsLiveData(userID);
    }

    public void deleteContact(Contact contact) {
        Dao.deleteContact(contact);
    }

    public LiveData<List<Conversation>> getConversationsLiveData() {
        return Dao.getConversations();
    }

    public Contact getContact(long contactID) {
        return Dao.getContact(contactID);
    }
}
