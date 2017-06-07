package com.yiwen.mobike.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.yiwen.mobike.MyApplication;
import com.yiwen.mobike.activity.LoginActivity;
import com.yiwen.mobike.bean.MyUser;


/**
 * Created by yiwen (https://github.com/yiwent)
 * Date:2017/6/3
 * Time: 20:20
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }

    public void startActivity(Intent intent, boolean isNeedLogin) {
        if (isNeedLogin) {
            MyUser user = MyApplication.getInstance().getUser();
            if (user != null) {
                super.startActivity(intent);
            } else {
                MyApplication.getInstance().putIntent(intent);
                Intent i = new Intent(this, LoginActivity.class);
                super.startActivity(intent);
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
                Intent i = new Intent(this, LoginActivity.class);
                super.startActivity(intent);
            }
        } else {
            super.startActivityForResult(intent, requestCode);
        }
    }
}
