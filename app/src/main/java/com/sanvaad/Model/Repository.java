package com.sanvaad.Model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.annotations.NotNull;
import com.sanvaad.Model.Entity.Contact;
import com.sanvaad.Model.Entity.Conversation;
import com.sanvaad.Model.Entity.Message;
import com.sanvaad.Model.Entity.User;
import com.sanvaad.Model.Speech.SpeechFunctionDataStore;

import java.util.List;
import java.util.Objects;


public class Repository {

    SpeechFunctionDataStore speechFunctionDataStore;

    UserDataStore userDataStore;
    User user;

    Repository(Application application){
        speechFunctionDataStore = new SpeechFunctionDataStore(application.getApplicationContext());
        userDataStore = new UserDataStore(application);

        //TODO Remove this
        this.user = new User();
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
        return Objects.requireNonNull(userDataStore.getContact().getValue()).get(0);
    }

    public void registerNewUser(User user){
        userDataStore.createUser(user);
        this.user = user;
    }

    public boolean isUserRegistered(@NotNull FirebaseUser firebaseUser){
            if(userDataStore.userExists(firebaseUser.getUid())){
                this.user = userDataStore.getUser();
                return true;
            }
            else{
                return  false;
            }
    }


    public List<Message> getMessages(Conversation c){

    }

    public List<Contact> getParticipants(Conversation c){

    }



}
