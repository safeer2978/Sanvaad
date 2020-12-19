package com.sanvaad;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.sanvaad.Model.Entity.Contact;

import java.util.List;

public interface ContactsViewModel {
    LiveData<List<Contact>> getContacts(String uid);
    void saveContact(Contact contact);
    void updateContact(Contact contact);
    void deleteContact(Contact contact);
    void addParticipant(Contact contact);
    int parent();
}
