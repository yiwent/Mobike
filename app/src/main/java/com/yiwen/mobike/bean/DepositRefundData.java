package com.yiwen.mobike.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by yiwen (https://github.com/yiwent)
 * Date:2017/6/20
 * Time: 15:03
 */

public class DepositRefundData extends BmobObject {
    private MyUser   mMyUser;
    private String   paytype;
    private BmobFile orderPic;
    private String   orderNub;

    public MyUser getMyUser() {
        return mMyUser;
    }

    public void setMyUser(MyUser myUser) {
        mMyUser = myUser;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public BmobFile getOrderPic() {
        return orderPic;
    }

    public void setOrderPic(BmobFile orderPic) {
        this.orderPic = orderPic;
    }

    public String getOrderNub() {
        return orderNub;
    }

    public void setOrderNub(String orderNub) {
        this.orderNub = orderNub;
    }
}
