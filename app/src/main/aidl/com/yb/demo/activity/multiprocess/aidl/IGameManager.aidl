// IGameManager.aidl
package com.yb.demo.activity.multiprocess.aidl;
import com.yb.demo.activity.multiprocess.aidl.Game;
import com.yb.demo.activity.multiprocess.aidl.IClientManager;

// Declare any non-default types here with import statements

interface IGameManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    List<Game> getGameList();
    void addGame(in Game game);
    void registerClient(in IClientManager clientManager);
}
