package com.yiwen.mobike.activity.usercenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.sharesdk.onekeyshare.OnekeyShare;
import com.yiwen.mobike.R;
import com.yiwen.mobike.bean.MyMessage;
import com.yiwen.mobike.views.TabTitleView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.sharesdk.framework.ShareSDK;
import dmax.dialog.SpotsDialog;

public class MessageDetailActivity extends AppCompatActivity {

    public static final String MESSAGE = "message";

    @BindView(R.id.toolbar_massage_detail)
    TabTitleView mToolbar;
    @BindView(R.id.web_detil)
    WebView      mWebView;
    @BindView(R.id.lo_webview)
    LinearLayout mLoWebview;
    private MyMessage mMessage;
    private String    mUrl;
    private String    mTitle;
    private SpotsDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        ButterKnife.bind(this);
        getData();
        initToolbar();
        initWebView();
        ShareSDK.initSDK(this);
    }

    private void initWebView() {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        /*使webview能够加载图片*/
        webSettings.setBlockNetworkImage(false);
        webSettings.setAppCacheEnabled(true);
        mDialog=new SpotsDialog(this);
        mDialog.show();
        mWebView.loadUrl(mMessage.getClickUrl());
//        mWebAppInterface = new WebAppInterface(this);
//        mWebView.addJavascriptInterface(mWebAppInterface, "appInterface");
        mWebView.setWebViewClient(new MyWebChromeC());
        mWebView.setWebChromeClient(new WebChromeClient(){

        });

    }

    private void initToolbar() {
        mToolbar.setOnLeftButtonClickListener(new TabTitleView.OnLeftButtonClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        mToolbar.setOnRightTextViewClickListener(new TabTitleView.OnRightButtonClickListener() {
            @Override
            public void onClick() {
                showShare();
            }
        });
        mToolbar.setTitleText(mMessage.getDecrdescription());
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(mMessage.getDecrdescription());
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://www.mobike.com");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(mMessage.getDecrdescription());
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl(mMessage.getImgUrl());
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数,优先级大于setImageUrl
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment(mMessage.getDecrdescription());
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("mobike");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://www.mobike.com");
        // 启动分享GUI
        oks.show(this);
    }

    private void getData() {
        Intent intent = getIntent();
        mMessage= (MyMessage) intent.getSerializableExtra(MessageDetailActivity.MESSAGE);
        if (mMessage==null)
            finish();
        mTitle = mMessage.getDecrdescription();
        mUrl=mMessage.getImgUrl();
    }
    class MyWebChromeC extends WebViewClient {
        /*加载网页代码时，逐行回调该方法*/
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
//            mWebAppInterface.showDetail();
//            if (!isCanRefresh)
//                isCanRefresh = true;
//            else
//                mRefreshLayout.finishRefresh();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK&&mWebView.canGoBack()){
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShareSDK.stopSDK();
    }
}
