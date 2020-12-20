package com.sanvaad.Model.UserData.db.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;


@Entity(tableName = "conversation")
public class Conversation {
    @PrimaryKey(autoGenerate = true)
    private long convoID;

    private long cdate;

    private String convcreatorID;

    private long messageID;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    String title;

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    private int top=0;

    public Conversation(String userID) {
        this.cdate = Calendar.getInstance().getTimeInMillis();
        this.convcreatorID = userID;
    }

    public Conversation(){

    }


    public void setCdate(long cdate) {
        this.cdate = cdate;
    }

    public String getConvcreatorID() {
        return convcreatorID;
    }

    public void setConvcreatorID(String convcreatorID) {
        this.convcreatorID = convcreatorID;
    }

    public long getMessageID() {
        return messageID;
    }

    public void setMessageID(long messageID) {
        this.messageID = messageID;
    }

    public void setConvoID(long convoID) {
        this.convoID = convoID;
    }

    public long getConvoID() {
        return convoID;
    }

    public long getCdate() {
        return cdate;
    }

    public void incrementMessageCount() {
        this.top+=1;
    }
}
