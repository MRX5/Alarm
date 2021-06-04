package com.example.alarm.Service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleService;
import androidx.lifecycle.Observer;

import com.example.alarm.data.Alarm;
import com.example.alarm.data.AlarmRepository;

public class StopAlarmService extends LifecycleService {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int alarmID=intent.getIntExtra(AlarmService.ALARM_ID,0);
        AlarmRepository repository=new AlarmRepository(getApplication());
        repository.getAlarmById(alarmID).observe(this, alarm -> {
            alarm.setActive(false);
            repository.updateAlarm(alarm);
        });
        return super.onStartCommand(intent, flags, startId);
    }

}