package com.yiwen.mobike.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.http.bean.Init;

/**
 * Created by yiwen (https://github.com/yiwent)
 * Date:2017/6/18
 * Time: 19:22
 */

public class Favorable extends BmobObject {
    private MyUser mMyUser;
    private Float money;
    private BmobDate date;
    private Init type;

    public MyUser getMyUser() {
        return mMyUser;
    }

    public void setMyUser(MyUser myUser) {
        mMyUser = myUser;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public BmobDate getDate() {
        return date;
    }

    public void setDate(BmobDate date) {
        this.date = date;
    }

    public Init getType() {
        return type;
    }

    public void setType(Init type) {
        this.type = type;
    }
}
