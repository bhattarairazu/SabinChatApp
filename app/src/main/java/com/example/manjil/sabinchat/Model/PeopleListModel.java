package com.example.manjil.sabinchat.Model;

/**
 * Created by User on 3/31/2019.
 */

public class PeopleListModel {
    private String name;
    private String images;

    public PeopleListModel() {
    }

    public PeopleListModel(String name, String images) {
        this.name = name;
        this.images = images;
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
}
