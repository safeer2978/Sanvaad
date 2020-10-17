package com.sanvaad.Model.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;



@Entity(tableName = "conversation")
public class Conversation {
    @PrimaryKey(autoGenerate = true)
    private long convoID;

    private long cdate;

    private long convcreatorID;

    private long messageID;

    public Conversation(long cdate, long convcreatorID) {
        this.cdate = cdate;
        this.convcreatorID = convcreatorID;
    }

    public void setCdate(long cdate) {
        this.cdate = cdate;
    }

    public long getConvcreatorID() {
        return convcreatorID;
    }

    public void setConvcreatorID(long convcreatorID) {
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

}
