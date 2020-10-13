package com.sanvaad.Model.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "commonmessage")
public class CommonMessage {
    @PrimaryKey(autoGenerate = true)
    private long messageID;

    private String message;

    private long mdate;

    public CommonMessage(String message, long mdate) {
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
}
