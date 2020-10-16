package com.sanvaad.Model.Speech;

import android.content.Context;

import java.io.IOException;

public class SpeechFunctionDataStore {

    final TextToSpeech textToSpeech;

    public SpeechFunctionDataStore(Context context) throws IOException {
        this.textToSpeech = new TextToSpeech();
    }

    void changeLanguage(){
    }

    void changeVoice(){

    }

    void playTextToSpeech(String text){
        textToSpeech.playText(text);
    }
}
