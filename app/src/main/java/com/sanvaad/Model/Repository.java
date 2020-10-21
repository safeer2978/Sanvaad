package com.sanvaad.Model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.sanvaad.Model.Speech.SpeechFunctionDataStore;


public class Repository {

    SpeechFunctionDataStore speechFunctionDataStore;

    UserDataStore userDataStore;


    Repository(Application application){
        speechFunctionDataStore = new SpeechFunctionDataStore(application.getApplicationContext());
        userDataStore = new UserDataStore(application);
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

}
