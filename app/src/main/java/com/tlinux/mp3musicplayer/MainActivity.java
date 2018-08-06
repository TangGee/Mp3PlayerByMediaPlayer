package com.tlinux.mp3musicplayer;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private IMp3PlayerInterface mp3Player;
    private Handler mMainHandler = new Handler();

    private Button start;
    private Button pause;
    private Button resume;
    private TextView totleTime;
    private TextView currentTime;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this,MusicPlayerService.class);
        startForegroundService(intent);
        bindService(intent,mPlayerConnection,BIND_AUTO_CREATE);
        mMainHandler.postDelayed(checkPlayerOk,5000);

        pause = (Button) findViewById(R.id.bt_pause);
        resume = (Button) findViewById(R.id.bt_resume);
        start = (Button) findViewById(R.id.bt_start);
        currentTime = (TextView) findViewById(R.id.current_time);
        totleTime = (TextView) findViewById(R.id.totle_time);
        seekBar = (SeekBar) findViewById(R.id.player_seek);
        seekBar.setMax(100);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        pause.setOnClickListener(this);
        resume.setOnClickListener(this);
        start.setOnClickListener(this);
    }


    private ServiceConnection mPlayerConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("AAAA","----------------------------------");
            mp3Player = IMp3PlayerInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mp3Player = null;
        }
    };

    private Runnable checkPlayerOk = new Runnable() {
        @Override
        public void run() {
            if (!isFinishing()) {
                if (mp3Player == null) {
                    Toast.makeText(MainActivity.this, "fuck mp3Player init error", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
            }
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_pause:
                break;
            case R.id.bt_resume:
                break;
            case R.id.bt_start:
                startPlay();
                break;
        }
    }

    private void startPlay() {
        try {
            mp3Player.newMusic("file:///sdcard/jiangzhende.mp3",callBack);
        } catch (RemoteException e) {
            Log.e("AAAA","asdasd",e);
        }
    }

    private MusicInfoCallBack callBack = new MusicInfoCallBack() {
        @Override
        public void onInitlized(MusicInfo info) throws RemoteException {

        }

        @Override
        public IBinder asBinder() {
            return null;
        }
    };
}
