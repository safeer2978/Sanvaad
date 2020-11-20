package com.sanvaad.Model;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.annotations.NotNull;
import com.sanvaad.Model.Entity.CommonMessage;
import com.sanvaad.Model.Entity.Contact;
import com.sanvaad.Model.Entity.Conversation;
import com.sanvaad.Model.Entity.Message;
import com.sanvaad.Model.Entity.User;
import com.sanvaad.Model.Speech.SpeechFunctionDataStore;
import com.sanvaad.View.Login.LoginListener;

import java.util.List;
import java.util.Objects;


public class Repository implements RepositoryListener{

    SpeechFunctionDataStore speechFunctionDataStore;

    UserDataStore userDataStore;
    User user;

    Repository(Application application){
        speechFunctionDataStore = new SpeechFunctionDataStore(application.getApplicationContext());
        userDataStore = new UserDataStore(application);
        userDataStore.setListener(this);
        SharedPreferences sharedPreferences = application.getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean(Constants.LOGIN_STATUS,false);
        if(isLoggedIn)
            user=userDataStore.getUser();
    }

    private static Repository INSTANCE;
    public static Repository getInstance(Application application){
        if(INSTANCE == null){
            INSTANCE = new Repository(application);

        }
        return INSTANCE;
    }

    public LiveData<TextData> getTextData(){
        return speechFunctionDataStore.getTextData();
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

    public void changeUserSpeechVoice(String userGender){
        speechFunctionDataStore.changeVoice(userGender);
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
        return userDataStore.getContact().get(0);
    }

    public void registerNewUser(User user){
        userDataStore.createUser(user);
        this.user = user;
    }

/*    public boolean isUserRegistered(@NotNull FirebaseUser firebaseUser){
            if(userDataStore.userExists(firebaseUser.getUid().replace(".","_"))){
                this.user = userDataStore.getUser();
                return true;
            }
            else{
                return  false;
            }
    }
    */
    LoginListener loginListener;
    FirebaseUser firebaseUser;
    public void handleLoginSuccess(LoginListener listener, FirebaseUser firebaseUser){
        loginListener=listener;
        this.firebaseUser=firebaseUser;
        userDataStore.userExists(firebaseUser.getUid().replace(".","_"));
    }



    public List<Contact> getContactList() {
        return userDataStore.getContact();
    }

    public List<CommonMessage> getCommonMessages(User user) {
        return userDataStore.getCommonMessagesOfUser(user.getUserID());
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
}

interface RepositoryListener{
    void registered();
    void notRegistered();
}
