package com.yb.demo.activity.multiprocess.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/11/10.
 */
public class Game implements Parcelable {
    private String name;
    private String describe;

    public Game(String name, String describe) {
        this.name = name;
        this.describe = describe;
    }

    protected Game(Parcel in) {
        name = in.readString();
        describe = in.readString();
    }

    public static final Creator<Game> CREATOR = new Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(describe);
    }

    @Override
    public String toString() {
        return "Game{" +
                "name='" + name + '\'' +
                ", describe='" + describe + '\'' +
                '}';
    }
}
