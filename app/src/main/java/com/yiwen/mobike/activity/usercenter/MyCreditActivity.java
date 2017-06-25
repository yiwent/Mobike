package com.yiwen.mobike.activity.usercenter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yiwen.mobike.R;
import com.yiwen.mobike.utils.BuidUrl;
import com.yiwen.mobike.utils.CommonUtils;
import com.yiwen.mobike.views.LoadingPageView;
import com.yiwen.mobike.views.NetworkUnavailableView;
import com.yiwen.mobike.views.TabTitleView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyCreditActivity extends AppCompatActivity {


    @BindView(R.id.title_credit)
    TabTitleView           mTitleCredit;
    @BindView(R.id.web_detil)
    WebView                webView;
    @BindView(R.id.id_loading)
    LoadingPageView        loadingPageView;
    @BindView(R.id.id_network)
    NetworkUnavailableView unavailableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_credit);
        ButterKnife.bind(this);

        initWebView();
        initEvent();
       // if (q.getUrl().b())
        {
            if (CommonUtils.isNetworkAvailable(this))
            {
                this.webView.loadUrl(BuidUrl.getCredit());
                this.loadingPageView.show();
                this.unavailableView.setVisibility(View.GONE);
            }else {
                this.loadingPageView.hide();
                this.unavailableView.setVisibility(View.VISIBLE);
            }
         //   return;
        }
     //   startActivityForResult(LoginActivity.getUrl(), 1);
    }

    private void initWebView() {
        this.webView.setInitialScale(0);
        this.webView.setVerticalScrollBarEnabled(false);
        this.webView.setHorizontalScrollBarEnabled(false);
        this.webView.getSettings().setDomStorageEnabled(true);
        this.webView.getSettings().setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= 21)
            this.webView.getSettings().setMixedContentMode(2);
        this.webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView paramWebView, String paramString) {
                super.onPageFinished(paramWebView, paramString);
                MyCreditActivity.this.loadingPageView.hide();
            }

            public void onPageStarted(WebView paramWebView, String paramString, Bitmap paramBitmap) {
                super.onPageStarted(paramWebView, paramString, paramBitmap);
                MyCreditActivity.this.loadingPageView.show();
            }

            public void onReceivedSslError(WebView paramWebView, SslErrorHandler paramSslErrorHandler, SslError paramSslError) {
                paramSslErrorHandler.proceed();
            }
        });

        WebChromeClient chromeClient = new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (100 == newProgress)
                   loadingPageView.hide();
                if (!CommonUtils.isNetworkAvailable(MyCreditActivity.this)){
                MyCreditActivity.this.loadingPageView.hide();
                unavailableView.setVisibility(View.VISIBLE);}
            }
        };

        if (!(webView instanceof WebView)) {
            webView.setWebChromeClient(chromeClient);
            return;
        }
    }

    @Override
    public void onBackPressed() {
        if (this.webView.canGoBack()) {
            this.webView.goBack();
            return;
        }
        super.onBackPressed();
    }

    private void initEvent() {
        mTitleCredit.setOnLeftButtonClickListener(new TabTitleView.OnLeftButtonClickListener() {
            @Override
            public void onClick() {
                MyCreditActivity.this.finish();
            }
        });
    }

    @OnClick(R.id.id_network)
    public void onViewClicked() {
        if (CommonUtils.isNetworkAvailable(this)) {
            this.loadingPageView.show();
            unavailableView.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(this.webView.getUrl()))
                this.webView.reload();
        } else {
            return;
        }
        this.webView.loadUrl(BuidUrl.getCredit());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1){
            this.webView.loadUrl(BuidUrl.getCredit());
            this.loadingPageView.show();
            this.unavailableView.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == 16908332)
        {
            if (this.webView.canGoBack())
                this.webView.goBack();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
