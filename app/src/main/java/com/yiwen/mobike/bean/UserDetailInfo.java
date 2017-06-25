package com.yiwen.mobike.bean;

/**
 * Created by yiwen (https://github.com/yiwent)
 * Date:2017/6/14
 * Time: 10:40
 */

public class UserDetailInfo  {
    private String content;
    private boolean isClickable;
    private boolean isMargin;
    private String title;

    public UserDetailInfo(String title, String content)
    {
        this.content = content;
        this.title = title;
        this.isMargin = false;
    }

    public UserDetailInfo(String title, String content, boolean isClickable)
    {
        this.content = content;
        this.isClickable = isClickable;
        this.title = title;
        this.isMargin = false;
    }

    public UserDetailInfo(String title, String content, boolean isClickable, boolean isMargin)
    {
        this.title = title;
        this.content = content;
        this.isClickable = isClickable;
        this.isMargin = isMargin;
    }

    public String getContent()
    {
        return this.content;
    }

    public String getTitle()
    {
        return this.title;
    }

    public boolean isClickable()
    {
        return this.isClickable;
    }

    public boolean isMargin()
    {
        return this.isMargin;
    }

    public void setClickable(boolean paramBoolean)
    {
        this.isClickable = paramBoolean;
    }

    public void setContent(String paramString)
    {
        this.content = paramString;
    }

    public void setMargin(boolean paramBoolean)
    {
        this.isMargin = paramBoolean;
    }

    public void setTitle(String paramString)
    {
        this.title = paramString;
    }
}
