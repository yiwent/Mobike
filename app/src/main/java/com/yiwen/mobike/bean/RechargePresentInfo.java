package com.yiwen.mobike.bean;

import java.util.List;

/**
 * Created by yiwen (https://github.com/yiwent)
 * Date:2017/6/14
 * Time: 10:19
 */

public class RechargePresentInfo {
    private String country;
    private List<RechargePresentInfo.PackagesBean> packages;

    public String getCountry()
    {
        return this.country;
    }

    public List<PackagesBean> getPackages()
    {
        return this.packages;
    }

    public void setCountry(String paramString)
    {
        this.country = paramString;
    }

    public void setPackages(List<RechargePresentInfo.PackagesBean> paramList)
    {
        this.packages = paramList;
    }

    public class PackagesBean {
    }
}
