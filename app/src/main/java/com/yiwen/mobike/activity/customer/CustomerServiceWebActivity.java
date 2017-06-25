package com.yiwen.mobike.activity.customer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.orhanobut.logger.Logger;
import com.yiwen.mobike.R;
import com.yiwen.mobike.activity.pay.DepositRefundIssueActivity;
import com.yiwen.mobike.utils.CommonUtils;
import com.yiwen.mobike.utils.ToastUtils;
import com.yiwen.mobike.views.LoadingPageView;
import com.yiwen.mobike.views.NetworkUnavailableView;
import com.yiwen.mobike.views.TabTitleView;

import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomerServiceWebActivity extends AppCompatActivity {


    public static final String URL   = "url";
    public static final String TITLE = "title";
    @BindView(R.id.id_loadingviewpager)
    LoadingPageView        loadingPageView;
    @BindView(R.id.title_webview)
    TabTitleView           mTitleWebview;
    @BindView(R.id.web_detil)
    WebView                webView;
    @BindView(R.id.lo_networt)
    NetworkUnavailableView unavailableView;

    private String url;
    private String title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_service_web);
        ButterKnife.bind(this);
        initWebView();
        initToobar();
        Intent localIntent = getIntent();
        if (localIntent == null)
            finish();
        title = localIntent.getStringExtra(TITLE);
        if (!TextUtils.isEmpty(this.title))
            setTitle(title);
        url = localIntent.getStringExtra(URL);
        Uri localUri = Uri.parse(this.url);
        Logger.d(url);
        if ((!localUri.getQueryParameterNames().contains("countryid")) || (!localUri.getQueryParameterNames().contains("belongid"))) {
            this.url = buildurl(localUri);
        }
        Logger.d(url);
        if (!TextUtils.isEmpty(this.url)) {
            if (CommonUtils.isNetworkAvailable(this)) {
                Logger.d(url);
                this.webView.loadUrl(this.url);
                this.loadingPageView.show();
                this.unavailableView.setVisibility(View.GONE);
            } else {
                Logger.d("NetworkAvailable");
                CustomerServiceWebActivity.this.loadingPageView.hide();
                CustomerServiceWebActivity.this.unavailableView.setVisibility(View.VISIBLE);
            }
        } else {
            finish();
        }
    }

    private void initToobar() {
        mTitleWebview.setOnLeftButtonClickListener(new TabTitleView.OnLeftButtonClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        mTitleWebview.setTitleText(title);
    }

    private String buildurl(Uri paramUri) {
        // Uri localUri;
        Uri.Builder localBuilder = new Uri.Builder();
        if (TextUtils.isEmpty(paramUri.getQuery())) {
            localBuilder = paramUri.buildUpon();
        }
        for (String str1 = "0"; ; str1 = "1") {
            localBuilder.appendQueryParameter("countryid", str1).
                    appendQueryParameter("belongid", "0");//String.valueOf(q.getUrl().g().id)
            Iterator localIterator = paramUri.getQueryParameterNames().iterator();
            while (localIterator.hasNext()) {
                String str2 = (String) localIterator.next();
                if (("countryid".equals(str2)) || ("belongid".equals(str2)))
                    continue;
                localBuilder.appendQueryParameter(str2, paramUri.getQueryParameter(str2));
            }
            // localUri = Uri.parse(paramUri.toString().replace(paramUri.getQuery(), ""));
            break;
        }
        return localBuilder.toString();
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
                CustomerServiceWebActivity.this.loadingPageView.hide();
            }

            public void onPageStarted(WebView paramWebView, String paramString, Bitmap paramBitmap) {
                super.onPageStarted(paramWebView, paramString, paramBitmap);
                CustomerServiceWebActivity.this.loadingPageView.show();
            }

            public void onReceivedSslError(WebView paramWebView, SslErrorHandler paramSslErrorHandler, SslError paramSslError) {
                paramSslErrorHandler.proceed();
            }
        });
        WebChromeClient chromeClient = new WebChromeClient() {
            public void onProgressChanged(WebView paramWebView, int paramInt) {
                super.onProgressChanged(paramWebView, paramInt);
                if (100 == paramInt)
                    CustomerServiceWebActivity.this.loadingPageView.hide();
                if (!CommonUtils.isNetworkAvailable(CustomerServiceWebActivity.this)) {
                    CustomerServiceWebActivity.this.loadingPageView.hide();
                    CustomerServiceWebActivity.this.unavailableView.setVisibility(View.VISIBLE);
                }

            }

            public void onReceivedTitle(WebView paramWebView, String paramString) {
                super.onReceivedTitle(paramWebView, paramString);
                CustomerServiceWebActivity.this.setTitle(paramString);
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
                    ToastUtils.show(CustomerServiceWebActivity.this, "点击网页：超过7个工作日未到账");
                    startActivity(DepositRefundIssueActivity.
                            getDepositRefundIntent(CustomerServiceWebActivity.this));
                }
            });

        }
    }

    public static Intent getCustomerServiceIntent(Context context, String title, String url) {
        Intent intent = new Intent(context, CustomerServiceWebActivity.class);
        intent.putExtra(TITLE, title);
        intent.putExtra(URL, url);
        return intent;
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

    @Override
    public void onBackPressed() {
        if (this.webView.canGoBack()) {
            this.webView.goBack();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332) {
            if (this.webView.canGoBack())
                this.webView.goBack();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
