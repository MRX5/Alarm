package com.example.alarm.Broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.example.alarm.Service.AlarmService;
import com.example.alarm.Service.StopAlarmService;
import com.example.alarm.data.Alarm;

public class AlarmBroadcastReceiver extends BroadcastReceiver {
    public static final String SATURDAY = "SATURDAY";
    public static final String SUNDAY = "SUNDAY";
    public static final String MONDAY = "MONDAY";
    public static final String TUESDAY = "TUESDAY";
    public static final String WEDNESDAY = "WEDNESDAY";
    public static final String THURSDAY = "THURSDAY";
    public static final String FRIDAY = "FRIDAY";
    public static final String REPEAT = "REPEAT";
    public static final String TITLE = "TITLE";
    public static final String ALARM_ID="ALARM_ID";

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction()!=null &&intent.getAction().equals("CANCEL"))
        {
            Intent intent1=new Intent(context,AlarmService.class);
            context.stopService(intent1);
            int alarmID=intent.getIntExtra(ALARM_ID,0);
            Intent stopIntent=new Intent(context, StopAlarmService.class);
            stopIntent.putExtra(AlarmService.ALARM_ID,alarmID);
            context.startService(stopIntent);
        }
        else {
            Intent intentService = new Intent(context, AlarmService.class);
            intentService.putExtra(AlarmService.TITLE, intent.getStringExtra(TITLE));
            intentService.putExtra(ALARM_ID, intent.getIntExtra(ALARM_ID, 0));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intentService);
            } else {
                context.startService(intentService);
            }
        }
    }
}

