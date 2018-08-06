package com.tlinux.mp3musicplayer;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by tlinux on 18-8-4.
 */

public class MusicPlayerService extends Service{

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new Mp3PlayerImpl(intent,new Handler(getMainLooper()),this);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Notification.Builder builder = new Notification.Builder(this,"asdasdasdasd");
        builder.setContentText("bofangqi");
        Notification notification =builder.build();
        startForeground(1,notification);
    }
}
