package com.sanvaad.Model.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.sanvaad.Model.Constants;

@Entity(tableName = "contact")
public class Contact {
    @PrimaryKey(autoGenerate = true)
    private long contactID;

    private String name;

    public long getContactID() {
        return contactID;
    }

    public void setContactID(long contactID) {
        this.contactID = contactID;
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

    public Contact(String name, long id){
        this.name = name;
        this.imglink= Constants.DUMMY_CONTACT_IMAGE_LINK;
this.contactID =id;
    }

    public Contact(){}

    public void setId(long conID) {
        this.contactID = conID;
    }

    public long getId() {
        return contactID;
    }

    public String getName() {
        return name;
    }

    public String getImglink() {
        return imglink;
    }
}
