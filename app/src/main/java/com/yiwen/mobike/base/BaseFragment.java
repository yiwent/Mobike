package com.yiwen.mobike.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yiwen.mobike.MyApplication;
import com.yiwen.mobike.activity.LoginActivity;
import com.yiwen.mobike.bean.MyUser;


public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = createView(inflater, container, savedInstanceState);

        initToolBar();
        init();
        return view;
    }

    public void initToolBar() {

    }

    public abstract View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    public abstract void init();

    public void startActivity(Intent intent, boolean isNeedLogin) {
        if (isNeedLogin) {
            MyUser user = MyApplication.getInstance().getUser();
            if (user != null) {
                super.startActivity(intent);
            } else {
                MyApplication.getInstance().putIntent(intent);
                Intent i = new Intent(getActivity(), LoginActivity.class);
                super.startActivity(i);
            }
        } else {
            super.startActivity(intent);
        }
    }

    public void startActivityForResult(Intent intent, int requestCode, boolean isNeedLogin) {
        if (isNeedLogin) {
            MyUser user = MyApplication.getInstance().getUser();
            if (user != null) {
                super.startActivityForResult(intent, requestCode);
            } else {
                MyApplication.getInstance().putIntent(intent);
                Intent i = new Intent(getActivity(), LoginActivity.class);
                super.startActivity(intent);
            }
        } else {
            super.startActivityForResult(intent, requestCode);
        }
    }
}
