package com.example.alarm.data;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AlarmRepository {
    private AlarmDatabase db;
    private MutableLiveData<List<Alarm>>alarms=new MutableLiveData<>();
    private MutableLiveData<Alarm>alarm=new MutableLiveData<>();
    CompositeDisposable disposable=new CompositeDisposable();

    public  AlarmRepository(Application application)
    {
        db=AlarmDatabase.getInstance(application);

    }
    public LiveData<List<Alarm>>getAlarms()
    {
        Flowable<List<Alarm>>flowable=db.alarmDao().getAlarms()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        flowable.subscribe(alarms1 ->alarms.setValue(alarms1));
        return alarms;
    }
    public void createAlarm(Alarm alarm)
    {
        db.alarmDao().createAlarm(alarm)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    @SuppressLint("CheckResult")
    public void updateAlarm(Alarm alarm)
    {
        db.alarmDao().updateAlarm(alarm)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(()-> Log.d("aa", "updateAlarm "),e-> Log.d("aaa",  e.toString()));
    }
    public void updateAlarmState(int id,boolean state)
    {
        db.alarmDao().updateAlarmState(id,state)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }
                    @Override
                    public void onComplete() {
                        Log.d("aaa", "onComplete: ");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    @SuppressLint("CheckResult")
    public void deleteAlarm(Alarm alarm)
    {
        db.alarmDao().deleteAlarm(alarm)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(()-> Log.d("aa", "deleteAlarm: "));
    }

    @SuppressLint("CheckResult")
    public LiveData<Alarm> getAlarmById(int id) {
        db.alarmDao().getAlarm(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(alarm1 -> alarm.setValue(alarm1));
        return alarm;
    }

}
