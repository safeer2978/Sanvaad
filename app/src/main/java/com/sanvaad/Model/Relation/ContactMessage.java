package com.sanvaad.Model.Relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.sanvaad.Model.Entity.Contact;
import com.sanvaad.Model.Entity.Message;

import java.util.List;

public class ContactMessage {
    @Embedded
    Contact contact;

    @Relation(
            parentColumn = "contactID",
            entityColumn = "ID"
    )
    List<Message> message;

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public List<Message> getMessage() {
        return message;
    }

    public void setMessage(List<Message> message) {
        this.message = message;
    }
}
