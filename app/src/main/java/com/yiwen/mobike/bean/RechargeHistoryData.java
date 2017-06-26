package com.yiwen.mobike.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by yiwen (https://github.com/yiwent)
 * Date:2017/6/19
 * Time: 19:57
 * desc:充值记录
 */

public class RechargeHistoryData extends BmobObject {
    private MyUser mMyUser;
    private String cash;
    private String timestamp;
    private String payApproach;
    private String StatusCode;

    public MyUser getMyUser() {
        return mMyUser;
    }

    public void setMyUser(MyUser myUser) {
        mMyUser = myUser;
    }

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPayApproach() {
        return payApproach;
    }

    public void setPayApproach(String payApproach) {
        this.payApproach = payApproach;
    }

    public String getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(String statusCode) {
        StatusCode = statusCode;
    }
}
