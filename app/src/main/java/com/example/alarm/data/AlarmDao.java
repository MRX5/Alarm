package com.example.alarm.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public interface AlarmDao {
    @Insert
    Completable createAlarm(Alarm alarm);

    @Query("select * from alarms order by createTime asc")
    Flowable<List<Alarm>>getAlarms();

    @Query("select * from alarms where alarmId=:id")
    Single<Alarm>getAlarm(int id);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    Completable updateAlarm(Alarm alarm);

    @Query("update alarms set active=:state where alarmId=:id")
    Completable updateAlarmState(int id,boolean state);

    @Delete
    Completable deleteAlarm(Alarm alarm);

}
