package com.yiwen.mobike.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by yiwen (https://github.com/yiwent)
 * Date:2017/6/18
 * Time: 19:32
 * desc:信用积分
 */

public class Credit extends BmobObject {
    private MyUser mMyUser;
    private Integer creditNub;

    public MyUser getMyUser() {
        return mMyUser;
    }

    public void setMyUser(MyUser myUser) {
        mMyUser = myUser;
    }

    public Integer getCreditNub() {
        return creditNub;
    }

    public void setCreditNub(Integer creditNub) {
        this.creditNub = creditNub;
    }
}
