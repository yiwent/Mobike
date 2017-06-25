package com.yiwen.mobike.activity.usercenter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sharesdk.onekeyshare.OnekeyShare;
import com.yiwen.mobike.R;
import com.yiwen.mobike.utils.ToastUtils;
import com.yiwen.mobike.views.TabTitleView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.ShareSDK;

public class InviteFriendActivity extends AppCompatActivity {


    @BindView(R.id.title_invent)
    TabTitleView mTitleInvent;
    @BindView(R.id.id_bt_query)
    Button       mBtQuery;
    @BindView(R.id.id_tv_myinvent_nub)
    TextView     mTvMyinventNub;
    @BindView(R.id.id_bt_share)//
    Button       mBtShare;
    @BindView(R.id.id_et_invent)//
    EditText     mEtinvent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friend);
        ButterKnife.bind(this);
        ShareSDK.initSDK(this);
        initEvent();
    }

    private void initEvent() {
        mTitleInvent.setOnLeftButtonClickListener(new TabTitleView.OnLeftButtonClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        mTitleInvent.setOnRightTextViewClickListener(new TabTitleView.OnRightButtonClickListener() {
            @Override
            public void onClick() {
                showShare();
            }
        });

    }

    @OnClick({R.id.id_bt_query, R.id.id_bt_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.id_bt_query:
                ToastUtils.show(InviteFriendActivity.this,"提交了"+mEtinvent.getText().toString());
                break;
            case R.id.id_bt_share:
                showShare();
                break;
        }
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("http://www.mobike.com");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://www.mobike.com");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("http://www.mobike.com");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("https://mbkst.mobike.com/app/static/images/circle.d766e24.png");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数,优先级大于setImageUrl
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("mobike");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://www.mobike.com");
        // 启动分享GUI
        oks.show(this);
    }
}
