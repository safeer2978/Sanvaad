package com.sanvaad.Model.Speech;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.sanvaad.Model.TextData;

import java.io.IOException;

public class SpeechFunctionDataStore {

    final TextToSpeech textToSpeech;
    SpeechToText speechToText;

    public SpeechFunctionDataStore(Context context){
        this.textToSpeech = new TextToSpeech(context);
        speechToText = new SpeechToText(context);
        playTextToSpeech("");
    }

    public void onStart(){
        speechToText.onStart();
    }
    public void onStop(){
        speechToText.onStop();
    }



    void changeLanguage(){
    }

    void changeVoice(){

    }


    public LiveData<TextData> getTextData(){
        return speechToText.getTextData();
    }

    public void playTextToSpeech(String text){
        speechToText.onPause();
        textToSpeech.playText(text);
        speechToText.onStart();
    }
}
