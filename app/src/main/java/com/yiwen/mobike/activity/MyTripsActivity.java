package com.yiwen.mobike.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.yiwen.mobike.R;
import com.yiwen.mobike.views.TabTitleView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyTripsActivity extends AppCompatActivity {

    @BindView(R.id.title_credit)
    TabTitleView mTitleCredit;
    @BindView(R.id.recycler_trips)
    RecyclerView mRecyclerTrips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_trips);
        ButterKnife.bind(this);
        initEvent();
    }

    private void initEvent() {
        mTitleCredit.setOnLeftButtonClickListener(new TabTitleView.OnLeftButtonClickListener() {
            @Override
            public void onClick() {
                MyTripsActivity.this.finish();
            }
        });

    }
}
