package com.example.demochuong6;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class MyApplication extends Application {

    public  static String CHANNEL_ID = "CHANNEL_EXAMPLE";
    @Override
    public void onCreate() {
        super.onCreate();

        createNoticationChannel();
    }

    private void createNoticationChannel() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){

            NotificationChannel chanel = new NotificationChannel(CHANNEL_ID, "channel example",
                    NotificationManager.IMPORTANCE_LOW);
            NotificationManager manager = getSystemService(NotificationManager.class);
            if(manager != null){
                manager.createNotificationChannel(chanel);
            }
        }
    }
}











