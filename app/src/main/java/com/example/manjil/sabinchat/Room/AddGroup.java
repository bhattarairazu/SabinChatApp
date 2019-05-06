package com.example.manjil.sabinchat.Room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName="grouplist")
public class AddGroup {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name="groupname")
    private String username;

    @ColumnInfo(name = "ids")
    private int ids;
}
