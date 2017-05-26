package com.yiwen.mobike.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yiwen.mobike.R;
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
    @BindView(R.id.bt_wallet_charge)
    Button       mBtWalletCharge;

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
    }

    @OnClick({R.id.tv_cashfind, R.id.bt_wallet_charge})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cashfind:
                break;
            case R.id.bt_wallet_charge:
                break;
        }
    }
}
