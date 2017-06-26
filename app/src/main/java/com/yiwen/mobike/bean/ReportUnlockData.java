package com.yiwen.mobike.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by yiwen (https://github.com/yiwent)
 * Date:2017/6/24
 * Time: 12:53
 * desc:开不了锁  其他问题 的反馈
 */

public class ReportUnlockData extends BmobObject {
    private MyUser mMyUser;
    private String carNub;
    private String desc;

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
