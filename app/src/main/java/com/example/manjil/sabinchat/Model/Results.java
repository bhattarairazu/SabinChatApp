package com.example.manjil.sabinchat.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Results {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("from_id")
    @Expose
    private int from_id;
    @SerializedName("to_id")
    @Expose
    private int to_id;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("time")
    @Expose
    private String time;

    public int getFrom_id() {
        return from_id;
    }

    public int getTo_id() {
        return to_id;
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
