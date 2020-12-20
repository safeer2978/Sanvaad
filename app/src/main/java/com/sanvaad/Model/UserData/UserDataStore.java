package com.sanvaad.Model.UserData;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sanvaad.Model.UserData.db.Entity.CommonMessage;
import com.sanvaad.Model.UserData.db.Entity.Contact;
import com.sanvaad.Model.UserData.db.Entity.Conversation;
import com.sanvaad.Model.UserData.db.Entity.Feedback;
import com.sanvaad.Model.UserData.db.Entity.Message;
import com.sanvaad.Model.UserData.db.Entity.User;
import com.sanvaad.Model.UserData.db.Database;
import com.sanvaad.Model.UserData.db.RoomDao;
import com.sanvaad.Model.Util.RepositoryListener;

import java.util.ArrayList;
import java.util.List;

/**
 * This class maintains all interaction between Local Database and Firebase*/
public class UserDataStore {
    RoomDao Dao;

    /**
     * For interacting with Firebase
     * **/
    DatabaseReference databaseUsers;
    DatabaseReference databasefeedbacks;
    DatabaseReference databaseCommonMessages;
    User user;
    RepositoryListener listener;
    boolean isUserSet=false;

    public UserDataStore(Application application) {
        databaseUsers = FirebaseDatabase.getInstance().getReference("Users");
        databasefeedbacks = FirebaseDatabase.getInstance().getReference("Feedbacks");
        databaseCommonMessages = FirebaseDatabase.getInstance().getReference("CommonMessages");
        Dao = Database.getDatabase(application).dao();
        user = getUser();
        updateAdminMessages();
    }

    public void createUser(User user){
        databaseUsers.child(String.valueOf(user.getFirebaseId())).setValue(user);
        setCurrentUser(user);
    }

    public void setCurrentUser(User user){
        isUserSet=true;
        Dao.deleteAllUser();
        Dao.insertUser(user);
        this.user = user;
        listener.registered();
    }

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

    public User getUser(){
        List<User> users = Dao.getUser();
        if(users.size()==0)
            return null;
        return user==null?Dao.getUser().get(0):user;
    }

    public void createContact(Contact contact){
        Dao.insertContact(contact);
    }

    public List<Contact> getContactList(){
        return Dao.getContact();
    }

    public void updateContact(Contact contact){
        Dao.updateContact(contact);
    }

    public LiveData<List<Contact>> getContactsLiveData(String userID) {
        return Dao.getContactsLiveData(userID);
    }

    public Contact getContact(long contactID) {
        return Dao.getContact(contactID);
    }

    public void deleteContact(Contact contact) {
        Dao.deleteContact(contact);
    }

    public List<CommonMessage> getCommonMessagesOfUser(long userID){
        return Dao.getCommonMessageList(userID);
    }

    public List<CommonMessage> getAdminCommonMessage(){
        return Dao.getAdminCommonMessageList();
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

    public void createConversation(Conversation conversation){
        Dao.insertConversation(conversation);
    }

    public List<Message> getMessages(long convID){
        return Dao.getMessages(convID);
    }

    public void saveMessages(List<Message> messages) {
        for(Message message: messages)
            Dao.insertMessage(message);
    }

    public LiveData<List<Conversation>> getConversationsLiveData(String uid) {
        return Dao.getConversations(uid);
    }

    public void createFeedback(Feedback feedback){
        //com.sanvaad.Model.UserData.db.Entity.Feedback roomFeedback = new com.sanvaad.Model.UserData.db.Entity.Feedback(feedback.getFdate(),feedback.getUserID(),feedback.getComment());
        Dao.insertFeedback(feedback);
        databasefeedbacks.child(feedback.getUserID()).child(String.valueOf(feedback.getFid())).setValue(feedback);       //.child(feedback));

    }

}
