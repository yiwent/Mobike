package com.yiwen.mobike.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by yiwen (https://github.com/yiwent)
 * Date:2017/6/20
 * Time: 20:06
 * desc:我的行程
 */

public class MyTripsData extends BmobObject {
    private MyUser mMyUser;
    private String time;
    private String money;
    private String carNub;
    private String rideTime;

    public MyUser getMyUser() {
        return mMyUser;
    }

    public void setMyUser(MyUser myUser) {
        mMyUser = myUser;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getCarNub() {
        return carNub;
    }

    public void setCarNub(String carNub) {
        this.carNub = carNub;
    }

    public String getRideTime() {
        return rideTime;
    }

    public void setRideTime(String rideTime) {
        this.rideTime = rideTime;
    }
}
