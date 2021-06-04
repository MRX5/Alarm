package com.example.alarm.AlarmsList;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.alarm.data.Alarm;
import com.example.alarm.data.AlarmRepository;

import java.util.List;

public class AlarmListViewModel extends AndroidViewModel {
    private AlarmRepository repository;
    private LiveData<List<Alarm>> alarms;
    public AlarmListViewModel(@NonNull Application application) {
        super(application);
        repository=new AlarmRepository(application);
        alarms=repository.getAlarms();
    }
    public LiveData<List<Alarm>> getAlarms()
    {
        return alarms;
    }
    public void deleteAlarm(Alarm alarm)
    {
        repository.deleteAlarm(alarm);
    }
    public void updateAlarm(Alarm alarm){repository.updateAlarm(alarm);}
}
