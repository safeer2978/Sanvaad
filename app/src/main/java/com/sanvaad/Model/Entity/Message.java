package com.sanvaad.Model.Entity;

public class Message {
    private String message;

    private long ID;

    private long messageDate;

    private int position;

    public Message(String message, long ID, long messageDate, int position) {
        this.message = message;
        this.ID = ID;
        this.messageDate = messageDate;
        this.position = position;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public long getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(long messageDate) {
        this.messageDate = messageDate;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
