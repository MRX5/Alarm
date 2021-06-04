package com.example.alarm.CreateAlarm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

public class CreateAlarmViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private int alarmId;
    private Application app;
    CreateAlarmViewModelFactory(Application app,int alarmId)
    {
        this.app=app;
        this.alarmId=alarmId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T)new CreateAlarmViewModel(app,alarmId);
    }
}
