package com.yiwen.mobike.bean;

import java.util.Locale;

/**
 * Created by yiwen (https://github.com/yiwent)
 * Date:2017/6/14
 * Time: 12:01
 */

public enum CurrencyEnum {
    RMB(0),SGD(1),USD(2),GBP(3),MYR(4),TWD(5);

    public final int id;

//    static
//    {
//        GBP = new CurrencyEnum("GBP", 3, 3);
//        MYR = new CurrencyEnum("MYR", 4, 4);
//        TWD = new CurrencyEnum("TWD", 5, 5);
//        CurrencyEnum[] arrayOfCurrencyEnum = new CurrencyEnum[6];
//        arrayOfCurrencyEnum[0] = RMB;
//        arrayOfCurrencyEnum[1] = SGD;
//        arrayOfCurrencyEnum[2] = USD;
//        arrayOfCurrencyEnum[3] = GBP;
//        arrayOfCurrencyEnum[4] = MYR;
//        arrayOfCurrencyEnum[5] = TWD;
//        $VALUES = arrayOfCurrencyEnum;
//    }

    private CurrencyEnum(int paramInt)
    {
        this.id = paramInt;
    }

    public static CurrencyEnum mapIntegerToCurrencyEnum(Integer paramInteger)
    {
        for (CurrencyEnum localCurrencyEnum : values())
            if (localCurrencyEnum.id == paramInteger.intValue())
                return localCurrencyEnum;
        return RMB;
    }

    public String getPrefixSymbol()
    {
        if (this.id == 0)
            return "¥";
        if (this.id == 1)
            return "S$";
        if (this.id == 2)
            return "$";
        if (this.id == 3)
            return "£";
        if (this.id == 4)
            return "RM";
        if (this.id == 5)
            return "NT$";
        return "";
    }

    public String getSymbol()
    {
        if (Locale.getDefault().getLanguage().equals("zh"))
        {
            if (this.id == 0)
                return "元";
            if (this.id == 1)
                return "新元";
            if (this.id == 2)
                return "美元";
            if (this.id == 3)
                return "英镑";
            if (this.id == 4)
                return "令吉";
            if (this.id == 5)
                return "台币";
        }
        else
        {
            if (this.id == 0)
                return " CNY";
            if (this.id == 1)
                return " SGD";
            if (this.id == 2)
                return " USD";
            if (this.id == 3)
                return " GBP";
            if (this.id == 4)
                return " MYR";
            if (this.id == 5)
                return " TWD";
        }
        return "";
    }

    public String getSymbolTrimmed()
    {
        return getSymbol().trim();
    }
}
