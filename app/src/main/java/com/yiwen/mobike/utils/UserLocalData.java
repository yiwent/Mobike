package com.yiwen.mobike.utils;

import android.content.Context;
import android.text.TextUtils;

import com.orhanobut.logger.Logger;
import com.yiwen.mobike.bean.MyUser;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;


public class UserLocalData {

    public static void putUser(Context context, MyUser user) {
        String user_json = JSONUtil.toJSON(user);
        PreferencesUtils.putString(context, MyConstains.USER_JSON, user_json);
    }


    public static MyUser getUser(Context context) {
        String user_json = PreferencesUtils.getString(context, MyConstains.USER_JSON);
        if (!TextUtils.isEmpty(user_json)) {
            return JSONUtil.fromJson(user_json, MyUser.class);
        }
        return null;
    }

    public static void clearUser(Context context) {
        PreferencesUtils.remove(context, MyConstains.USER_JSON);
    }


    public static void upDataUser(Context context, MyUser localuser,MyUser newUser) {
        putUser(context,localuser);
        newUser.update(localuser.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                Logger.d(e);
            }
        });
    }
}
