package com.sanvaad.Model.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "feedback")
public class Feedback {
    @PrimaryKey(autoGenerate = true)
    private long fid;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    String userID;

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setFdate(long fdate) {
        this.fdate = fdate;
    }


    private String comment;

    private long fdate;

    public Feedback(String userID, String comment, long fdate) {
        this.comment = comment;
        this.fdate = fdate;
        this.userID = userID;
    }

    public void setFid(long fid) {
        this.fid = fid;
    }

    public long getFid() {
        return fid;
    }


    public String getComment() {
        return comment;
    }

    public long getFdate() {
        return fdate;
    }
}
