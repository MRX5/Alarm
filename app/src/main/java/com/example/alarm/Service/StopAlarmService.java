package com.example.alarm.Service;

import android.app.IntentService;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.JobIntentService;
import androidx.lifecycle.LifecycleService;
import androidx.lifecycle.Observer;

import com.example.alarm.data.Alarm;
import com.example.alarm.data.AlarmRepository;

public class StopAlarmService extends JobIntentService {
    static final int JOB_ID = 1000;


    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, StopAlarmService.class, JOB_ID, work);
    }
    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        int alarmID=intent.getIntExtra(AlarmService.ALARM_ID,0);
        AlarmRepository repository=new AlarmRepository(getApplication());
        repository.updateAlarmState(alarmID,false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}