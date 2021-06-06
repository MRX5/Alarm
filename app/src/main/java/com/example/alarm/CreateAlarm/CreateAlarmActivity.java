package com.example.alarm.CreateAlarm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.alarm.AlarmsList.MainActivity;
import com.example.alarm.R;
import com.example.alarm.data.Alarm;
import com.example.alarm.data.AlarmRepository;
import com.example.alarm.databinding.ActivityEditAlarmBinding;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.timepicker.MaterialTimePicker;

import java.util.Random;

public class CreateAlarmActivity extends AppCompatActivity {

    ActivityEditAlarmBinding binding;
    public static String ALARM_ID_KEY="alarm_id_key";
    public static String ALARM_STATE="alarm_state";
    public static String ADD_STATE="add";
    public static String EDIT_STATE="edit";
    private int default_id=-1;
    private MaterialToolbar toolbar;
    private int alarmId;
    private CreateAlarmViewModel viewModel;
    private CreateAlarmViewModelFactory factory;

    private long createdTime=System.currentTimeMillis();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_edit_alarm);

        setupToolbar();

        Intent intent=getIntent();
        binding.repeatCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked)
            {
                binding.repeatField.setVisibility(View.VISIBLE);
            }
            else
            {
                binding.repeatField.setVisibility(View.GONE);
            }
        });

        if(intent.getStringExtra(ALARM_STATE).equals(EDIT_STATE))
        {
            alarmId=intent.getIntExtra(ALARM_ID_KEY,default_id);
            factory= new CreateAlarmViewModelFactory(getApplication(),alarmId);
            viewModel= ViewModelProviders.of(this,factory).get(CreateAlarmViewModel.class);
            viewModel.getAlarm().observe(this, alarm ->  populateUI(alarm));

        }
        else {
            alarmId=default_id;
            factory= new CreateAlarmViewModelFactory(getApplication(),alarmId);
            viewModel= ViewModelProviders.of(this,factory).get(CreateAlarmViewModel.class);
        }

    }

    private void setupToolbar() {
        toolbar=findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(getDrawable(R.drawable.icon_back));
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem settingsMenuItem = menu.findItem(R.id.action_save);
        SpannableString s = new SpannableString(settingsMenuItem.getTitle());
        s.setSpan(new ForegroundColorSpan(getColor(R.color.white)), 0, s.length(), 0);
        settingsMenuItem.setTitle(s);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId=item.getItemId();
        if(itemId==R.id.action_save)
        {
            Alarm alarm= getAlarm();
            if(alarmId==default_id)   // create alarm
            {
                viewModel.createAlarm(alarm);
            }else{                              // edit alarm
                alarm.setAlarmId(alarmId);
                viewModel.updateAlarm(alarm);
            }
            alarm.Schedule(CreateAlarmActivity.this);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private Alarm getAlarm() {
        int alarmId = new Random().nextInt(Integer.MAX_VALUE);

        Alarm alarm=new Alarm(
                alarmId,
                binding.alarmNameEditTxt.getText().toString(),
                binding.timePicker.getHour(),
                binding.timePicker.getMinute(),
                createdTime,
                true,
                binding.repeatCheckbox.isChecked(),
                binding.saturdayCheckbox.isChecked(),
                binding.sundayCheckbox.isChecked(),
                binding.mondayCheckbox.isChecked(),
                binding.tuesdayCheckbox.isChecked(),
                binding.wednesdayCheckbox.isChecked(),
                binding.thursdayCheckbox.isChecked(),
                binding.fridayCheckbox.isChecked());
        return alarm;
    }

    private void populateUI(Alarm alarm)
    {
        binding.timePicker.setHour(alarm.getHour());
        binding.timePicker.setMinute(alarm.getMinute());
        binding.alarmNameEditTxt.setText(alarm.getTitle());
        binding.repeatCheckbox.setChecked(alarm.isRepeat());
        binding.saturdayCheckbox.setChecked(alarm.isSaturday());
        binding.sundayCheckbox.setChecked(alarm.isSunday());
        binding.mondayCheckbox.setChecked(alarm.isMonday());
        binding.tuesdayCheckbox.setChecked(alarm.isTuesday());
        binding.wednesdayCheckbox.setChecked(alarm.isWednesday());
        binding.thursdayCheckbox.setChecked(alarm.isThursday());
        binding.fridayCheckbox.setChecked(alarm.isFriday());
        createdTime=alarm.getCreateTime();
    }

}