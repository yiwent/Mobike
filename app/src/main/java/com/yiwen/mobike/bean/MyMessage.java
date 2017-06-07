package com.yiwen.mobike.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by yiwen (https://github.com/yiwent)
 * Date:2017/6/5
 * Time: 14:52
 */

public class MyMessage extends BmobObject implements Serializable {
    private String time;
    private String imgUrl;
    private String decrdescription;
    private String clickUrl;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDecrdescription() {
        return decrdescription;
    }

    public void setDecrdescription(String decrdescription) {
        this.decrdescription = decrdescription;
    }

    public String getClickUrl() {
        return clickUrl;
    }

    public void setClickUrl(String clickUrl) {
        this.clickUrl = clickUrl;
    }
}
