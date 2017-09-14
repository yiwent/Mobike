package com.yiwen.mobike.bean;

/**
 * Created by yiwen (https://github.com/yiwent)
 * Date:2017/6/14
 * Time: 11:26
 */

public enum  CountryEnum {

    China(0,"+86","CN"),Singapore(1,null,null),America(2, "+1", "US"),
    UnitedKingdom(3, "+44", "GB"),Malaysia(4, "+60", "MY"),Taiwan(5, "+886", "TW");

    public final int id;
    public final String iso;
    public final String mobileCode;

    private CountryEnum(int id, String mobileCode, String iso)
    {
        this.id = id;
        this.mobileCode = mobileCode;
        this.iso = iso;
    }

    public static CountryEnum mapIntegerToCountryEnum(Integer paramInteger)
    {
        for (CountryEnum localCountryEnum : values())
            if (localCountryEnum.id == paramInteger.intValue())
                return localCountryEnum;
        return China;
    }

    public String toString()
    {
        return this.mobileCode;
    }
}
