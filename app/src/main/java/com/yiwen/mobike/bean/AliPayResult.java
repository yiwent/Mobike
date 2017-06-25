package com.yiwen.mobike.bean;

import android.text.TextUtils;

/**
 * Created by yiwen (https://github.com/yiwent)
 * Date:2017/6/14
 * Time: 9:38
 */

public class AliPayResult {
    private String memo;
    private String result;
    private String resultStatus;

    public AliPayResult(String string)
    {
        if (TextUtils.isEmpty(string));
        while (true)
        {
          //  return;
            for (String str : string.split(";"))
            {
                if (str.startsWith("resultStatus"))
                 this.resultStatus = gatValue(str, "resultStatus");
                if (str.startsWith("result"))
                    this.result = gatValue(str, "result");
                if (!str.startsWith("memo"))
                   continue;
               this.memo = gatValue(str, "memo");
            }
        }
    }

    private String gatValue(String paramString1, String paramString2)
    {
        String str = paramString2 + "={";
        return paramString1.substring(paramString1.indexOf(str) + str.length(), paramString1.lastIndexOf("}"));
    }

    public String getMemo()
    {
        return this.memo;
    }

    public String getResult()
    {
        return this.result;
    }

    public String getResultStatus()
    {
        return this.resultStatus;
    }

    public String toString()
    {
        return "resultStatus={" + this.resultStatus + "};memo={" + this.memo + "};result={" + this.result + "}";
    }
}
