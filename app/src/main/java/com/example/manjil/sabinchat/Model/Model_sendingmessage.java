package com.example.manjil.sabinchat.Model;

public class Model_sendingmessage {
    private String message;
    private int from_id;
    private int to_id;
    private String time;

    public Model_sendingmessage(String message, int from_id, int to_id, String time) {
        this.message = message;
        this.from_id = from_id;
        this.to_id = to_id;
        this.time = time;
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
