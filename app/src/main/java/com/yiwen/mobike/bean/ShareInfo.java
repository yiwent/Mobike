package com.yiwen.mobike.bean;

import java.io.Serializable;

/**
 * Created by yiwen (https://github.com/yiwent)
 * Date:2017/6/14
 * Time: 10:40
 */

public class ShareInfo implements Serializable {
    private String content;
    private String imgPath;
    private String title;
    private String url;

    public ShareInfo(String paramString1, String paramString2, String paramString3, String paramString4)
    {
        this.title = paramString1;
        this.content = paramString2;
        this.url = paramString3;
        this.imgPath = paramString4;
    }

    public String getContent()
    {
        return this.content;
    }

    public String getImgPath()
    {
        return this.imgPath;
    }

    public String getTitle()
    {
        return this.title;
    }

    public String getUrl()
    {
        return this.url;
    }

    public void setContent(String paramString)
    {
        this.content = paramString;
    }

    public void setImgPath(String paramString)
    {
        this.imgPath = paramString;
    }

    public void setTitle(String paramString)
    {
        this.title = paramString;
    }

    public void setUrl(String paramString)
    {
        this.url = paramString;
    }
}
