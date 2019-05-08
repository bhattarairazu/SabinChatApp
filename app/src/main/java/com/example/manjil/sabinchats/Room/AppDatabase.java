package com.example.manjil.sabinchats.Room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = Latestmessage.class,exportSchema = false,version = 3)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "db_latestmessage";
    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
    public abstract LastMessageDao lmessagedao();
}
