package com.example.demochuong6;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MianAcitivity extends AppCompatActivity {

    private  MyService myService;
    private boolean isServiceConnected;
    private RelativeLayout layoutBottom;
    private TextView tv_namesong;
    private ImageView imgPlayPause;

    private ServiceConnection mServiceConnection = new ServiceConnection(){
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            MyService.MybBinder mybBinder = (MyService.MybBinder) iBinder;
            myService = mybBinder.getMyService();
            isServiceConnected= true;

            handleLayoutMusic();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            myService= null;
            isServiceConnected=false;
        }
    };



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnStartService = findViewById(R.id.btn_start_service);
        Button btnStopService = findViewById(R.id.btn_stop_service);
        layoutBottom = findViewById(R.id.layout_bottom);
        imgPlayPause = findViewById(R.id.img_play_Pause);
        tv_namesong = findViewById(R.id.tv_Play_Pause);

        imgPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myService.isPlaying()){
                    myService.PauseMusic();
                }else{
                    myService.ResumeMusic();
                }
                setStatusimgView();
            }
        });
        btnStopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickStopService();
            }
        });
        btnStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickStartService();
            }
        });
    }
    private void onClickStartService(){
        Intent intent = new Intent(this, MyService.class);
        Bundle bundle = new Bundle();
        Sound song = new Sound("object_song",R.raw.sound);

        intent.putExtras(bundle);
        startService(intent);


        bindService(intent,mServiceConnection, Context.BIND_AUTO_CREATE);

    }
    private void onClickStopService(){
        Intent intent = new Intent(this, MyService.class);
        stopService(intent);
        if(isServiceConnected){
            unbindService(mServiceConnection);
            isServiceConnected=false;
        }
    }
    private void handleLayoutMusic() {
        layoutBottom.setVisibility(View.VISIBLE);
        tv_namesong.setText(myService.getMsound().getName());
        setStatusimgView();
    }
    private void setStatusimgView(){
        if(myService==null){
            return;
        }
        if(myService.isPlaying()) {
            imgPlayPause.setImageResource(R.drawable.baseline_pause_24);
        }else{
            imgPlayPause.setImageResource(R.drawable.baseline_play_arrow_24);
        }
    }


}














