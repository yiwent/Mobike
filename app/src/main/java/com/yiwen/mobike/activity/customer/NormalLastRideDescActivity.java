package com.yiwen.mobike.activity.customer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.yiwen.mobike.R;
import com.yiwen.mobike.bean.MyTripsData;
import com.yiwen.mobike.views.MySettingView;
import com.yiwen.mobike.views.TabTitleView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NormalLastRideDescActivity extends AppCompatActivity {

    @BindView(R.id.title_nomallasttrip)
    TabTitleView  mTitleNomallasttrip;
    @BindView(R.id.id_tv_carnub)
    TextView      mIdTvCarnub;
    @BindView(R.id.id_tv_time)
    TextView      mIdTvTime;
    @BindView(R.id.id_tv_timetotal)
    TextView      mIdTvTimetotal;
    @BindView(R.id.findcar)
    MySettingView mFindcar;
    @BindView(R.id.other)
    MySettingView mOther;

    private MyTripsData myTripsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_last_ride_desc);
        ButterKnife.bind(this);
        initToolbar();
        initView();
    }

    private void initToolbar() {
        mTitleNomallasttrip.setOnLeftButtonClickListener(new TabTitleView.OnLeftButtonClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
    }

    private void initView() {
        Logger.d("--------------------------------");
        if (getIntent().getSerializableExtra("TripsData")==null)finish();
        myTripsData= (MyTripsData) getIntent().getSerializableExtra("TripsData");
        mIdTvCarnub.setText(myTripsData.getCarNub());
        mIdTvTime.setText(myTripsData.getTime());
        mIdTvTimetotal.setText(myTripsData.getRideTime());
    }

    public static Intent getMyIntrnt(Context context, MyTripsData myTripsData) {
        Intent intent = new Intent(context, NormalLastRideDescActivity.class);
        intent.putExtra("TripsData", myTripsData);
        return intent;
    }

    @OnClick({R.id.findcar, R.id.other})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.findcar:
                startActivity(BikeDamageReportActivity.
                        getMyIntent(NormalLastRideDescActivity.this,myTripsData.getCarNub()));
                break;
            case R.id.other:
                startActivity(RidingOtherIssueActivity.
                        getMyIntent(NormalLastRideDescActivity.this,myTripsData.getCarNub()));
                break;
        }
    }
}
