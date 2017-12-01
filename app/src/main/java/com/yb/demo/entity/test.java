package com.yb.demo.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/3/7.
 */
public class test implements Parcelable{
    private int va1 = 0;
    private int va2 = 0;

    protected test(Parcel in) {
        va1 = in.readInt();
        va2 = in.readInt();
    }

    public static final Creator<test> CREATOR = new Creator<test>() {
        @Override
        public test createFromParcel(Parcel in) {
            return new test(in);
        }

        @Override
        public test[] newArray(int size) {
            return new test[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(va1);
        dest.writeInt(va2);
    }

}
