package com.example.manjil.sabinchat.Model;

/**
 * Created by User on 3/31/2019.
 */

public class PeopleListModel {
    private String name;
    private String images;
    private int user_id;

    public PeopleListModel() {
    }

    public PeopleListModel(String name, String images, int user_id) {
        this.name = name;
        this.images = images;
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
