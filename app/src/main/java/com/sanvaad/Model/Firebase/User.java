package com.sanvaad.Model.Firebase;

import java.io.Serializable;

public class User implements Serializable {
    private long userID;

    private String name;

    private int age;

    private String email;

    private String status;

    private long phone;

    public User(){

    }

    public User(long userID, String name, int age, String email, String status, long phone) {
        this.userID= userID;
        this.name = name;
        this.age = age;
        this.email = email;
        this.status = status;
        this.phone = phone;
    }

    public void setUser(long userID) {
        this.userID = userID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getStatus() {
        return status;
    }

    public long getPhone() {
        return phone;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }
}
