package com.example.manjil.sabinchats.Model;

public class Model_Message_Singleitems {
    private String send_to_name;
    private String send_from_name;
    private String send_messages;
    private int send_to_ids;
    private int send_from_ids;
    private boolean isFromHostUser;

    public Model_Message_Singleitems() {
    }

    public Model_Message_Singleitems(String send_to_name, String send_from_name, String send_messages, int send_to_ids, int send_from_ids, boolean isFromHostUser) {
        this.send_to_name = send_to_name;
        this.send_from_name = send_from_name;
        this.send_messages = send_messages;
        this.send_to_ids = send_to_ids;
        this.send_from_ids = send_from_ids;
        this.isFromHostUser = isFromHostUser;
    }

    public String getSend_to_name() {
        return send_to_name;
    }

    public void setSend_to_name(String send_to_name) {
        this.send_to_name = send_to_name;
    }

    public String getGet_from_name() {
        return send_from_name;
    }

    public void setGet_from_name(String get_from_name) {
        this.send_from_name = get_from_name;
    }

    public int getSend_to_ids() {
        return send_to_ids;
    }

    public void setSend_to_ids(int send_to_ids) {
        this.send_to_ids = send_to_ids;
    }

    public int getGet_from_ids() {
        return send_from_ids;
    }

    public void setGet_from_ids(int get_from_ids) {
        this.send_from_ids = get_from_ids;
    }

    public String getSend_messages() {
        return send_messages;
    }

    public void setSend_messages(String send_messages) {
        this.send_messages = send_messages;
    }

    public boolean isFromHostUser() {
        return isFromHostUser;
    }

    public void setFromHostUser(boolean fromHostUser) {
        isFromHostUser = fromHostUser;
    }
}
