package com.yiwen.mobike.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.yiwen.mobike.R;
import com.yiwen.mobike.views.TabTitleView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageDetailActivity extends AppCompatActivity {

    public static final String URL   = "url";
    public static final String TITLE = "title";
    @BindView(R.id.toolbar_massage_detail)
    TabTitleView mToolbar;
    @BindView(R.id.web_detil)
    WebView      mWebDetil;
    @BindView(R.id.lo_webview)
    LinearLayout mLoWebview;

    private String url;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        ButterKnife.bind(this);
        getData();
        initToolbar();
        initWebView();
    }

    private void initWebView() {
        // TODO: 2017/6/5 loding cuowu

    }

    private void initToolbar() {
        mToolbar.setOnLeftButtonClickListener(new TabTitleView.OnLeftButtonClickListener() {
            @Override
            public void onClick() {
                onBackPressed();
            }
        });
        mToolbar.setOnRightTextViewClickListener(new TabTitleView.OnRightButtonClickListener() {
            @Override
            public void onClick() {
                showShare();
            }
        });
    }

    private void showShare() {
        // TODO: 2017/6/5 分享功能
    }

    private void getData() {
        Intent intent = getIntent();
        url = intent.getStringExtra(MessageDetailActivity.URL);
        if (TextUtils.isEmpty(url))
            finish();
        title = intent.getStringExtra(MessageDetailActivity.TITLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
