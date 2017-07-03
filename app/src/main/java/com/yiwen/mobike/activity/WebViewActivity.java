package com.yiwen.mobike.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.yiwen.mobike.R;
import com.yiwen.mobike.activity.pay.DepositRefundIssueActivity;
import com.yiwen.mobike.utils.CommonUtils;
import com.yiwen.mobike.views.LoadingPageView;
import com.yiwen.mobike.views.NetworkUnavailableView;
import com.yiwen.mobike.views.TabTitleView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebViewActivity extends AppCompatActivity {

    @BindView(R.id.title_webview)
    TabTitleView           mTitleWebview;
    @BindView(R.id.web_detil)
    WebView                webView;
    @BindView(R.id.lo_webview)
    LinearLayout           mLo_webview;
    @BindView(R.id.id_loadingviewpager)
    LoadingPageView        loadingPageView;
    @BindView(R.id.lo_networt)
    NetworkUnavailableView unavailableView;


    private String url;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        initData();
        initEvent();
    }

    private void initEvent() {
        mTitleWebview.setOnLeftButtonClickListener(new TabTitleView.OnLeftButtonClickListener() {
            @Override
            public void onClick() {
                WebViewActivity.this.finish();
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK&&webView.canGoBack()){
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initData() {
        Intent intent = getIntent();
        Intent localIntent = getIntent();
        this.title = localIntent.getStringExtra("webview_title");
        this.url = localIntent.getStringExtra("webview_url");
        Uri localUri = localIntent.getData();
        if (localUri != null)
        {
            if (TextUtils.isEmpty(this.url))
                this.url = localUri.getQueryParameter("url");
            if (TextUtils.isEmpty(this.title))
                this.title = localUri.getQueryParameter("title");
        }
        if (TextUtils.isEmpty(url))finish();
        if (TextUtils.isEmpty(title))setTitle(title);
        initWebView();
        if (!CommonUtils.isNetworkAvailable(this)) {
            loadingPageView.hide();
            unavailableView.setVisibility(View.VISIBLE);
        } else {
            loadingPageView.show();
            unavailableView.setVisibility(View.GONE);
        }
    }

    private void initWebView() {
        this.webView.setInitialScale(0);
        this.webView.setVerticalScrollBarEnabled(false);
        this.webView.setHorizontalScrollBarEnabled(false);
        this.webView.setLayerType(2, null);
        WebSettings localWebSettings = this.webView.getSettings();
        if (localWebSettings != null)
        {
            localWebSettings.setUseWideViewPort(true);
            localWebSettings.setLoadWithOverviewMode(true);
            localWebSettings.setDomStorageEnabled(true);
            localWebSettings.setAppCacheEnabled(true);
            localWebSettings.setDatabaseEnabled(true);
            localWebSettings.setJavaScriptEnabled(true);
            localWebSettings.setPluginState(WebSettings.PluginState.ON);
            localWebSettings.setUserAgentString(localWebSettings.getUserAgentString()
                    + " Mobike/");// + MyApplication.a
        }
        this.webView.addJavascriptInterface(new WebAppInterface(this), "app");
        if (Build.VERSION.SDK_INT >= 21)
            this.webView.getSettings().setMixedContentMode(2);
        this.webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView paramWebView, String paramString) {
                super.onPageFinished(paramWebView, paramString);
                WebViewActivity.this.loadingPageView.hide();
            }

            public void onPageStarted(WebView paramWebView, String paramString, Bitmap paramBitmap) {
                super.onPageStarted(paramWebView, paramString, paramBitmap);
                WebViewActivity.this.loadingPageView.show();
            }

            public void onReceivedSslError(WebView paramWebView, SslErrorHandler paramSslErrorHandler, SslError paramSslError) {
                paramSslErrorHandler.proceed();
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);

            }
        });
        WebChromeClient chromeClient = new WebChromeClient() {
            public void onProgressChanged(WebView paramWebView, int paramInt) {
                super.onProgressChanged(paramWebView, paramInt);
                if (100 == paramInt)
                    WebViewActivity.this.loadingPageView.hide();
                if (!CommonUtils.isNetworkAvailable(WebViewActivity.this)) {
                    WebViewActivity.this.loadingPageView.hide();
                    WebViewActivity.this.unavailableView.setVisibility(View.VISIBLE);
                }

            }

            public void onReceivedTitle(WebView paramWebView, String paramString) {
                super.onReceivedTitle(paramWebView, paramString);
                WebViewActivity.this.setTitle(paramString);
            }
        };
        webView.setWebChromeClient(chromeClient);
        webView.addJavascriptInterface(new WebAppInterface(this), "app");
    }

    class WebAppInterface {
        private Context mContext;

        public WebAppInterface(Context context) {
            this.mContext = context;
        }

        @JavascriptInterface
        public void showDetail() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //安卓端调用网页api
                }
            });
        }

        @JavascriptInterface
        public void depositIssue() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //供网页调用安卓原生api
                   // ToastUtils.show(WebViewActivity.this, "点击网页：超过7个工作日未到账");
                    startActivity(DepositRefundIssueActivity.
                            getDepositRefundIntent(WebViewActivity.this));
                }
            });

        }
    }
    @OnClick(R.id.lo_networt)
    public void onViewClicked() {
        if (CommonUtils.isNetworkAvailable(this)) {
            this.loadingPageView.show();
            unavailableView.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(this.webView.getUrl()))
                this.webView.reload();
        } else {
            return;
        }
        this.webView.loadUrl(this.url);
    }

    public static Intent getWebViewIntent(String webview_title, String webview_url)
    {
        Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse("mobike://home/web"));
        localIntent.putExtra("webview_title", webview_title);
        localIntent.putExtra("webview_url", webview_url);
        return localIntent;
    }
    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        mTitleWebview.setTitleText(title);
    }
}
