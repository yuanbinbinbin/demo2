// IClientManager.aidl
package com.yb.demo.activity.multiprocess.aidl;
import com.yb.demo.activity.multiprocess.aidl.Game;

// Declare any non-default types here with import statements

interface IClientManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    void refreshData(in List<Game> data);
}
