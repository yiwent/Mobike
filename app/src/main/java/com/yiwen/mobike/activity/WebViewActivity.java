package com.yiwen.mobike.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.yiwen.mobike.R;
import com.yiwen.mobike.utils.CommonUtils;
import com.yiwen.mobike.views.TabTitleView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewActivity extends AppCompatActivity {

    @BindView(R.id.title_webview)
    TabTitleView mTitleWebview;
    @BindView(R.id.web_detil)
    WebView      mWebDetil;
    @BindView(R.id.lo_webview)
    LinearLayout mLo_webview;
    @BindView(R.id.lo_networt)
    LinearLayout mLo_networt;

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

    private void initData() {
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        if (!CommonUtils.isConn(this)) {
            mLo_webview.setVisibility(View.GONE);
            mLo_networt.setVisibility(View.VISIBLE);
        }else {

        }
    }
}
