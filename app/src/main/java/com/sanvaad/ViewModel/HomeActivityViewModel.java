package com.sanvaad.ViewModel;

import androidx.lifecycle.LiveData;

import com.sanvaad.Model.Entity.Contact;
import com.sanvaad.Model.Entity.Conversation;
import com.sanvaad.Model.Repository;

import java.util.ArrayList;
import java.util.List;

public class HomeActivityViewModel {

    Repository repository;

    public LiveData<List<Conversation>> getConversationList(){
        return repository.getAllConversationList();
    }

    public LiveData<List<Contact>> getAllContactsList(){
        return repository.getAllConstacts();
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





}
