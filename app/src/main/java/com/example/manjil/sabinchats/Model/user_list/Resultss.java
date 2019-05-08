package com.example.manjil.sabinchats.Model.user_list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Resultss {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("picture")
    @Expose
    private String picture;
    @SerializedName("status")
    @Expose
    private int status;



    public Resultss(int id, String username, String name, String picture, int status) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.picture = picture;
        this.status = status;
    }

    public Resultss() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
