package com.sanvaad.ViewModel.Interfaces;

import androidx.lifecycle.LiveData;

import com.sanvaad.Model.UserData.db.Entity.Contact;

import java.util.List;

public interface ContactsViewModel {
    LiveData<List<Contact>> getContacts(String uid);
    void saveContact(Contact contact);
    void updateContact(Contact contact);
    void deleteContact(Contact contact);
    void addParticipant(Contact contact);
    int parent();
}
