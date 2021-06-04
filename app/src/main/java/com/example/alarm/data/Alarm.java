package com.example.alarm.data;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.alarm.Broadcast.AlarmBroadcastReceiver;
import java.util.Calendar;
@Entity(tableName = "alarms")
public class Alarm {
    @PrimaryKey(autoGenerate = true)
    private int alarmId;
    private String title;
    private int hour, minute;
    private boolean repeat;
    @ColumnInfo(name = "active",defaultValue = "true")
    private boolean active;
    private long createTime;
    private boolean saturday, sunday, monday, tuesday, wednesday, thursday, friday;


    public Alarm(int alarmId, String title, int hour, int minute, long createTime,boolean active, boolean repeat, boolean saturday, boolean sunday, boolean monday, boolean tuesday, boolean wednesday, boolean thursday, boolean friday) {
        this.alarmId = alarmId;
        this.title = title;
        this.hour = hour;
        this.minute = minute;
        this.createTime=createTime;
        this.active=active;
        this.repeat = repeat;
        this.saturday = saturday;
        this.sunday = sunday;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
    }


/*    @Ignore
    public Alarm(String title, int hour, int minute, long createTime,boolean active, boolean repeat, boolean saturday, boolean sunday, boolean monday, boolean tuesday, boolean wednesday, boolean thursday, boolean friday) {
        this.title = title;
        this.hour = hour;
        this.minute = minute;
        this.createTime=createTime;
        this.active=active;
        this.repeat = repeat;
        this.saturday = saturday;
        this.sunday = sunday;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
    }*/

    public int getAlarmId() {
        return alarmId;
    }


    public void setAlarmId(int alarmId) {
        this.alarmId = alarmId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }


    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public boolean isSaturday() {
        return saturday;
    }

    public void setSaturday(boolean saturday) {
        this.saturday = saturday;
    }

    public boolean isSunday() {
        return sunday;
    }

    public void setSunday(boolean sunday) {
        this.sunday = sunday;
    }

    public boolean isMonday() {
        return monday;
    }

    public void setMonday(boolean monday) {
        this.monday = monday;
    }

    public boolean isTuesday() {
        return tuesday;
    }

    public void setTuesday(boolean tuesday) {
        this.tuesday = tuesday;
    }

    public boolean isWednesday() {
        return wednesday;
    }

    public void setWednesday(boolean wednesday) {
        this.wednesday = wednesday;
    }

    public boolean isThursday() {
        return thursday;
    }

    public void setThursday(boolean thursday) {
        this.thursday = thursday;
    }

    public boolean isFriday() {
        return friday;
    }

    public void setFriday(boolean friday) {
        this.friday = friday;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getDays() {
        String ring = "";
        if (!repeat) {
            ring = "Ring once";
        } else {
            if (saturday) {
                ring += "Sat ";
            }
            if (sunday) {
                ring += "Sun ";
            }
            if (monday) {
                ring += "Mon ";
            }
            if (tuesday) {
                ring += "Tue ";
            }
            if (wednesday) {
                ring += "Wed ";
            }
            if (thursday) {
                ring += "Thu ";
            }
            if (friday) {
                ring += "Fri ";
            }
        }
        return ring;
    }

    public void Schedule(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        intent.putExtra(AlarmBroadcastReceiver.ALARM_ID,alarmId);
        intent.putExtra(AlarmBroadcastReceiver.SATURDAY, saturday);
        intent.putExtra(AlarmBroadcastReceiver.SUNDAY, sunday);
        intent.putExtra(AlarmBroadcastReceiver.MONDAY, monday);
        intent.putExtra(AlarmBroadcastReceiver.TUESDAY, tuesday);
        intent.putExtra(AlarmBroadcastReceiver.WEDNESDAY, wednesday);
        intent.putExtra(AlarmBroadcastReceiver.THURSDAY, thursday);
        intent.putExtra(AlarmBroadcastReceiver.FRIDAY, friday);
        intent.putExtra(AlarmBroadcastReceiver.REPEAT, repeat);
        intent.putExtra(AlarmBroadcastReceiver.TITLE,title);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        String v=""+calendar.getTimeInMillis();

        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        }

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(alarmPendingIntent);
    }
}
