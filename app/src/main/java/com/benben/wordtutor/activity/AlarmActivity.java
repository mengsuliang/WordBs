package com.benben.wordtutor.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.benben.wordtutor.R;
import com.benben.wordtutor.receiver.AlarmReceiver;

import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity {

    private TimePicker timePicker;
    private Switch aSwitch;
    private TextView mTvTitle;
    private ImageView mIvBack;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    boolean isfirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        aSwitch = (Switch) findViewById(R.id.switch_alarm_learn);
        timePicker = (TimePicker) findViewById(R.id.timePicker_alarm);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("时间提醒");
        mIvBack = findViewById(R.id.iv_back);
        mIvBack.setVisibility(View.VISIBLE);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //实例化闹钟管理器
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    timePicker.setEnabled(true);
                } else {
                    timePicker.setEnabled(false);
                    Toast.makeText(AlarmActivity.this, "已取消学习提醒", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //时间选择器设置为24小时制
        timePicker.setIs24HourView(true);

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                if (aSwitch.isChecked()) {

                    //获取日期对象，小时数，分钟数
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                    calendar.set(Calendar.MINUTE,minute);

                    Intent intent = new Intent(AlarmActivity.this, AlarmReceiver.class);
                    intent.setAction("com.benben.wordtutor.RING");
                    pendingIntent =PendingIntent.getBroadcast(AlarmActivity.this,0x101,intent,0);
                    AlarmManager.AlarmClockInfo alarmClockInfo;

                    if (System.currentTimeMillis() < calendar.getTimeInMillis()){
                        alarmClockInfo = new AlarmManager.AlarmClockInfo(calendar.getTimeInMillis(), pendingIntent);
                        alarmManager.setAlarmClock(alarmClockInfo, pendingIntent);
                        Toast.makeText(AlarmActivity.this, "已设置" + hourOfDay + "时"
                                + minute + "分进行提醒", Toast.LENGTH_SHORT).show();
                    }else {
                        if(isfirst){//只提醒一次
                            Toast.makeText(AlarmActivity.this, "设置时间要晚于系统时间哦~",
                                    Toast.LENGTH_SHORT).show();
                            isfirst = false;
                        }

                    }

                }
            }
        });

    }
}