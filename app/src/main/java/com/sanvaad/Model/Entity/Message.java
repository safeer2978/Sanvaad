package com.sanvaad.Model.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;

import javax.annotation.Generated;

@Entity(tableName = "message")
public class Message {
    private String message;

    @PrimaryKey(autoGenerate = true)
    private long ID;

    private long messageDate;

    private int position;

    private int convID;

    public int getConvID() {
        return convID;
    }

    public void setConvID(int convID) {
        this.convID = convID;
    }

    public Message( String message, int position, int convID) {
        this.message = message;
        this.messageDate = Calendar.getInstance().getTimeInMillis();
        this.position = position;
        this.convID = convID;
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
