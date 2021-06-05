package com.example.alarm.AlarmsList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.alarm.CreateAlarm.CreateAlarmActivity;
import com.example.alarm.R;
import com.example.alarm.data.Alarm;

public class MainActivity extends AppCompatActivity implements AlarmsListAdapter.onItemClickListener,AlarmsListAdapter.onToggleAlarmListener{
    RecyclerView recyclerView;
    AlarmsListAdapter adapter;
    AlarmListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupRecycler();

        viewModel= ViewModelProviders.of(this).get(AlarmListViewModel.class);
        viewModel.getAlarms().observe(this,alarms -> adapter.setAlarms(alarms));
    }

    private void setupRecycler() {
        recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recyclerView.setHasFixedSize(true);
        adapter=new AlarmsListAdapter(this,this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId=item.getItemId();
        if(itemId==R.id.action_add)
        {
            Intent intent=new Intent(MainActivity.this, CreateAlarmActivity.class);
            intent.putExtra(CreateAlarmActivity.ALARM_STATE,CreateAlarmActivity.ADD_STATE);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(int alarmId) {
        Intent intent=new Intent(MainActivity.this,CreateAlarmActivity.class);
        intent.putExtra(CreateAlarmActivity.ALARM_STATE,CreateAlarmActivity.EDIT_STATE);
        intent.putExtra(CreateAlarmActivity.ALARM_ID_KEY,alarmId);
        startActivity(intent);
    }

    @Override
    public void onToggle(Alarm alarm) {
        if(alarm.isActive())
        {
            alarm.Schedule(this);
        }
        else
        {
            alarm.cancelAlarm(this);

        }
        viewModel.updateAlarm(alarm);
    }
}