package com.yiwen.mobike.activity.pay;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yiwen.mobike.MyApplication;
import com.yiwen.mobike.R;
import com.yiwen.mobike.activity.customer.CustomerServiceWebActivity;
import com.yiwen.mobike.adapter.BaseAdapter;
import com.yiwen.mobike.adapter.ChargeAmountAdapter;
import com.yiwen.mobike.bean.MyUser;
import com.yiwen.mobike.bean.RechargeHistoryData;
import com.yiwen.mobike.utils.BuidUrl;
import com.yiwen.mobike.utils.ToastUtils;
import com.yiwen.mobike.views.LoadingToastView;
import com.yiwen.mobike.views.TabTitleView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class PayActivity extends AppCompatActivity {


    @BindView(R.id.title_pay)
    TabTitleView     mTitlePay;
    @BindView(R.id.tv_pay)
    TextView         mTvPay;
    @BindView(R.id.id_gridview)
    RecyclerView     mRecyclerview;
    @BindView(R.id.wechat_layout)
    RelativeLayout   mWechatLayout;
    @BindView(R.id.alipay_layout)
    RelativeLayout   mAlipayLayout;
    @BindView(R.id.id_bt_pay)
    Button           mBtPay;
    @BindView(R.id.charge_services)
    TextView         mChargeServices;
    @BindView(R.id.pay_loading)
    LoadingToastView mLoadingToastView;
    @BindView(R.id.wechat)
    ImageView        mWechat;
    @BindView(R.id.alipay)
    ImageView        mAlipay;

    private ChargeAmountAdapter mAdapter;
    private String[] chargetext = {"充￥100元", "充￥50元", "充￥20元", "充￥10元", "充￥5元"};
    private final static String PAY_WEIXIN="wechat";
    private final static String PAY_ALIPAY="alipay";
    private String payType=PAY_WEIXIN;
    private RechargeHistoryData mData=new RechargeHistoryData();
    private List<Integer> mIntegers=new ArrayList<>(5);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);
        initToolbar();
        initView();
    }

    private void initToolbar() {
        mTitlePay.setOnLeftButtonClickListener(new TabTitleView.OnLeftButtonClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
    }

    private void initView() {
        mIntegers.add(100);
        mIntegers.add(50);
        mIntegers.add(20);
        mIntegers.add(10);
        mIntegers.add(5);

        final List<String> mData = new ArrayList<>();
        for (int i = 0; i < chargetext.length; i++) {
            mData.add(chargetext[i]);
        }
        mAdapter = new ChargeAmountAdapter(this, mData);
        mRecyclerview.setAdapter(mAdapter);
        mRecyclerview.setLayoutManager(new GridLayoutManager(this, 2));
        mAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                mAdapter.setSelectPosition(position);
            }
        });

    }

    @OnClick({R.id.alipay_layout, R.id.wechat_layout, R.id.id_bt_pay, R.id.charge_services})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.alipay_layout:
                mWechat.setImageResource(R.drawable.report_checkbox_select_default);
                mAlipay.setImageResource(R.drawable.report_checkbox_select_hover);
                payType=PAY_ALIPAY;
                break;
            case R.id.wechat_layout:
                mWechat.setImageResource(R.drawable.report_checkbox_select_hover);
                mAlipay.setImageResource(R.drawable.report_checkbox_select_default);
                payType=PAY_WEIXIN;
                break;
            case R.id.id_bt_pay:
                toPay();

                break;
            case R.id.charge_services:
                startActivity(CustomerServiceWebActivity
                        .getCustomerServiceIntent(PayActivity.this, "充值协议",
                                BuidUrl.getChargeProtocal()));
                break;
        }
    }

    private void toPay() {
        mLoadingToastView.show();
       // ToastUtils.show(PayActivity.this, "付钱吧");
        mBtPay.setClickable(false);
        mData.setMyUser(MyApplication.getInstance().getUser());
        mData.setPayApproach(payType);
        mData.setStatusCode("付款成功");
        mData.setCash(mIntegers.get(mAdapter.selectPosition)+"");
        SimpleDateFormat formatter   =   new   SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date curDate =  new Date(System.currentTimeMillis());
        mData.setTimestamp(formatter.format(curDate));
        mData.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e==null){
                    ToastUtils.show(PayActivity.this, "充值成功");
                    MyUser myUser=  MyApplication.getInstance().getUser();
                    myUser.setMoney(myUser.getMoney()+
                            Float.parseFloat(mIntegers.get(mAdapter.selectPosition)+""));
                    MyUser newUse=new MyUser();
                    newUse.setMoney(myUser.getMoney()+
                            Float.parseFloat(mIntegers.get(mAdapter.selectPosition)+""));
                    MyApplication.getInstance().upDataUser(myUser,newUse);
                }else {
                    ToastUtils.show(PayActivity.this, "充值失败");
                }
                mBtPay.setClickable(true);
                mLoadingToastView.hind();
            }
        });
    }
}
