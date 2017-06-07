package com.yiwen.mobike.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by yiwen (https://github.com/yiwent)
 * Date:2017/6/5
 * Time: 12:47
 */

public class RideSummary extends BmobObject implements Serializable {
    private String phone;
    private String ride;
    private String save;
    private String kaluli;
    private MyUser myuser;

    public MyUser getMyuser() {
        return myuser;
    }

    public void setMyuser(MyUser myuser) {
        this.myuser = myuser;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRide() {
        return ride;
    }

    public void setRide(String ride) {
        this.ride = ride;
    }

    public String getSave() {
        return save;
    }

    public void setSave(String save) {
        this.save = save;
    }

    public String getKaluli() {
        return kaluli;
    }

    public void setKaluli(String kaluli) {
        this.kaluli = kaluli;
    }
}
