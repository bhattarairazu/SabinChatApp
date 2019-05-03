package com.example.manjil.sabinchat.Model.stories;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Results_stories {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("user_id")
    @Expose
    private int user_id;
    @SerializedName("created_at")
    @Expose
    private String created_at;

    public Results_stories() {
    }

    public Results_stories(int id, String image, String text, int user_id, String created_at) {
        this.id = id;
        this.image = image;
        this.text = text;
        this.user_id = user_id;
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
