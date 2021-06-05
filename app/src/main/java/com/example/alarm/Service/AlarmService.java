package com.example.alarm.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.alarm.Broadcast.AlarmBroadcastReceiver;
import com.example.alarm.R;

public class AlarmService extends Service {
    private String CHANNEL_ID="channel_id";
    public static String ALARM_ID="ALARM_ID";
    public static String TITLE="TITLE";
    private int alarmID;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        alarmID=intent.getIntExtra(ALARM_ID,0);
        Notification notification=getNotification(intent.getStringExtra(TITLE));
        startForeground(alarmID,notification);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private Notification getNotification(String title)
    {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "my_channel", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("description");
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        Intent intent=new Intent(this, AlarmBroadcastReceiver.class);
        intent.setAction("CANCEL");
        intent.putExtra(ALARM_ID,alarmID);
        PendingIntent cancelPendingIntent=PendingIntent.getBroadcast(this,alarmID,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_access_alarms_24)
                .setContentTitle("Alarm")
                .setContentText(title)
                .setAutoCancel(true)
                .addAction(R.drawable.ic_baseline_access_alarms_24,"Cancel",cancelPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        return builder.build();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
