package com.sanvaad.Model.Firebase;

import java.io.Serializable;

public class Feedback implements Serializable {
    private long fID;

    private long userID;

    private String comment;

    private long fdate;

    public Feedback(){

    }

    public Feedback(long fID, long userID, String comment, long fdate) {
        this.fID = fID;
        this.userID = userID;
        this.comment = comment;
        this.fdate = fdate;
    }

    public long getfID() {
        return fID;
    }

    public long getUserID() {
        return userID;
    }

    public String getComment() {
        return comment;
    }

    public long getFdate() {
        return fdate;
    }
}
