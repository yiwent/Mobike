package com.yiwen.mobike.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;

import com.yiwen.mobike.R;
import com.yiwen.mobike.views.TabTitleView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyCouponsActivity extends AppCompatActivity {

    @BindView(R.id.title_coupons)
    TabTitleView mTitleCoupons;
    @BindView(R.id.et_coupons)
    EditText     mEtCoupons;
    @BindView(R.id.bt_coupons)
    Button       mBtCoupons;
    @BindView(R.id.recycler_coupons)
    RecyclerView mRecyclerCoupons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_coupons);
        ButterKnife.bind(this);
        initEvent();
    }

    private void initEvent() {
        mTitleCoupons.setOnLeftButtonClickListener(new TabTitleView.OnLeftButtonClickListener() {
            @Override
            public void onClick() {
                MyCouponsActivity.this.finish();
            }
        });
    }

    @OnClick(R.id.bt_coupons)
    public void onViewClicked() {
    }
}
