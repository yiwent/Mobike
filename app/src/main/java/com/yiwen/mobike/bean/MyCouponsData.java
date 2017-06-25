package com.yiwen.mobike.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by yiwen (https://github.com/yiwent)
 * Date:2017/6/20
 * Time: 16:32
 */

public class MyCouponsData extends BmobObject {
    private MyUser mMyUser;
    private String money;
    private String time;
    private String type;

    public MyUser getMyUser() {
        return mMyUser;
    }

    public void setMyUser(MyUser myUser) {
        mMyUser = myUser;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
