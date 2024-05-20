package com.example.demochuong6;

import static com.example.demochuong6.MyApplication.CHANNEL_ID;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.io.IOException;


public class MyService extends Service {
    private MybBinder mybBinder = new MybBinder();
    private MediaPlayer media;
    private Sound msound;
    private boolean isPlaying;

    public class MybBinder extends Binder {
        MyService getMyService() {
            return MyService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("Nhom4", "Trinh phat nhac dang chay");
        return mybBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Sound sound = (Sound) bundle.get("object_song");
            startMusic(sound);
            sendNotification(sound);
            msound = sound;
        }
        return START_NOT_STICKY;
    }

    private void startMusic(Sound sound) {
        try {
            if (media == null) {
                media = new MediaPlayer();
                media.setDataSource(sound.getName());
                media.prepare();
            }
            media.start();
            isPlaying = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("ForegroundServiceType")
    private void sendNotification(Sound sound) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setContentTitle(getString(R.string.app_name));
        builder.setContentText(sound.getName());
        builder.setSmallIcon(R.drawable.notification);

        Notification notification = builder.build();

        startForeground(1, notification);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        if (media != null) {
            media.release();
            media = null;
        }
        super.onDestroy();
    }

    public Sound getMsound() {
        return msound;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void PauseMusic() {
        if (media != null && isPlaying) {
            media.pause();
            isPlaying = false;
        }
    }

    public void ResumeMusic() {
        if (media != null && !isPlaying) {
            media.start();
            isPlaying = true;
        }
    }
}