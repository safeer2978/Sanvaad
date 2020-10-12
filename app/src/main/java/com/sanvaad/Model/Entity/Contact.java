package com.sanvaad.Model.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "contact")
public class Contact {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private String name;

    private String imglink;

    public Contact(String name, String imglink) {
        this.name = name;
        this.imglink = imglink;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImglink() {
        return imglink;
    }
}
