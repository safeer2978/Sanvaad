package com.sanvaad.Model.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "commonmessage")
public class CommonMessage {
    @PrimaryKey(autoGenerate = true)
    private long messageID;

    private long userID;

    public void setCmcreatorID(long cmcreatorID) {
        this.cmcreatorID = cmcreatorID;
    }

    public void setConvoID(long convoID) {
        this.convoID = convoID;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMdate(long mdate) {
        this.mdate = mdate;
    }

    public long getCmcreatorID() {
        return cmcreatorID;
    }

    public long getConvoID() {
        return convoID;
    }

    private long convoID;


    private String message;

    private long mdate;

    public CommonMessage(long userID, long convoID, String message, long mdate) {
        this.userID = userID;
        this.convoID = convoID;
        this.message = message;
        this.mdate = mdate;
    }

    public void setMessageID(long messageID) {
        this.messageID = messageID;
    }

    public long getMessageID() {
        return messageID;
    }

    public String getMessage() {
        return message;
    }

    public long getMdate() {
        return mdate;
    }

    public long getUserID() {
        return userID;
    }

    public long getConvoID() {
        return convoID;
    }
}
