package com.example.alarm.data;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = {Alarm.class},version = 2,exportSchema = false)
public abstract class AlarmDatabase extends RoomDatabase {
    private static AlarmDatabase mInstance;

    public static synchronized AlarmDatabase getInstance(Application app)
    {
        if(mInstance==null)
        {
            mInstance= Room.databaseBuilder(app,AlarmDatabase.class,"myDatabase")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return mInstance;
    }

    public abstract AlarmDao alarmDao();

}
