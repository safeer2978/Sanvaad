package com.sanvaad.Model.Relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.sanvaad.Model.Entity.Contact;
import com.sanvaad.Model.Entity.User;

import java.util.List;

public class UserContact {
    @Embedded
    User user;


    @Relation(
            parentColumn = "",
            entityColumn = ""
    )
    List<Contact> contacts;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }
}
