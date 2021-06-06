package com.example.alarm.Broadcast;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.example.alarm.Service.AlarmService;

import java.util.Calendar;

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
    public static final String ALARM_ID = "ALARM_ID";

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction() != null && intent.getAction().equals(AlarmService.ACTION_CANCEL_ALARM)) {

            Intent stopIntent = new Intent(context, AlarmService.class);
            int alarmID = intent.getIntExtra(ALARM_ID, 0);
            stopIntent.putExtra(AlarmService.ALARM_ID, alarmID);
            stopIntent.putExtra(AlarmService.CANCEL_ALARM, true);
            context.startService(stopIntent);

        } else {
            if (!intent.getBooleanExtra(REPEAT, false)) {
                startAlarmService(context, intent);
            } else if (checkToday(intent)) {
                startAlarmService(context, intent);
            }
        }
    }

    private void startAlarmService(Context context, Intent intent) {
        Intent startService = new Intent(context, AlarmService.class);
        startService.putExtra(AlarmService.TITLE, intent.getStringExtra(TITLE));
        startService.putExtra(ALARM_ID, intent.getIntExtra(ALARM_ID, 0));
        startService.putExtra(AlarmService.CANCEL_ALARM, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(startService);
        } else {
            context.startService(startService);
        }
    }

    private boolean checkToday(Intent intent) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int today = calendar.get(Calendar.DAY_OF_WEEK);

        switch (today) {
            case Calendar.MONDAY:
                if (intent.getBooleanExtra(MONDAY, false))
                    return true;
                return false;
            case Calendar.TUESDAY:
                if (intent.getBooleanExtra(TUESDAY, false))
                    return true;
                return false;
            case Calendar.WEDNESDAY:
                if (intent.getBooleanExtra(WEDNESDAY, false))
                    return true;
                return false;
            case Calendar.THURSDAY:
                if (intent.getBooleanExtra(THURSDAY, false))
                    return true;
                return false;
            case Calendar.FRIDAY:
                if (intent.getBooleanExtra(FRIDAY, false))
                    return true;
                return false;
            case Calendar.SATURDAY:
                if (intent.getBooleanExtra(SATURDAY, false))
                    return true;
                return false;
            case Calendar.SUNDAY:
                if (intent.getBooleanExtra(SUNDAY, false))
                    return true;
                return false;
        }
        return false;
    }
}

