package com.yb.demo.activity.startForCallback;


import android.app.Activity;
import android.app.FragmentManager;

public class StartForCallbackManager {
    private static final String TAG = "StartForCallbackFragment";

    public static void startRequest(Activity activity, ResultCallback callback) {
        getOnResultFragment(activity).request(callback);
    }

    private static StartForCallbackFragment getOnResultFragment(Activity activity) {
        StartForCallbackFragment onResultFragment = findOnResultFragment(activity);
        if (onResultFragment == null) {
            onResultFragment = new StartForCallbackFragment();
            FragmentManager fragmentManager = activity.getFragmentManager();
            fragmentManager.beginTransaction().add(onResultFragment, TAG).commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        return onResultFragment;
    }

    public static void remove(Activity activity) {
        StartForCallbackFragment onResultFragment = findOnResultFragment(activity);
        if (onResultFragment != null) {
            FragmentManager fragmentManager = activity.getFragmentManager();
            fragmentManager.beginTransaction().remove(onResultFragment).commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
    }

    private static StartForCallbackFragment findOnResultFragment(Activity activity) {
        return (StartForCallbackFragment) activity.getFragmentManager().findFragmentByTag(TAG);
    }

    public interface ResultCallback {
        void onSuccess(Object result);

        void onFailure();
    }
}
