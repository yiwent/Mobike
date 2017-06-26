package com.yiwen.mobike.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by yiwen (https://github.com/yiwent)
 * Date:2017/6/5
 * Time: 15:07
 * desc:用户基本信息
 */

public class MyUser extends BmobUser implements Serializable {
    private String   nickName;
    private String   qq;
    private BmobFile qqPic;
    private String   weixin;
    private BmobFile weixinPic;
    private String   School;
    private String   SchoolNub;
    private BmobFile picUser;
    private Float   money     ;
    private Boolean isPay     ;
    private Boolean isRealName ;
    private String myName;//真名
    private String idCard;

    public String getSchoolNub() {
        return SchoolNub;
    }

    public void setSchoolNub(String schoolNub) {
        SchoolNub = schoolNub;
    }

    public BmobFile getQqPic() {
        return qqPic;
    }

    public void setQqPic(BmobFile qqPic) {
        this.qqPic = qqPic;
    }

    public BmobFile getWeixinPic() {
        return weixinPic;
    }

    public void setWeixinPic(BmobFile weixinPic) {
        this.weixinPic = weixinPic;
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

    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    @Override
    public String toString() {
        return "MyUser{" +
                "nickName='" + nickName + '\'' +
                ", qq='" + qq + '\'' +
                ", qqPic=" + qqPic +
                ", weixin='" + weixin + '\'' +
                ", weixinPic=" + weixinPic +
                ", School='" + School + '\'' +
                ", picUser=" + picUser +
                ", money=" + money +
                ", isPay=" + isPay +
                ", isRealName=" + isRealName +
                ", myName='" + myName + '\'' +
                ", idCard='" + idCard + '\'' +
                '}';
    }
}
