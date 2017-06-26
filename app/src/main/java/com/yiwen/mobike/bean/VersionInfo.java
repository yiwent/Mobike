package com.yiwen.mobike.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by yiwen (https://github.com/yiwent)
 * Date:2017/6/21
 * Time: 9:54
 * desc:更新版本
 */

public class VersionInfo extends BmobObject implements Serializable{
    private String versionName;//版本名
    private String versionCode;//版本号
    private String downloadUrl;//新版本下载路径
    private String versionDesc;//新版本的功能描述

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getVersionDesc() {
        return versionDesc;
    }

    public void setVersionDesc(String versionDesc) {
        this.versionDesc = versionDesc;
    }
}
