package com.sanvaad.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.sanvaad.Model.Repository;
import com.sanvaad.Model.TextData;

public class ChatActivityViewModel extends AndroidViewModel {
    Repository repository;
    boolean triggerState = false;

    public ChatActivityViewModel(Application application) {
        super(application);
        this.repository = Repository.getInstance(application);
    }

    public LiveData<TextData> getTextData(){
        return repository.getTextData();
    }

    public void triggerListening(){
        repository.triggerListening(triggerState);
        triggerState = !triggerState;
    }

    public void speakText(String text){
        repository.speakText(text);
    }
    public boolean gettriggerListeningState(){
        return triggerState;
    }
}
