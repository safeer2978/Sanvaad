package com.sanvaad.Model.Entity;
import java.util.Date;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "feedback")
public class Feedback {
    @PrimaryKey(autoGenerate = true)
    private long fid;

    private long userID;

    private String comment;

    private Date fdate;

    public Feedback(String comment, Date fdate) {
        this.comment = comment;
        this.fdate = fdate;
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

    public Date getFdate() {
        return fdate;
    }
}
