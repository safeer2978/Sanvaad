package com.sanvaad.Model.Speech;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import com.sanvaad.Model.TextData;
import java.io.IOException;
import java.util.Calendar;
import java.util.logging.Handler;

public class SpeechFunctionDataStore {
    final TextToSpeech textToSpeech;
    SpeechToText speechToText;

    public SpeechFunctionDataStore(Context context){
        this.textToSpeech = new TextToSpeech(context);
        speechToText = new SpeechToText(context);
    }
    public void onStart(){
        speechToText.onStart();
    }
    public void onStop(){
        speechToText.onStop();
    }
    public LiveData<TextData> getTextData(){
        return speechToText.getTextData();
    }
    public void playTextToSpeech(String text){
        speechToText.onPause();
        textToSpeech.playText(text);
    }
    String TAG="SpeechModelTest:";
    void TextToSpeechTest(){
        Log.w(TAG, "Testing Text to Speech");
        Log.w(TAG, "Sending text 'Hi Hello Whats up?'");
        playTextToSpeech("Hi Hello Whats up?");
        Log.w(TAG, "text sent to API, kindly check the speaker output.");
    }

}
