package com.tlinux.mp3musicplayer;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.TimedMetaData;
import android.media.TimedText;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;

/**
 * Created by tlinux on 18-8-4.
 */

public class Mp3PlayerImpl extends IMp3PlayerInterface.Stub {


    private static final int REQUEST_NEWMUSIC = 1;
    private static final int REQUEST_PAUSE = 2;
    private static final int REQUEST_RESUME = 3;
    private static final int REQUEST_RELEASE = 4;

    private Handler mMainHandler;
    private Intent mIntent;
    private Handler mWorkHandler;
    private Context mContext;

    private volatile int seq = 0;


    private MediaPlayer mMediaPlayer;
    private MusicInfoCallBack mCurrentMusicCallBack;
    private String mCurrentPath;



    public Mp3PlayerImpl(Intent intent, Handler handler, Context context) {
        mIntent = intent;
        mMainHandler = handler;

        HandlerThread handlerThread = new HandlerThread("player_thread");
        handlerThread.start();
        mWorkHandler = new WorkHandler(handlerThread.getLooper());
        mContext = context;
    }

    @Override
    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
    }

    @Override
    public void newMusic(String path, MusicInfoCallBack callback) throws RemoteException {
        if (TextUtils.isEmpty(path)) {
            throw new IllegalArgumentException("path is err");
        }
        Message message = mWorkHandler.obtainMessage();
        message.obj = new Pair<>(path,callback);
        message.what = REQUEST_NEWMUSIC;
        message.sendToTarget();
    }

    @Override
    public void resume() throws RemoteException {

    }

    @Override
    public void pause() throws RemoteException {
    }

    @Override
    public void release() throws RemoteException {

    }

    @Override
    public void seek(int position) throws RemoteException {

    }

    private class WorkHandler extends Handler {
        public WorkHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case REQUEST_NEWMUSIC:
                    Pair<String,MusicInfoCallBack> pair = (Pair<String, MusicInfoCallBack>) msg.obj;
                    newMusicAsync(pair.first,pair.second);
                    break;
            }
        }
    }

    public void newMusicAsync(String path,MusicInfoCallBack callBack) {
        mCurrentPath = path;
        mCurrentMusicCallBack = callBack;
        if (mMediaPlayer!=null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
        mMediaPlayer = MediaPlayer.create(mContext, Uri.parse(mCurrentPath));
        if (mMediaPlayer == null) return; //TODO callback
        mMediaPlayer.start();
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.e("AAAAA","thread: "+ Thread.currentThread().toString()+"  onCompletion");
            }
        });

        mMediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                Log.e("AAAAA","thread: "+ Thread.currentThread().toString()+"  onBufferingUpdate "+"  percent:"+percent);
            }
        });

        mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.e("AAAAA","thread: "+ Thread.currentThread().toString()+"  onError "+"  what:"+what+"   extra:"+extra);
                return false;
            }
        });

        mMediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                Log.e("AAAAA","thread: "+ Thread.currentThread().toString()+"  onInfo "+"  what:"+what+"   extra:"+extra);
                return false;
            }
        });

        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Log.e("AAAAA","thread: "+ Thread.currentThread().toString()+"  onPrepared ");
            }
        });

        mMediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete(MediaPlayer mp) {
                Log.e("AAAAA","thread: "+ Thread.currentThread().toString()+"  onSeekComplete ");
            }
        });

        mMediaPlayer.setOnTimedMetaDataAvailableListener(new MediaPlayer.OnTimedMetaDataAvailableListener() {
            @Override
            public void onTimedMetaDataAvailable(MediaPlayer mp, TimedMetaData data) {
                Log.e("AAAAA","thread: "+ Thread.currentThread().toString()+"  onTimedMetaDataAvailable "+"  time:"+data.getTimestamp());

            }
        });

        mMediaPlayer.setOnTimedTextListener(new MediaPlayer.OnTimedTextListener() {
            @Override
            public void onTimedText(MediaPlayer mp, TimedText text) {
                Log.e("AAAAA","thread: "+ Thread.currentThread().toString()+"  onTimedText "+"  time:"+text.getText());
            }
        });

    }
}
