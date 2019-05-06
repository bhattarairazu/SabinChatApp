package com.example.manjil.sabinchat.Room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;


@Dao
public interface LastMessageDao {
    @Query("Select * from latestmessage where own_userids=:ownids")
    List<Latestmessage> getMessageList(int ownids);
    @Query("Select * from latestmessage where user_ids = :ids")
    List<Latestmessage> getting_single_username(int ids);
    @Query("Select username from latestmessage where user_ids=:ids and own_userids=:ownids")
    String getsingleusername(int ids,int ownids);
    //getting single group ids
    @Query("Select username from latestmessage where group_ids=:gids")
    String groupname(int gids);

    @Insert
    void inserLastmessage(Latestmessage messages);

    @Update
    void updateLastmessage(Latestmessage message);

    @Delete
    void deleteLastmessage(Latestmessage message);


}
