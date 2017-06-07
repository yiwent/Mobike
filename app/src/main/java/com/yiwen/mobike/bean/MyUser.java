package com.yiwen.mobike.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by yiwen (https://github.com/yiwent)
 * Date:2017/6/5
 * Time: 15:07
 */

public class MyUser extends BmobUser implements Serializable {
    private String   nickName;
    private String   qq;
    private String   weixin;
    private String   School;
    private BmobFile picUser;
    private Float    money;
    private Boolean  isPay;
    private Boolean  isRealName;
    private String   token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getSchool() {
        return School;
    }

    public void setSchool(String school) {
        School = school;
    }

    public BmobFile getPicUser() {
        return picUser;
    }

    public void setPicUser(BmobFile picUser) {
        this.picUser = picUser;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public Boolean getPay() {
        return isPay;
    }

    public void setPay(Boolean pay) {
        isPay = pay;
    }

    public Boolean getRealName() {
        return isRealName;
    }

    public void setRealName(Boolean realName) {
        isRealName = realName;
    }
}
