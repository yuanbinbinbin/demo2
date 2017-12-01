package com.yb.demo.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Fragmet 基类
 * Created by yb on 2017/9/18.
 */
public abstract class BaseFragment extends Fragment {
    protected View containerView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("test", "BaseFragment onAttach ");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("test", "BaseFragment onCreate ");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("test", "BaseFragment onCreateView ");
        if (inflater == null) {
            if (getActivity() != null) {
                inflater = LayoutInflater.from(getActivity());
            } else if (getContext() != null) {
                inflater = LayoutInflater.from(getActivity());
            }
        }
        if (inflater != null) {
            containerView = inflater.inflate(getLayoutId(), null);
            return containerView;
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
        initListener();
        Log.e("test", "BaseFragment onActivityCreated ");
    }

    protected View findViewById(int id) {
        if (containerView == null) {
            return null;
        } else {
            return containerView.findViewById(id);
        }
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initListener();
}
