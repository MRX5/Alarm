package com.example.alarm.AlarmsList;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alarm.R;
import com.example.alarm.data.Alarm;
import com.example.alarm.data.AlarmDatabase;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.List;

public class AlarmsListAdapter extends RecyclerView.Adapter<AlarmsListAdapter.viewHolder> {
    List<Alarm>alarms;
    onItemClickListener itemClickListener;
    onToggleAlarmListener toggleAlarmListener;
    public AlarmsListAdapter(onItemClickListener itemClickListener,onToggleAlarmListener toggleAlarmListener)
    {
        this.itemClickListener=itemClickListener;
        this.toggleAlarmListener=toggleAlarmListener;
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        int hour =alarms.get(position).getHour();
        int minute =alarms.get(position).getMinute();
        String time=hour+":"+minute;
        boolean isActive=alarms.get(position).isActive();
        holder.format.setText(alarms.get(position).isActive()+"");
        holder.time.setText(time);
        holder.title.setText(alarms.get(position).getTitle());
        holder.ring_status.setText(alarms.get(position).getDays());
        holder.switchButton.setChecked(isActive);
    }

    public void setAlarms(List<Alarm> alarms) {
        this.alarms = alarms;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(alarms!=null)return alarms.size();
        return 0;
    }

    class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
        private TextView time,title,ring_status,format;
        private SwitchMaterial switchButton;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            time=itemView.findViewById(R.id.list_item_time);
            title=itemView.findViewById(R.id.list_item_title);
            ring_status=itemView.findViewById(R.id.list_item_ring_time);
            switchButton=itemView.findViewById(R.id.list_item_switch);
            format=itemView.findViewById(R.id.list_item_format);
            itemView.setOnClickListener(this);
            switchButton.setOnCheckedChangeListener(this);
        }

        @Override
        public void onClick(View v) {
            int alarmId=alarms.get(getAdapterPosition()).getAlarmId();
            itemClickListener.onClick(alarmId);
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            Alarm alarm=alarms.get(getAdapterPosition());
            if(isChecked){ alarm.setActive(true);}
            else{alarm.setActive(false);}
            toggleAlarmListener.onToggle(alarm);
        }
    }

    public interface onItemClickListener{
        public void onClick(int alarmId);
    }

    public interface onToggleAlarmListener
    {
        public void onToggle(Alarm alarm);
    }
}
