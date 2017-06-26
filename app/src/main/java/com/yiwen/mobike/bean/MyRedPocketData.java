package com.yiwen.mobike.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by yiwen (https://github.com/yiwent)
 * Date:2017/6/23
 * Time: 23:55
 * desc:红包记录
 */

public class MyRedPocketData extends BmobObject {
    private MyUser mMyUser;
    private String carNub;
    private String time;
    private String money;
    private boolean isvalid;

    public MyRedPocketData(MyUser myUser, String carNub, String time, String money, boolean isvalid) {
        mMyUser = myUser;
        this.carNub = carNub;
        this.time = time;
        this.money = money;
        this.isvalid = isvalid;
    }

    public boolean isvalid() {
        return isvalid;
    }

    public void setIsvalid(boolean isvalid) {
        this.isvalid = isvalid;
    }

    public MyUser getMyUser() {
        return mMyUser;
    }

    public void setMyUser(MyUser myUser) {
        mMyUser = myUser;
    }

    public String getCarNub() {
        return carNub;
    }

    public void setCarNub(String carNub) {
        this.carNub = carNub;
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

    @Override
    public String toString() {
        return "MyRedPocketData{" +
                "mMyUser=" + mMyUser +
                ", carNub='" + carNub + '\'' +
                ", time='" + time + '\'' +
                ", money='" + money + '\'' +
                ", isvalid=" + isvalid +
                '}';
    }
}
