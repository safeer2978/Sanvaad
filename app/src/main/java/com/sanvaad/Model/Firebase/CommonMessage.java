package com.sanvaad.Model.Firebase;

import java.io.Serializable;

public class CommonMessage implements Serializable {
    private long messageID;

    private String message;

    private long userID;

    private long mdate;

    public CommonMessage(){


    }

    public CommonMessage(long messageID, String message, long userID, long mdate) {
        this.messageID = messageID;
        this.message = message;
        this.userID = userID;
        this.mdate = mdate;
    }

    public long getMessageID() {
        return messageID;
    }

    public String getMessage() {
        return message;
    }

    public long getUserID() {
        return userID;
    }

    public long getMdate() {
        return mdate;
    }
}
