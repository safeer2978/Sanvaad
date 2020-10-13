package com.sanvaad.Model.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "contact")
public class Contact {
    @PrimaryKey(autoGenerate = true)
    private long conID;

    private String name;

    private String imglink;

    public Contact(String name, String imglink) {
        this.name = name;
        this.imglink = imglink;
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
