package com.yiwen.mobike;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;

import com.baidu.mapapi.SDKInitializer;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.SimpleProgressiveJpegConfig;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.crashreport.CrashReport;
import com.yiwen.mobike.bean.MyUser;
import com.yiwen.mobike.utils.UserLocalData;

import java.io.File;

import cn.bmob.v3.Bmob;


/**
 * Created by yiwen (https://github.com/yiwent)
 * Date:2017/5/27
 * Time: 20:46
 */

public class MyApplication extends Application {
    private static MyApplication sInstance;
    public static String mSDCardPath;
    public static final String APP_FOLDER_NAME = "MoBike";

    @Override
    public void onCreate() {
        super.onCreate();
        CrashReport.initCrashReport(getApplicationContext(), "e1a62089c6", false);
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setProgressiveJpegConfig(new SimpleProgressiveJpegConfig())
                .build();
        Fresco.initialize(this, config);
        SDKInitializer.initialize(this);
        Bmob.initialize(this, "b0cb494256d9b86fc931ca930a055b75");
        Logger.addLogAdapter(new AndroidLogAdapter(){
            @Override
            public boolean isLoggable(int priority, String tag) {
                return true;// TODO: 2017/6/5
            }
        });
        sInstance = this;
        initUser();
    }

    public static MyApplication getInstance() {
        return sInstance;
    }

    private MyUser mUser;

    private void initUser() {
        this.mUser = UserLocalData.getUser(this);
    }

    public MyUser getUser() {
        return mUser;
    }
    public void upDataUser(MyUser localuser,MyUser newUser) {
        this.mUser = localuser;
        UserLocalData.upDataUser(this, localuser,newUser);
    }

    public void putUser(MyUser user) {
        this.mUser = user;
        UserLocalData.putUser(this, user);
    }

    public void clearUser() {
        this.mUser = null;
        UserLocalData.clearUser(this);
    }

    private Intent intent;

    public void putIntent(Intent intent) {
        this.intent = intent;
    }

    public Intent getIntent() {
        return this.intent;
    }

    public void jumpToTargetActivity(Context context) {
        context.startActivity(intent);
        this.intent = null;
    }

    private boolean initDirs() {
        mSDCardPath = Environment.getExternalStorageDirectory().toString();
        if (mSDCardPath == null) {
            return false;
        }
        File f = new File(mSDCardPath, APP_FOLDER_NAME);
        if (!f.exists()) {
            try {
                f.mkdirs();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

}
