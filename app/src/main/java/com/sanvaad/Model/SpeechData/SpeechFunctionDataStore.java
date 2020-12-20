package com.sanvaad.Model.SpeechData;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.sanvaad.Model.Util.TextData;

public class SpeechFunctionDataStore {
    final TextToSpeech textToSpeech;
    SpeechToText speechToText;

    Context context;
    public SpeechFunctionDataStore(Context context){
        this.context=context;
        speechToText = new SpeechToText(context);
        this.textToSpeech = new TextToSpeech(context);

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
        speechToText.onResume();
    }


    public void setGender(boolean gender){
        textToSpeech.setVoiceGender(gender);
    }
    String TAG="SpeechModelTest:";
    void TextToSpeechTest(){
        Log.w(TAG, "Testing Text to Speech");
        Log.w(TAG, "Sending text 'Hi Hello Whats up?'");
        playTextToSpeech("Hi Hello Whats up?");
        Log.w(TAG, "text sent to API, kindly check the speaker output.");
    }

}
