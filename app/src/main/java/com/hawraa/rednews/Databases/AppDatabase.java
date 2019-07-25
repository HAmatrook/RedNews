package com.hawraa.rednews.Databases;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.hawraa.rednews.Models.NewsModel;

@android.arch.persistence.room.Database(entities = NewsModel.class, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase{

    public abstract DataAO NewsDAO();
    private static AppDatabase database;

    public static AppDatabase getDatabase (Context context) {
        if (database == null){
            database = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "Local_Database")
                    .fallbackToDestructiveMigration()
                 //  .allowMainThreadQueries()
                    .build();
        }
        return database;
    }
}
