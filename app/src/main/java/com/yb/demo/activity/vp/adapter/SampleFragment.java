package com.yb.demo.activity.vp.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SampleFragment extends Fragment {
    public static SampleFragment newInstance(String tag) {

        Bundle args = new Bundle();
        args.putString("tag", tag);
        SampleFragment fragment = new SampleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    String tag;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e("SampleFragment", tag + " setUserVisibleHint " + isVisibleToUser);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        tag = getArguments().getString("tag", "no have");
        Log.e("SampleFragment", tag + " onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("SampleFragment", tag + " onCreate onDestroyView: " + getArguments().getString("onDestroyView") + " onSaveInstanceState: "
                + (savedInstanceState == null ? null : savedInstanceState.getString("onSaveInstanceState")));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("SampleFragment", tag + " onCreateView onDestroyView: "
                + getArguments().getString("onDestroyView") + " onSaveInstanceState: "
                + (savedInstanceState == null ? null : savedInstanceState.getString("onSaveInstanceState")));
        TextView tv = new TextView(getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        tv.setLayoutParams(params);
        tv.setText(tag);
        tv.setGravity(Gravity.CENTER);
        return tv;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("SampleFragment", tag + " onViewCreated");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("SampleFragment", tag + " onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("SampleFragment", tag + " onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("SampleFragment", tag + " onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("SampleFragment", tag + " onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("SampleFragment", tag + " onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("SampleFragment", tag + " onDestroyView");
        getArguments().putString("onDestroyView", "onSaveInstanceState");
        Log.e("SampleFragment", tag + " onDestroyView: onSaveInstanceState");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("SampleFragment", tag + " onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("SampleFragment", tag + " onDetach");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("onSaveInstanceState", "onSaveInstanceState");
        Log.e("SampleFragment", tag + " onSaveInstanceState: onSaveInstanceState");
    }

}
