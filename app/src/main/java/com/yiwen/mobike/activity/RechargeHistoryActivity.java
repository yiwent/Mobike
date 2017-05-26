package com.yiwen.mobike.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.yiwen.mobike.R;
import com.yiwen.mobike.views.TabTitleView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RechargeHistoryActivity extends AppCompatActivity {

    @BindView(R.id.title_wallet)
    TabTitleView       mTitleWallet;
    @BindView(R.id.recycler_charge_history)
    RecyclerView       mRecyclerChargeHistory;
    @BindView(R.id.swipeRefresg_charge_history)
    SwipeRefreshLayout mSwipeRefresgChargeHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_history);
        ButterKnife.bind(this);

        initData();
        initEvent();
    }

    private void initEvent() {
        mTitleWallet.setOnLeftButtonClickListener(new TabTitleView.OnLeftButtonClickListener() {
            @Override
            public void onClick() {
                RechargeHistoryActivity.this.finish();
            }
        });
        mTitleWallet.setOnRightTextViewClickListener(new TabTitleView.OnRightButtonClickListener() {
            @Override
            public void onClick() {

            }
        });
    }

    private void initData() {
    }
}
