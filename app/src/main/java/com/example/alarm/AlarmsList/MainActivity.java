package com.example.alarm.AlarmsList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.alarm.CreateAlarm.CreateAlarmActivity;
import com.example.alarm.R;
import com.example.alarm.data.Alarm;
import com.example.alarm.databinding.ActivityMainBinding;
import com.google.android.material.appbar.MaterialToolbar;

public class MainActivity extends AppCompatActivity implements AlarmsListAdapter.onItemClickListener, AlarmsListAdapter.onToggleAlarmListener {

    AlarmsListAdapter adapter;
    AlarmListViewModel viewModel;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar((MaterialToolbar) binding.customToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        setupRecycler();
        loadAlarms();

    }

    private void loadAlarms() {
        viewModel = ViewModelProviders.of(this).get(AlarmListViewModel.class);
        viewModel.getAlarms().observe(this, alarms -> {
            if (alarms.size() == 0) {
                binding.emptyView.setVisibility(View.VISIBLE);
            } else {
                binding.emptyView.setVisibility(View.GONE);
            }
            adapter.setAlarms(alarms);
        });
    }

    private void setupRecycler() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        binding.recyclerView.setHasFixedSize(true);
        adapter = new AlarmsListAdapter(this, this);
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_add) {
            Intent intent = new Intent(MainActivity.this, CreateAlarmActivity.class);
            intent.putExtra(CreateAlarmActivity.ALARM_STATE, CreateAlarmActivity.ADD_STATE);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(int alarmId) {
        Intent intent = new Intent(MainActivity.this, CreateAlarmActivity.class);
        intent.putExtra(CreateAlarmActivity.ALARM_STATE, CreateAlarmActivity.EDIT_STATE);
        intent.putExtra(CreateAlarmActivity.ALARM_ID_KEY, alarmId);
        startActivity(intent);
    }

    @Override
    public void onToggle(Alarm alarm) {
        if (alarm.isActive()) {
            alarm.Schedule(this);
        } else {
            alarm.cancelAlarm(this);

        }
        viewModel.updateAlarm(alarm);
    }

    @Override
    public void onLongClick(Alarm alarm) {
        showDeleteDialog(alarm);
    }

    private void showDeleteDialog(Alarm alarm) {
        ViewGroup viewGroup = findViewById(android.R.id.content);

        View dialogView = LayoutInflater.from(this).inflate(R.layout.delete_dialog, viewGroup, false);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(dialogView);

        AlertDialog alertDialog = builder.create();

        dialogView.findViewById(R.id.delete_btn).setOnClickListener(v -> {
            deleteAlarm(alarm);
            alertDialog.dismiss();
        });

        alertDialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.dialog_shape));
        alertDialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(alertDialog.getWindow().getAttributes());
        lp.width = (int) getResources().getDisplayMetrics().density * 150;
        alertDialog.getWindow().setAttributes(lp);
    }

    private void deleteAlarm(Alarm alarm) {
        if (alarm.isActive()) {
            alarm.cancelAlarm(this);
        }
        viewModel.deleteAlarm(alarm);
    }
}