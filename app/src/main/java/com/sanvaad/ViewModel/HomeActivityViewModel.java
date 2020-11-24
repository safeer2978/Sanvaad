package com.sanvaad.ViewModel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.provider.ContactsContract;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.sanvaad.CommonParticipantsViewModel;
import com.sanvaad.Model.Constants;
import com.sanvaad.Model.Entity.Contact;
import com.sanvaad.Model.Entity.Conversation;
import com.sanvaad.Model.Entity.Message;
import com.sanvaad.Model.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class HomeActivityViewModel extends ViewModel implements CommonParticipantsViewModel {

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


    public List<Message> getMessages(Conversation c){
        return repository.getMessages(c);
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

    public LiveData<List<Conversation>> getConversations() {
        return repository.getConversationsLiveData();
    }

    Map<Contact,Integer> colorMap = new HashMap<>();

    @Override
    public int getColorInteger(Contact contact) {
        if(colorMap.get(contact)==null){
            Random rnd = new Random();
            int newColor = Color.argb(255, rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255));
            colorMap.put(contact,newColor);
        }
        return colorMap.get(contact);
    }

    public Contact getContact(long contactID) {
        List<Contact> contacts = repository.getContactList();
        for(Contact c : contacts)
            if(contactID==c.getId())
                return c;

            return null;
    }
}
