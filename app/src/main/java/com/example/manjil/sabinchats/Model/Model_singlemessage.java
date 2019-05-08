package com.example.manjil.sabinchats.Model;

public class Model_singlemessage {
    private int from_id;
    private int to_id;
    private String messages;
    private String time;

    public Model_singlemessage() {
    }

    public Model_singlemessage(int from_id, int to_id, String messages, String time) {
        this.from_id = from_id;
        this.to_id = to_id;
        this.messages = messages;
        this.time = time;
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

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
