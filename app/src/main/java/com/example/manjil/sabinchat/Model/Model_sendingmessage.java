package com.example.manjil.sabinchat.Model;

import android.net.Uri;

import java.net.URI;

public class Model_sendingmessage {
    private int id;
    private String message;
    private int from_id;
    private int to_id;
    private String time;
    private Uri muri;
    private String picture;


    public Model_sendingmessage() {
    }

    public Model_sendingmessage(int id, String message, int from_id, int to_id, String time, Uri muri) {
        this.id = id;
        this.message = message;
        this.from_id = from_id;
        this.to_id = to_id;
        this.time = time;
        this.muri = muri;
    }

    public Model_sendingmessage(int id, String message, int from_id, int to_id, String time, Uri muri, String picture) {
        this.id = id;
        this.message = message;
        this.from_id = from_id;
        this.to_id = to_id;
        this.time = time;
        this.muri = muri;
        this.picture = picture;
    }

    public Model_sendingmessage(String message, int from_id, int to_id, String time) {
        this.message = message;
        this.from_id = from_id;
        this.to_id = to_id;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Uri getMuri() {
        return muri;
    }

    public void setMuri(Uri muri) {
        this.muri = muri;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getFrom_id() {
        return from_id;
    }

    public void setFrom_id(int from_id) {
        this.from_id = from_id;
    }

    public int getTo_id() {
        return to_id;
    }

    public void setTo_id(int to_id) {
        this.to_id = to_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
