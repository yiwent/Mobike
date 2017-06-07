package com.yiwen.mobike.base;

/**
 * Created by yiwen (https://github.com/yiwent)
 * Date:2017/6/6
 * Time: 13:05
 */

public class ShareInfo {
    private String content;
    private String imgPath;
    private String title;
    private String url;

    public ShareInfo(String content, String imgPath, String title, String url) {
        this.content = content;
        this.imgPath = imgPath;
        this.title = title;
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
