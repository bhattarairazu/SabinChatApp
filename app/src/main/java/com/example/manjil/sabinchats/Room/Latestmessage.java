package com.example.manjil.sabinchats.Room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "latestmessage")
public class Latestmessage {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name="username")
    private String username;
    @ColumnInfo(name="lastmessage")
    private String lastmessage;
    @ColumnInfo(name = "user_ids")
    private int user_ids;
    @ColumnInfo(name="group_ids")
    private int group_ids;
    @ColumnInfo(name="own_userids")
    private int own_userids;

    public Latestmessage(String username, String lastmessage, int user_ids, int group_ids, int own_userids) {
        this.username = username;
        this.lastmessage = lastmessage;
        this.user_ids = user_ids;
        this.group_ids = group_ids;
        this.own_userids = own_userids;
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

    public String getLastmessage() {
        return lastmessage;
    }

    public void setLastmessage(String lastmessage) {
        this.lastmessage = lastmessage;
    }

    public int getUser_ids() {
        return user_ids;
    }

    public void setUser_ids(int user_ids) {
        this.user_ids = user_ids;
    }

    public int getGroup_ids() {
        return group_ids;
    }

    public void setGroup_ids(int group_ids) {
        this.group_ids = group_ids;
    }

    public int getOwn_userids() {
        return own_userids;
    }

    public void setOwn_userids(int own_userids) {
        this.own_userids = own_userids;
    }
}
