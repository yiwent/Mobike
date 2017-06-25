package com.yiwen.mobike.activity.pay;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yiwen.mobike.MyApplication;
import com.yiwen.mobike.R;
import com.yiwen.mobike.bean.MyUser;
import com.yiwen.mobike.utils.ToastUtils;
import com.yiwen.mobike.views.TabTitleView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WalletActivity extends AppCompatActivity {

    @BindView(R.id.title_wallet)
    TabTitleView mTitleWallet;
    @BindView(R.id.tv_freeRide)
    TextView     mTvFreeRide;
    @BindView(R.id.tv_money_total)
    TextView     mTvMoneyTotal;
    @BindView(R.id.tv_cashfind)
    TextView     mTvCashfind;
    @BindView(R.id.iscash)
    TextView     mTvisCash;
    @BindView(R.id.bt_wallet_charge)
    Button       mBtWalletCharge;
    MyUser mMyUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        ButterKnife.bind(this);
        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
        mTitleWallet.setOnLeftButtonClickListener(new TabTitleView.OnLeftButtonClickListener() {
            @Override
            public void onClick() {
                WalletActivity.this.finish();
            }
        });
        mTitleWallet.setOnRightTextViewClickListener(new TabTitleView.OnRightButtonClickListener() {
            @Override
            public void onClick() {
                Intent intent=new Intent(WalletActivity.this,RechargeHistoryActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initData() {
    }

    private void initView() {
        mMyUser= MyApplication.getInstance().getUser();
        if (mMyUser!=null){
            if (mMyUser.getMoney()!=null)
            mTvMoneyTotal.setText(mMyUser.getMoney()+"");
            else {
                mTvMoneyTotal.setText("0.0");
            }

           if (mMyUser.getPay()){
             mTvisCash.setText("押金299元");
               mTvCashfind.setText("押金退款");
           }else {
               mTvisCash.setText("押金0元");
               mTvCashfind.setText("充押金");
           }
        }
    }

    @OnClick({R.id.tv_cashfind, R.id.bt_wallet_charge})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cashfind:
                toCashfindOrpay();
                break;
            case R.id.bt_wallet_charge:
                startActivity(new Intent(WalletActivity.this,PayActivity.class));
                break;
        }
    }

    private void toCashfindOrpay() {
        // TODO: 2017/6/22
        if (mMyUser.getPay()){
            ToastUtils.show(WalletActivity.this,"退押金");
        } else {
            ToastUtils.show(WalletActivity.this,"交押金");
        }
    }
}
