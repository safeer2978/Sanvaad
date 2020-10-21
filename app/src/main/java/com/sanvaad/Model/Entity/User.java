package com.sanvaad.Model.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class User {
    @PrimaryKey(autoGenerate = true)
    private long userID;

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setPhoneNo(long phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String name;

    private String email;

    private int age;

    private long phoneNo;

    private String status;

    public User(){

    }

    public User(String name, String email, int age, long phoneNo, String status) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.phoneNo = phoneNo;
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

    public long getPhoneNo() {
        return phoneNo;
    }

    public String getStatus() {
        return status;
    }
}
