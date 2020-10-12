package com.sanvaad.Model.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class User {
    @PrimaryKey(autoGenerate = true)
    private long userID;

    private String name;

    private String email;

    private int age;

    private long phoneno;

    private String status;

    public User(String name, String email, int age, long phoneno, String status) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.phoneno = phoneno;
        this.status = status;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public long getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public long getPhoneno() {
        return phoneno;
    }

    public String getStatus() {
        return status;
    }
}
