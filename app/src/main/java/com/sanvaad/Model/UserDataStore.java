package com.sanvaad.Model;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

import io.grpc.netty.shaded.io.netty.handler.codec.MessageAggregationException;
public class UserDataStore {
    RoomDao Dao;
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
        //com.sanvaad.Model.Entity.Feedback roomFeedback = new com.sanvaad.Model.Entity.Feedback(feedback.getFdate(),feedback.getUserID(),feedback.getComment());
        Dao.insertFeedback(feedback);
        databasefeedbacks.child(feedback.getUserID()).child(String.valueOf(feedback.getFid())).setValue(feedback);       //.child(feedback));

    }
/*    private void ContactTests(){
        //20 functions to be tested
        final String TAG = "UserDataStore-Test = Contacts";
        Contact contact = new Contact("Safeer",951);
        createContact(contact);
        Log.w(TAG, "Tests: Contact created");
        getContactList();
        Log.w(TAG, "Tests: Contact retrieved"+getContact(951));
        updateContact(new Contact("Safeer Khan", 951));
        Log.w(TAG, "Tests: Contact Updated");
        Log.w(TAG, "Tests: Contact retrieved"+getContact(951));
    }
    private void MessageTest(){
        final String TAG = "UserDataStore-Test = Messages";
        Conversation conversation = new Conversation(new User("as89d","",12,"","ds"));
        conversation.setConvoID(459);
        createConversation(conversation);
        Log.d(TAG, "MessageTest: Conversation Created");
        List<Message> list = new ArrayList<>();
        list.add(new Message("asd", conversation));
        saveMessages(list);
        Log.d(TAG, "MessageTest: Message Saved");
        for(Message m: getMessages(conversation.getConvoID()))
            Log.d(TAG, "MessageTest: Message retrived"+m.getMessage());
    }
    private void CommonMessageTest(){
        String TAG = "UserDataStore-Test = Common Message";
        updateAdminMessages();
        Log.d(TAG, "Commonm messages Updated");
        for(CommonMessage m: getAdminCommonMessage())
            Log.d(TAG,"Common message received:"+m);
    }*/
    public Contact getContactFromID(long ID){
        return Dao.getContact(ID);
    }
    public void sendFeedback(Feedback feedback) {
        createFeedback(feedback);
    }
    public void updateUser(User user){
        Dao.updateUser(user);
    }
    public void createMessage(Message message){
        Dao.insertMessage(message);
    }
    public void createCommonMessage(CommonMessage commonMessage){
        Dao.insertCommonMessage(commonMessage);
        databaseCommonMessages.child(String.valueOf(user.getUserID())).child(String.valueOf(commonMessage.getMessageID())).setValue(commonMessage);
    }
}
