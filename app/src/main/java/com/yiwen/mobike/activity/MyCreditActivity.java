package com.yiwen.mobike.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yiwen.mobike.R;
import com.yiwen.mobike.views.TabTitleView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyCreditActivity extends AppCompatActivity {

    @BindView(R.id.title_credit)
    TabTitleView mTitleCredit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_credit);
        ButterKnife.bind(this);
        initEvent();
    }

    private void initEvent() {
        mTitleCredit.setOnLeftButtonClickListener(new TabTitleView.OnLeftButtonClickListener() {
            @Override
            public void onClick() {
                MyCreditActivity.this.finish();
            }
        });
    }
}
