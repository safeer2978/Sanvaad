package com.sanvaad.Model.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.sanvaad.Model.Constants;

@Entity(tableName = "contact")
public class Contact {
    @PrimaryKey(autoGenerate = true)
    private long conID;

    private String name;

    public long getConID() {
        return conID;
    }

    public void setConID(long conID) {
        this.conID = conID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImglink(String imglink) {
        this.imglink = imglink;
    }

    private String imglink;

    public Contact(String name, String imglink) {
        this.name = name;
        this.imglink = imglink;
    }

    public Contact(String name){
        this.name = name;
        this.imglink= Constants.DUMMY_CONTACT_IMAGE_LINK;

    }

    public void setId(long conID) {
        this.conID = conID;
    }

    public long getId() {
        return conID;
    }

    public String getName() {
        return name;
    }

    public String getImglink() {
        return imglink;
    }
}
