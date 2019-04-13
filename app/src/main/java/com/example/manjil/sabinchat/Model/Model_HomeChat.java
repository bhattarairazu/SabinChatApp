package com.example.manjil.sabinchat.Model;

/**
 * Created by User on 3/31/2019.
 */

public class Model_HomeChat {
    private String name;
    private String last_message;
    private String date;
    private String image;
    private int no_of_unseenmessages;
    private boolean online_status;
    private int userids;



    public Model_HomeChat() {

    }

    public Model_HomeChat(String name, String last_message, String date, String image, int no_of_unseenmessages, boolean online_status) {
        this.name = name;
        this.last_message = last_message;
        this.date = date;
        this.image = image;
        this.no_of_unseenmessages = no_of_unseenmessages;
        this.online_status = online_status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_message() {
        return last_message;
    }

    public void setLast_message(String last_message) {
        this.last_message = last_message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getNo_of_unseenmessages() {
        return no_of_unseenmessages;
    }

    public void setNo_of_unseenmessages(int no_of_unseenmessages) {
        this.no_of_unseenmessages = no_of_unseenmessages;
    }

    public int getUserids() {
        return userids;
    }

    public void setUserids(int userids) {
        this.userids = userids;
    }

    public boolean isOnline_status() {
        return online_status;
    }

    public void setOnline_status(boolean online_status) {
        this.online_status = online_status;
    }
}
