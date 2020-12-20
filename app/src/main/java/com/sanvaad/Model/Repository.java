package com.sanvaad.Model;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseUser;
import com.sanvaad.Model.UserData.UserDataStore;
import com.sanvaad.Model.UserData.db.Entity.CommonMessage;
import com.sanvaad.Model.UserData.db.Entity.Contact;
import com.sanvaad.Model.UserData.db.Entity.Conversation;
import com.sanvaad.Model.UserData.db.Entity.Feedback;
import com.sanvaad.Model.UserData.db.Entity.Message;
import com.sanvaad.Model.UserData.db.Entity.User;
import com.sanvaad.Model.SpeechData.SpeechFunctionDataStore;
import com.sanvaad.Model.Util.Constants;
import com.sanvaad.Model.Util.RepositoryListener;
import com.sanvaad.Model.Util.TextData;
import com.sanvaad.View.Login.LoginListener;

import java.util.List;


public class Repository implements RepositoryListener {

    SpeechFunctionDataStore speechFunctionDataStore;
    UserDataStore userDataStore;
    User user;
    Application application;
    public UserDataStore getUserDataStore(){
        return userDataStore;
    }

    Repository(Application application){
        speechFunctionDataStore = new SpeechFunctionDataStore(application.getApplicationContext());
        speechFunctionDataStore.setGender(application
                .getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE)
                .getBoolean(Constants.GENDER, true));
        userDataStore = new UserDataStore(application);
        userDataStore.setListener(this);
        updateUser(application);
        this.application=application;
    }
    private static Repository INSTANCE;

    public static Repository getInstance(Application application){
        if(INSTANCE == null){
            INSTANCE = new Repository(application);
        }
        return INSTANCE;
    }
    public LiveData<TextData> getTextData(){
        //triggerListening(false);
        return speechFunctionDataStore.getTextData();
    }

    public void stopListening(){
        speechFunctionDataStore.onStop();
    }
    public void triggerListening(boolean isListening){
        if (isListening)
            speechFunctionDataStore.onStop();
        else
            speechFunctionDataStore.onStart();
    }
    public void speakText(String s){
        speechFunctionDataStore.playTextToSpeech(s);
    }
    public User getUser(){
        if(user == null)
            user = userDataStore.getUser();
        return user;
    }
    public void saveMessages(List<Message> messages) {
        userDataStore.saveMessages(messages);
    }

    public Contact getUserAsContact() {
        return userDataStore.getContactList().get(0);
    }
    public void registerNewUser(User user){
        userDataStore.createUser(user);
        this.user = user;
    }
    LoginListener loginListener;
    FirebaseUser firebaseUser;
    public void handleLoginSuccess(LoginListener listener, FirebaseUser firebaseUser){
        loginListener=listener;
        this.firebaseUser=firebaseUser;
        userDataStore.userExists(firebaseUser.getUid().replace(".","_"));
    }
    public List<Contact> getContactList() {
        return userDataStore.getContactList();
    }
    public LiveData<List<Contact>> getContactLiveData(String userID) {
        return userDataStore.getContactsLiveData(userID);
    }
    public List<CommonMessage> getCommonMessages(User user) {
        List<CommonMessage> list;
        list = userDataStore.getCommonMessagesOfUser(user.getUserID());
        list.addAll(userDataStore.getAdminCommonMessage());
        return list;
    }
    public List<Message> getMessages(Conversation c){
        return userDataStore.getMessages(c.getConvoID());
    }
    public List<Contact> getParticipants(Conversation c){
        /*TODO: Write Logic for this....*/
        return null;
    }
    @Override
    public void registered() {
        loginListener.updateUI();
    }
    @Override
    public void notRegistered() {
        loginListener.showRegistrationForm(firebaseUser);
    }
    GoogleSignInClient googleSignInClient;
    public void saveGoogleClient(GoogleSignInClient mGoogleSignInClient) {
        this.googleSignInClient=mGoogleSignInClient;
    }
    public GoogleSignInClient getGoogleSignInClient(){
        return googleSignInClient;
    }
    public void updateContact(Contact contact) {
        userDataStore.updateContact(contact);
    }
    public void saveContact(Contact contact) {
        for(Contact c: userDataStore.getContactList())
            if(c.getFirebaseUserID().equals(contact.getFirebaseUserID()) && c.getName().equals(contact.getName()))
                return;
        userDataStore.createContact(contact);
    }
    public void deleteContact(Contact contact) {
        userDataStore.deleteContact(contact);
    }
    public LiveData<List<Conversation>> getConversationsLiveData(String uid) {
        return userDataStore.getConversationsLiveData(uid);
    }
    public void endConversation(List<Message> messages, Conversation conversation) {
        userDataStore.saveMessages(messages);
        userDataStore.createConversation(conversation);
    }
    public void updateUser(Application application){
        SharedPreferences sharedPreferences = application.getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean(Constants.LOGIN_STATUS,false);
        if(isLoggedIn)
            user=userDataStore.getUser();
    }
    public void sendFeedBack(Feedback feedback) {
        userDataStore.createFeedback(feedback);
    }

    public void updateGenderPref() {
        speechFunctionDataStore.setGender(application.getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE).getBoolean(Constants.GENDER, true));
    }
}

