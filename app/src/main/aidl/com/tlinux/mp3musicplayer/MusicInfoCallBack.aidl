// MusicInfoCallBack.aidl
package com.tlinux.mp3musicplayer;

// Declare any non-default types here with import statements
import com.tlinux.mp3musicplayer.MusicInfo;

interface MusicInfoCallBack {

   void onInitlized(in MusicInfo info);
}
