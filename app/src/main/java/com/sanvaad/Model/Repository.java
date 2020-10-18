package com.sanvaad.Model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.google.firebase.database.DatabaseReference;
import com.sanvaad.Model.Firebase.DatabaseRef;
import com.sanvaad.Model.Speech.SpeechFunctionDataStore;


public class Repository {

    SpeechFunctionDataStore speechFunctionDataStore;
    DatabaseRef databaseRef;


    Repository(Application application){
        speechFunctionDataStore = new SpeechFunctionDataStore(application.getApplicationContext());
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


}
