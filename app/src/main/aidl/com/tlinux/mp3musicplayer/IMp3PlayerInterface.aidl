// IMp3PlayerInterface.aidl
package com.tlinux.mp3musicplayer;

// Declare any non-default types here with import statements
import com.tlinux.mp3musicplayer.MusicInfoCallBack;

interface IMp3PlayerInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    void newMusic(String path,MusicInfoCallBack callback);
    void resume();
    void pause();
    void release();
    void seek(int position);
}
