package com.sanvaad.ViewModel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.sanvaad.Model.Constants;
import com.sanvaad.Model.Entity.Contact;
import com.sanvaad.Model.Entity.Conversation;
import com.sanvaad.Model.Repository;

import java.util.ArrayList;
import java.util.List;

public class HomeActivityViewModel extends ViewModel {

    Repository repository;

    Application application;

/*    public LiveData<List<Conversation>> getConversationList(){
        return repository.getAllConversationList();
    }*/

 /*   public LiveData<List<Contact>> getAllContactsList(){
        return repository.getAllConstacts();
    }
*/

    public void init(Application application){
        repository = Repository.getInstance(application);
    }

    public Contact getUserProfileData(){
        return repository.getUserAsContact();
    }

    public List<List> getListDataForConversation(Conversation c){
        List<List> chatDataLists = new ArrayList<>();
        chatDataLists.add(repository.getMessages(c));
        chatDataLists.add(repository.getParticipants(c));
        return chatDataLists;
    }

    public void logout(){
        SharedPreferences sharedPreferences = application.getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(Constants.LOGIN_STATUS,false).apply();
    }


    public void saveContact(Contact contact) {
        repository.saveContact(contact);
    }

    public void updateContact(Contact contact) {
        repository.updateContact(contact);
    }

    public LiveData<List<Contact>> getContacts() {
        return repository.getContactLiveData();
    }

    public void deleteContact(Contact contact) {
        repository.deleteContact(contact);
    }
}
