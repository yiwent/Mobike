package com.yiwen.mobike.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by yiwen (https://github.com/yiwent)
 * Date:2017/6/24
 * Time: 14:43
 * desc:举报违停和发现故障
 */

public class BikeDamageData extends BmobObject {
    private MyUser mMyUser;
    private  String carNub;
    private String desc;
    private BmobFile carPic;
    private String type;

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

    public BmobFile getCarPic() {
        return carPic;
    }

    public void setCarPic(BmobFile carPic) {
        this.carPic = carPic;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
