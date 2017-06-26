package com.yiwen.mobike.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yiwen.mobike.MyApplication;
import com.yiwen.mobike.R;
import com.yiwen.mobike.activity.MainActivity;
import com.yiwen.mobike.activity.customer.CustomerServiceWebActivity;
import com.yiwen.mobike.bean.MyUser;
import com.yiwen.mobike.bean.RechargeHistoryData;
import com.yiwen.mobike.utils.BuidUrl;
import com.yiwen.mobike.utils.ToastUtils;
import com.yiwen.mobike.views.LoadingToastView;
import com.yiwen.mobike.views.TabTitleView;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisterRechargeActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_login)
    TabTitleView     mToolbar;
    @BindView(R.id.wechat)
    ImageView        mWechat;
    @BindView(R.id.wechat_layout)
    RelativeLayout   mWechatLayout;
    @BindView(R.id.view1)
    View             mView1;
    @BindView(R.id.alipay)
    ImageView        mAlipay;
    @BindView(R.id.alipay_layout)
    RelativeLayout   mAlipayLayout;
    @BindView(R.id.id_bt_pay)
    Button           mBtPay;
    @BindView(R.id.charge_services)
    TextView         mChargeServices;
    @BindView(R.id.pay_loading)
    LoadingToastView mLoadingToastView;
    private final static String              PAY_WEIXIN ="wechat";
    private final static String              PAY_ALIPAY ="alipay";
    private              String              payType    =PAY_WEIXIN;
    private              RechargeHistoryData mData      =new RechargeHistoryData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_recharge);
        ButterKnife.bind(this);
        initToolbar();
        initView();

    }

    private void initView() {

    }

    private void initToolbar() {
        mToolbar.setOnLeftButtonClickListener(new TabTitleView.OnLeftButtonClickListener() {
            @Override
            public void onClick() {
                Intent intent = new Intent(RegisterRechargeActivity.this,
                        MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @OnClick({R.id.wechat_layout, R.id.alipay_layout, R.id.id_bt_pay, R.id.charge_services})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.wechat_layout:
                mWechat.setImageResource(R.drawable.report_checkbox_select_hover);
                mAlipay.setImageResource(R.drawable.report_checkbox_select_default);
                payType=PAY_WEIXIN;
                break;
            case R.id.alipay_layout:
                mWechat.setImageResource(R.drawable.report_checkbox_select_default);
                mAlipay.setImageResource(R.drawable.report_checkbox_select_hover);
                payType=PAY_ALIPAY;
                break;
            case R.id.id_bt_pay:
                toPay();
                break;
            case R.id.charge_services:
                startActivity(CustomerServiceWebActivity
                        .getCustomerServiceIntent(RegisterRechargeActivity.this, "充值协议",
                                BuidUrl.getChargeProtocal()));
                break;
        }
    }

    private void toPay() {
        mLoadingToastView.show();
        mBtPay.setClickable(false);
        mData.setMyUser(MyApplication.getInstance().getUser());
        mData.setPayApproach(payType);
        mData.setStatusCode("付款成功");
        mData.setCash("299");
        SimpleDateFormat formatter   =   new   SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date curDate =  new Date(System.currentTimeMillis());
        mData.setTimestamp(formatter.format(curDate));
        mData.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e==null){
                    ToastUtils.show(RegisterRechargeActivity.this, "充值成功");
                    MyUser myUser=  MyApplication.getInstance().getUser();
                    myUser.setMoney(myUser.getMoney()+299.0f);
                    myUser.setPay(true);
                    MyUser newUse=new MyUser();
                    newUse.setPay(true);
                    newUse.setMoney(myUser.getMoney()+299.0f);
                    MyApplication.getInstance().upDataUser(myUser,newUse);

                    if (!MyApplication.getInstance().getUser().getRealName()){
                        Intent intent = new Intent(RegisterRechargeActivity.this,
                                IDCardVerifyActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Intent intent = new Intent(RegisterRechargeActivity.this,
                                MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }else {
                    ToastUtils.show(RegisterRechargeActivity.this, "充值失败");
                }
                mBtPay.setClickable(true);
                mLoadingToastView.hind();
            }
        });
    }

}
