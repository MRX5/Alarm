package com.example.alarm.CreateAlarm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.alarm.data.Alarm;
import com.example.alarm.data.AlarmRepository;

public class CreateAlarmViewModel extends AndroidViewModel {

    private AlarmRepository repository;
    private LiveData<Alarm>alarm;
    public CreateAlarmViewModel(@NonNull Application application,int alarmId) {
        super(application);
        repository=new AlarmRepository(application);
        if(alarmId!=-1) {
            alarm = repository.getAlarmById(alarmId);
        }
    }
    public LiveData<Alarm> getAlarm()
    {
        return alarm;
    }
    public void updateAlarm(Alarm alarm){repository.updateAlarm(alarm);}
    public void createAlarm(Alarm alarm){repository.createAlarm(alarm);}

}
