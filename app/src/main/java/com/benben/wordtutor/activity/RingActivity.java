package com.benben.wordtutor.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import com.benben.wordtutor.R;

public class RingActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private Button closeAlarm;
    private TimePicker timePicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring);

        closeAlarm = (Button) findViewById(R.id.close);
        timePicker = (TimePicker) findViewById(R.id.timepicker2);
        timePicker.setIs24HourView(true);

        //时间一到，播放音乐
        mediaPlayer = MediaPlayer.create(this,R.raw.champagneocean);
        mediaPlayer.start();

        //关闭闹钟
        closeAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                finish();
            }
        });

    }


}