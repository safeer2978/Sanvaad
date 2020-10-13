package com.sanvaad.Model.Relation;

import androidx.room.Embedded;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import com.sanvaad.Model.Entity.Contact;
import com.sanvaad.Model.Entity.Conversation;

import java.util.List;

public class ContactChatBubble {
    @PrimaryKey(autoGenerate = true)
    private  long ccbID;

    private long date_time;

    private String message;
    @Embedded
    Contact contact;

    @Relation(
            parentColumn = "conID",
            entityColumn = "convcreaterID"
    )
    List<Conversation> conversations;

    public ContactChatBubble(long date_time, String message) {
        this.date_time = date_time;
        this.message = message;
    }

    public long getCcbID() {
        return ccbID;
    }

    public long getDate_time() {
        return date_time;
    }

    public String getMessage() {
        return message;
    }

    public Contact getContact() {
        return contact;
    }

    public List<Conversation> getConversations() {
        return conversations;
    }

    public void setCcbID(long ccbID) {
        this.ccbID = ccbID;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public void setConversations(List<Conversation> conversations) {
        this.conversations = conversations;
    }
}
