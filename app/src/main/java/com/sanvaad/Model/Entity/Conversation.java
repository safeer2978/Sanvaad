package com.sanvaad.Model.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "conversation")
public class Conversation {
    @PrimaryKey(autoGenerate = true)
    private long convoID;

    private Date cdate;

    public Conversation(Date cdate) {
        this.cdate = cdate;
    }

    public void setConvoID(long convoID) {
        this.convoID = convoID;
    }

    public long getConvoID() {
        return convoID;
    }

    public Date getCdate() {
        return cdate;
    }
}
