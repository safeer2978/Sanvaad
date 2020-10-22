package com.sanvaad.Model.Entity;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;

@Entity(tableName = "message")
public class Message {
    private String message;

    @PrimaryKey(autoGenerate = true)
    private long ID;

    private long messageDate;

    private int position;

    private long convID;

    public long getContactID() {
        return contactID;
    }

    public void setContactID(long contactID) {
        this.contactID = contactID;
    }

    @Nullable
    private long contactID;

    public long getConvID() {
        return convID;
    }

    public void setConvID(long convID) {
        this.convID = convID;
    }

    public Message( String message, Conversation conversation) {
        this.message = message;
        this.messageDate = Calendar.getInstance().getTimeInMillis();
        this.position = conversation.getTop();
        this.convID = conversation.getConvoID();
        contactID=-1;
    }

    public void setContact(Contact contact){
        this.contactID = contact.getId();
    }


    public Message(){

    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public long getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(long messageDate) {
        this.messageDate = messageDate;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
