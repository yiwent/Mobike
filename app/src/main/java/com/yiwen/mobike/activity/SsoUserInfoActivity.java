package com.yiwen.mobike.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.yiwen.mobike.R;
import com.yiwen.mobike.utils.ToastUtils;
import com.yiwen.mobike.views.TabTitleView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SsoUserInfoActivity extends AppCompatActivity {

    @BindView(R.id.title_sso_user)
    TabTitleView mTitleSsoUser;
    @BindView(R.id.iv_wxhead)
    ImageView    mIvWxhead;
    @BindView(R.id.tv_weixin_name)
    TextView     mTvWeixinName;
    @BindView(R.id.tv_can)
    TextView     mTvCan;
    @BindView(R.id.tv_unbindWX)
    TextView       mTvUnbindWX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sso_user_info);
        ButterKnife.bind(this);
        initEvent();
    }

    private void initEvent() {
        mTitleSsoUser.setOnLeftButtonClickListener(new TabTitleView.OnLeftButtonClickListener() {
            @Override
            public void onClick() {
                SsoUserInfoActivity.this.finish();
            }
        });
    }

    @OnClick(R.id.tv_unbindWX)
    public void onViewClicked() {
        ToastUtils.show(SsoUserInfoActivity.this,"取消绑定");
    }
}
