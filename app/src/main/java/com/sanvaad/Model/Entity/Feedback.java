package com.sanvaad.Model.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "feedback")
public class Feedback {
    @PrimaryKey(autoGenerate = true)
    private long fid;

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setFdate(long fdate) {
        this.fdate = fdate;
    }

    private long userID;

    private String comment;

    private long fdate;

    public Feedback(long userID, String comment, long fdate) {
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
