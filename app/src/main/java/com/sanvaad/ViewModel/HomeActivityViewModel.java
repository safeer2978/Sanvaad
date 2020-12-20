package com.sanvaad.ViewModel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.sanvaad.CommonParticipantsViewModel;
import com.sanvaad.ContactsViewModel;
import com.sanvaad.Model.Constants;
import com.sanvaad.Model.Entity.Contact;
import com.sanvaad.Model.Entity.Conversation;
import com.sanvaad.Model.Entity.Feedback;
import com.sanvaad.Model.Entity.Message;
import com.sanvaad.Model.Entity.User;
import com.sanvaad.Model.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class HomeActivityViewModel extends ViewModel implements CommonParticipantsViewModel, ContactsViewModel {
    Repository repository;
    Application application;
    public void init(Application application){
        repository = Repository.getInstance(application);
        colorMap = new HashMap<>();
    }

    Map<Long,Integer> colorMap;

    @Override
    public int getColorInteger(Contact contact) {
        if(!colorMap.containsKey(contact.getContactID())){
            Random rnd = new Random();
            int newColor = Constants.colorSet.get(rnd.nextInt(5));
            while(colorMap.size()<5 && colorMap.containsValue(newColor))
                newColor = Constants.colorSet.get(rnd.nextInt(5));
            colorMap.put(contact.getContactID(),newColor);
        }
        return colorMap.get(contact.getContactID());
    }

    public Contact getContact(long contactID) {
        List<Contact> contacts = repository.getContactList();
        for(Contact c : contacts)
            if(contactID==c.getId())
                return c;

        return null;
    }

    public List<Contact> getParticipants(Conversation conversation) {
        List<Contact> list = new ArrayList<>();
        for(Message message: repository.getMessages(conversation)){
            Contact contact = repository.getUserDataStore().getContact(message.getContactID());
            if(contact==null)
                continue;
            if(!check(list,contact) && contact.getId()!=-1)
                list.add(contact);
        }
        return list;
    }

    private boolean check(List<Contact> list, Contact contact){
        for(Contact c: list)
            if(c.getName().equals(contact.getName()))
                return true;
        return false;
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

    public LiveData<List<Contact>> getContacts(String userID) {
        return repository.getContactLiveData(userID);
    }

    public void deleteContact(Contact contact) {
        repository.deleteContact(contact);
    }

    @Override
    public void addParticipant(Contact contact) {

    }

    @Override
    public int parent() {
        return Constants.HOME_VIEWMODEL;
    }

    public LiveData<List<Conversation>> getConversations(String uid) {
        return repository.getConversationsLiveData(uid);
    }
    public User getUser() {
        return repository.getUser();
    }

    public void sendFeedBack(Feedback feedback) {
        repository.sendFeedBack(feedback);
    }

    public void updateGenderPref() {
        repository.updateGenderPref();
    }
}
