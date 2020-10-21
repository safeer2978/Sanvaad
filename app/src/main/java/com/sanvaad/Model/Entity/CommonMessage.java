
package com.sanvaad.Model.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;


@Entity(tableName = "commonMessage")
public class CommonMessage {
    @PrimaryKey(autoGenerate = true)
    private long messageID;

    public void setUserID(long userID) {
        this.userID = userID;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public void setMdate(long mdate) {
        this.mdate = mdate;
    }

    private long userID;



    private String message;

    private long mdate;

    public CommonMessage(String message, User user) {
        this.userID = user.getUserID();
        this.message = message;
        this.mdate = Calendar.getInstance().getTimeInMillis();
    }

    public CommonMessage(){

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
}