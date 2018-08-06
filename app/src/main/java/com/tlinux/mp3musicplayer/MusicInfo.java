package com.tlinux.mp3musicplayer;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tlinux on 18-8-4.
 */

public class MusicInfo implements Parcelable {


    protected MusicInfo(Parcel in) {
    }

    public static final Creator<MusicInfo> CREATOR = new Creator<MusicInfo>() {
        @Override
        public MusicInfo createFromParcel(Parcel in) {
            return new MusicInfo(in);
        }

        @Override
        public MusicInfo[] newArray(int size) {
            return new MusicInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
